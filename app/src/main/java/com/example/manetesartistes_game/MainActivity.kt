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

                    // Apply flood fill
                    floodFill(bitmap, imageX, imageY, targetColor, newColor)

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

    // BFS-based itearative flood fill algorithm
    private fun floodFill(bitmap: Bitmap, x: Int, y: Int, targetColor: Int, replacementColor: Int) {
        if (targetColor == replacementColor) return  // avoid if same color

        val width = bitmap.width
        val height = bitmap.height

        // queue to store the pixels to be filled
        val queue: Queue<Pair<Int, Int>> = LinkedList()
        queue.add(Pair(x, y))

        while (queue.isNotEmpty()) {
            val (currentX, currentY) = queue.remove()

            // Check if the current pixel is within the bounds and matches the target color
            if (currentX in 0 until width && currentY in 0 until height && isWithinTolerance(bitmap.getPixel(currentX, currentY), targetColor, 0.30)) {
                // Change the color of the current pixel
                bitmap.setPixel(currentX, currentY, replacementColor)

                // Add neighboring pixels (up, down, left, right) to the queue
                queue.add(Pair(currentX + 1, currentY))  // Right
                queue.add(Pair(currentX - 1, currentY))  // Left
                queue.add(Pair(currentX, currentY + 1))  // Down
                queue.add(Pair(currentX, currentY - 1))  // Up
            }

        }
    }

    fun isWithinTolerance(color1: Int, color2: Int, tolerance: Double): Boolean {
        val red1 = Color.red(color1)
        val green1 = Color.green(color1)
        val blue1 = Color.blue(color1)

        val red2 = Color.red(color2)
        val green2 = Color.green(color2)
        val blue2 = Color.blue(color2)

        // Calculate the percentage difference for each component
        val redDiff = Math.abs(red1 - red2) / 255.0
        val greenDiff = Math.abs(green1 - green2) / 255.0
        val blueDiff = Math.abs(blue1 - blue2) / 255.0

        // Return true if all components are within the tolerance
        return (redDiff <= tolerance) && (greenDiff <= tolerance) && (blueDiff <= tolerance)
    }

}
