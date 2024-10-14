package com.example.manetesartistes_game

import android.content.Context
import android.graphics.Color
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class ColorPalette @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr) {

    private val colorPaletteRecyclerView: RecyclerView

    init {
        // Inflate the layout for this custom view
        LayoutInflater.from(context).inflate(R.layout.view_color_palette, this, true)

        // Initialize the RecyclerView
        colorPaletteRecyclerView = findViewById(R.id.colorPaletteRecyclerView)
        setupRecyclerView()
    }

    private fun setupRecyclerView() {
        // List of colors
        val colors = listOf(
            Color.WHITE,
            Color.RED,
            Color.BLUE,
            Color.GREEN,
            Color.parseColor("#004d00"),
            Color.YELLOW,
            Color.BLACK,
            Color.GRAY,
            Color.parseColor("#8B4513") // Brown color (saddlebrown)
        )

        // Create and set the adapter
        val adapter = ColorPaletteAdapter(colors) { selectedColor ->
            // Notify about the selected color
            onColorSelected(selectedColor)
        }

        // Set the adapter and layout manager
        colorPaletteRecyclerView.layoutManager = LinearLayoutManager(context)
        colorPaletteRecyclerView.adapter = adapter
    }

    // Function to handle color selection
    var onColorSelected: (Int) -> Unit = {}
}
