package com.example.manetesartistes_game

import android.annotation.SuppressLint
import android.graphics.Bitmap
import androidx.appcompat.app.AppCompatActivity
import android.graphics.Color
import android.graphics.drawable.BitmapDrawable
import android.os.Bundle
import android.util.Log
import android.view.MotionEvent
import android.widget.ImageView

class ImageEditor: AppCompatActivity() {

    enum class DeviceType {
        GALAXY_TAB_A,
        EMULATOR,
        OTHER
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_image_editor)

        val imageView = findViewById<ImageView>(R.id.imageView)

        val imageResId = intent.getIntExtra("IMAGE_RES_ID", -1)
        if (imageResId != -1) {
            imageView.setImageResource(imageResId)
//            val layoutParams = imageView.layoutParams
//            var bitmap = (imageView.drawable as BitmapDrawable).bitmap
//            layoutParams.width = bitmap.width
//            layoutParams.height = bitmap.height
//            imageView.layoutParams = layoutParams
        }

        imageView.setOnTouchListener { v, event ->
            try {
                if (event.action == MotionEvent.ACTION_DOWN) {
                    // calibrate image coordinates
                    val deviceType = DeviceType.EMULATOR
                    val (scaleX, scaleY) = when(deviceType) {
                        DeviceType.GALAXY_TAB_A -> Pair(1.55, 1.55)
                        DeviceType.EMULATOR -> Pair(2.0, 2.0)
                        DeviceType.OTHER -> Pair(2.0, 2.0)
                    }

                    val imageX = (event.x / scaleX).toInt()
                    val imageY = (event.y / scaleY).toInt()

                    println("imageX$imageX")
                    println("imageY$imageY")


                    if (imageX != -1 && imageY != -1) {
                        var bitmap = (imageView.drawable as BitmapDrawable).bitmap
                        bitmap = bitmap.copy(Bitmap.Config.ARGB_8888, true)
                        val targetColor = bitmap.getPixel(imageX, imageY)
                        val newColor = Color.parseColor("#f59542")  // Set the new color

                        val newBitmap = FloodFillUtils.floodFill(bitmap, imageX, imageY, targetColor, newColor)

                        imageView.setImageBitmap(newBitmap)

                        // Set ImageView dimensions to match the bitmap dimensions
//                        val layoutParams = imageView.layoutParams
//                        layoutParams.width = bitmap.width
//                        layoutParams.height = bitmap.height
//                        imageView.layoutParams = layoutParams
                    }
                }
                true
            } catch (e: Exception) {
                Log.e("Error", e.toString())
                Log.e("Error", e.stackTraceToString())
                true
            }
        }
    }


}