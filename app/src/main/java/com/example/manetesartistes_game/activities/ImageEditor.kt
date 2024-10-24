package com.example.manetesartistes_game.activities

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Bitmap
import androidx.appcompat.app.AppCompatActivity
import android.graphics.Color
import android.graphics.drawable.BitmapDrawable
import android.os.Bundle
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import com.example.manetesartistes_game.R
import com.example.manetesartistes_game.colors.ColorLoader
import com.example.manetesartistes_game.colors.ColorPalette
import com.example.manetesartistes_game.imageEditor.Draw
import com.example.manetesartistes_game.imageEditor.FloodFillUtils

class ImageEditor: AppCompatActivity() {

    private var selectedColor: Int = Color.parseColor("#f59542")

    @SuppressLint("ClickableViewAccessibility")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        window.decorView.systemUiVisibility = (
                View.SYSTEM_UI_FLAG_HIDE_NAVIGATION       // Hides the navigation bar
                        or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION  // Allows layout to expand under navigation
                        or View.SYSTEM_UI_FLAG_FULLSCREEN          // Hides the status bar
                        or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN   // Allows layout to expand under the status bar
                        or View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY    // Ensures that the UI stays hidden even after user interaction
                )
        setContentView(R.layout.activity_image_editor)

        try {
            val drawData: Draw? = intent.getSerializableExtra("DRAW_DATA") as Draw?

            if (drawData != null) {
                renderDrawWhiteImage(drawData.whiteImage)
                renderBackgroundImage(drawData.backgroundImage)
                renderColorPalette(drawData)
            }

        }catch (e: Exception) {
            Log.e("Error", e.toString())
            Log.e("Error", e.stackTraceToString())
        }

    }

    private fun renderBackgroundImage(resourceString: String){
        val resource = resources.getIdentifier(resourceString, "drawable", packageName)
        val containerImageView = findViewById<LinearLayout>(R.id.containerImageView)
        containerImageView.setBackgroundResource(resource)
    }

    private fun renderDrawWhiteImage(resourceString: String){
        val resource = resources.getIdentifier(resourceString, "drawable", packageName)
        val imageView = findViewById<ImageView>(R.id.imageView)
        imageView.setImageResource(resource)

        imageView.setOnTouchListener { v, event ->
            onTouchImage(event, imageView)
        }
    }

    private fun renderColorPalette(draw: Draw){
        val colorPalette = findViewById<ColorPalette>(R.id.colorPalette)
        val colors = ColorLoader.getColorsByIds(draw.colors, this)
        colorPalette.setColors(colors)

        val resource = resources.getIdentifier(draw.coloredImage, "drawable", packageName)
        val bgResource = resources.getIdentifier(draw.squareBackgroundImage, "drawable", packageName)
        colorPalette.renderColorImage(resource, bgResource)
        // Set the listener to handle color selection
        colorPalette.onColorSelected = { selectedColor ->
            this.setSelectedColor(selectedColor)
            println(this.selectedColor)
        }
    }

    private fun getCalibrationScaleFactor(context: Context): Pair<Double, Double> {
        val metrics = context.resources.displayMetrics
        val density = metrics.densityDpi

        return when {
            density >= 400 -> Pair(2.0, 2.0)
            density in 300..399 -> Pair(1.75, 1.75)
            density in 200..299 -> Pair(1.5, 1.5)
            else -> Pair(1.0, 1.0)
        }
    }

    private fun onTouchImage(event: MotionEvent, imageView: ImageView): Boolean {
        try {
            if (event.action == MotionEvent.ACTION_DOWN) {
                val scaleFactors = getCalibrationScaleFactor(imageView.context)

                val imageX = (event.x / scaleFactors.first).toInt()
                val imageY = (event.y / scaleFactors.second).toInt()

                if (imageX != -1 && imageY != -1) {
                    var bitmap = (imageView.drawable as BitmapDrawable).bitmap
                    bitmap = bitmap.copy(Bitmap.Config.ARGB_8888, true)
                    val targetColor = bitmap.getPixel(imageX, imageY)

                    val newBitmap = FloodFillUtils.floodFill(
                        bitmap,
                        imageX,
                        imageY,
                        targetColor,
                        this.selectedColor
                    )

                    imageView.setImageBitmap(newBitmap)
                }
            }
            return true
        } catch (e: Exception) {
            Log.e("Error happened rendering the drawing: ", e.toString())
            Log.e("Error", e.stackTraceToString())
            return true
        }
    }

    fun setSelectedColor(color: Int) {
        selectedColor = color
    }


}