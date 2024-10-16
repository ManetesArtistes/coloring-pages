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
        }

        imageView.setOnTouchListener { v, event ->
            try {
                if (event.action == MotionEvent.ACTION_DOWN) {
                    val imageX: Int = (event.x / 2).toInt()
                    val imageY: Int = (event.y / 2).toInt()

                    if (imageX != -1 && imageY != -1) {
                        var bitmap = (imageView.drawable as BitmapDrawable).bitmap
                        bitmap = bitmap.copy(Bitmap.Config.ARGB_8888, true)
                        val targetColor = bitmap.getPixel(imageX, imageY)
                        val newColor = Color.parseColor("#f59542")  // Set the new color

                        FloodFillUtils.floodFill(bitmap, imageX, imageY, targetColor, newColor)

                        imageView.setImageBitmap(bitmap)
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