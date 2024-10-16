package com.example.manetesartistes_game

import android.annotation.SuppressLint
import android.graphics.Bitmap
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.MotionEvent
import android.widget.Button
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import java.util.LinkedList
import java.util.Queue
import android.graphics.drawable.BitmapDrawable

class MainActivity : AppCompatActivity() {


    @SuppressLint("ClickableViewAccessibility")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val imageView = findViewById<ImageView>(R.id.imageView)
        imageView.setImageResource(R.drawable.fish)  // Load your image here

        imageView.setOnTouchListener { v, event ->
            try {
                if (event.action == MotionEvent.ACTION_DOWN) {
                // Get the clicked coordinates
                    val imageX: Int = (event.x / 2).toInt();
                    val imageY: Int = (event.y / 2).toInt();


                if (imageX != -1 && imageY != -1) {
                    var bitmap = (imageView.drawable as BitmapDrawable).bitmap
                    bitmap = bitmap.copy(Bitmap.Config.ARGB_8888, true)
                    val targetColor = bitmap.getPixel(imageX, imageY)
                    val newColor = Color.parseColor("#f59542")  // Set the new color

                    FloodFillUtils.floodFill(bitmap, imageX, imageY, targetColor, newColor)

                    // Update the ImageView with the modified bitmap
                    imageView.setImageBitmap(bitmap)
                }
            }
            true
            }
            catch (e: Exception) {
                Log.e("Error", e.toString())
                Log.e("Error", e.stackTraceToString())
                true
            }
        }
    }

}
