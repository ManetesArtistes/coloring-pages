package com.example.manetesartistes_game.colors

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.manetesartistes_game.R

class ColorAdapter(
    private val context: Context,
    private val colors: List<Color>, // Assuming Color class has id, image, name, hex properties
    private val onColorSelected: (Int) -> Unit
) : RecyclerView.Adapter<ColorAdapter.ColorViewHolder>() {

    inner class ColorViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val colorImageView: ImageView = itemView.findViewById(R.id.colorImage)
        private val colorNameTextView: TextView = itemView.findViewById(R.id.colorName)

        fun bind(color: Color) {
            val imageResId = context.resources.getIdentifier(color.image, "drawable", context.packageName)
            colorImageView.setImageResource(imageResId)

            colorNameTextView.text = color.name

            itemView.setOnClickListener {
                onColorSelected(android.graphics.Color.parseColor(color.hex))
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ColorViewHolder {
        val inflater = LayoutInflater.from(context)
        val view = inflater.inflate(R.layout.color_item, parent, false)
        return ColorViewHolder(view)
    }

    override fun onBindViewHolder(holder: ColorViewHolder, position: Int) {
        val color = colors[position]
        holder.bind(color)
    }

    override fun getItemCount(): Int {
        return colors.size
    }
}
