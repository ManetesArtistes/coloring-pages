package com.example.manetesartistes_game

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {

    private lateinit var customCanvasView: CustomCanvasView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        customCanvasView = findViewById(R.id.canvasView)

        val colorPalette = findViewById<ColorPalette>(R.id.colorPalette)

        // Set the listener to handle color selection
        colorPalette.onColorSelected = { selectedColor ->
            customCanvasView.setSelectedColor(selectedColor)
        }

        // Set default selected color to gray
        customCanvasView.setSelectedColor(Color.GRAY)

        // Reference to the button
        val toggleButton = findViewById<Button>(R.id.toggleTeacherModeButton)

        // Set a click listener for the button
        toggleButton.setOnClickListener {
            customCanvasView.toggleTeacherMode()
        }
    }
}
