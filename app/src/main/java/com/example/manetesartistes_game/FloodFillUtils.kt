package com.example.manetesartistes_game

import android.graphics.Bitmap
import android.graphics.Color
import java.util.LinkedList
import java.util.Queue

class FloodFillUtils {
    companion object {
        // BFS-based itearative flood fill algorithm
        fun floodFill(
            bitmap: Bitmap,
            x: Int,
            y: Int,
            targetColor: Int,
            replacementColor: Int,
            targetColorTolerance : Double = 0.30
        ) {
            if (targetColor == replacementColor) return  // avoid if same color

            val width = bitmap.width
            val height = bitmap.height

            // queue to store the pixels to be filled
            val queue: Queue<Pair<Int, Int>> = LinkedList()
            queue.add(Pair(x, y))

            while (queue.isNotEmpty()) {
                val (currentX, currentY) = queue.remove()

                // Check if the current pixel is within the bounds and matches the target color
                if (currentX in 0 until width && currentY in 0 until height && isWithinTolerance(
                        bitmap.getPixel(currentX, currentY),
                        targetColor,
                        targetColorTolerance
                    )
                ) {
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
}