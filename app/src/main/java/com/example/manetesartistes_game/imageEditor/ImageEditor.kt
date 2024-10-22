package com.example.manetesartistes_game.imageEditor

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Bitmap
import androidx.appcompat.app.AppCompatActivity
import android.graphics.Color
import android.graphics.drawable.BitmapDrawable
import android.os.Bundle
import android.util.DisplayMetrics
import android.util.Log
import android.view.MotionEvent
import android.widget.ImageView
import android.widget.LinearLayout
import com.example.manetesartistes_game.R
import com.example.manetesartistes_game.colors.ColorPalette
import com.example.manetesartistes_game.draw.Draw

class ImageEditor: AppCompatActivity() {

    enum class DeviceType {
        GALAXY_TAB_A,
        EMULATOR,
        OTHER
    }

    private val deviceType = DeviceType.EMULATOR


    private var selectedColor: Int = Color.parseColor("#f59542")

    @SuppressLint("ClickableViewAccessibility")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_image_editor)

        val (width, height) = getScreenDimensions(this)
        println("width and height")
        println(width)
        println(height)

        val colorPalette = findViewById<ColorPalette>(R.id.colorPalette)

        // Set the listener to handle color selection
        colorPalette.onColorSelected = { selectedColor ->
            this.setSelectedColor(selectedColor)
            println(this.selectedColor)
        }

        try {
            // get elements
            val imageView = findViewById<ImageView>(R.id.imageView)
            val containerImageView = findViewById<LinearLayout>(R.id.containerImageView)

            val drawData: Draw? = intent.getSerializableExtra("DRAW_DATA") as Draw?

            val imageResId = intent.getIntExtra("IMAGE_RES_ID", -1)
            if (imageResId != -1 && drawData != null) {

                // get resources
                val drawWhiteImageResource = resources.getIdentifier(drawData.whiteImage, "drawable", packageName)
                val bgImageResource = resources.getIdentifier(drawData.backgroundImage, "drawable", packageName)

                imageView.setImageResource(drawWhiteImageResource)

                containerImageView.setBackgroundResource(bgImageResource)
            }

            imageView.setOnTouchListener { v, event ->
                try {
                    if (event.action == MotionEvent.ACTION_DOWN) {

                        // calibrate image coordinates
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

                            val newBitmap = FloodFillUtils.floodFill(bitmap, imageX, imageY, targetColor, this.selectedColor)

                            imageView.setImageBitmap(newBitmap)
                        }
                    }
                    true
                } catch (e: Exception) {
                    Log.e("Error happened rendering the drawing: ", e.toString())
                    Log.e("Error", e.stackTraceToString())
                    true
                }
            }

        }catch (e: Exception) {
            Log.e("Error", e.toString())
            Log.e("Error", e.stackTraceToString())
        }

    }

    fun getScreenDimensions(context: Context): Pair<Int, Int> {
        val displayMetrics = DisplayMetrics()
        val windowManager = context.getSystemService(Context.WINDOW_SERVICE) as android.view.WindowManager
        windowManager.defaultDisplay.getMetrics(displayMetrics)

        val width = displayMetrics.widthPixels
        val height = displayMetrics.heightPixels

        return Pair(width, height)
    }

    fun setSelectedColor(color: Int) {
        selectedColor = color
    }


}