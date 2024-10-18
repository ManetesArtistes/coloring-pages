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
                    val isGalaxyTabA = true;
                    val isEmulator = false;
                    var imageX: Int;
                    var imageY: Int;
                    if(isGalaxyTabA){
                        imageX = (event.x / 1.55).toInt()
                        imageY = (event.y / 1.55).toInt()
                    }
                    if(isEmulator){
                        imageX = (event.x / 2).toInt()
                        imageY = (event.y / 2).toInt()
                    }

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