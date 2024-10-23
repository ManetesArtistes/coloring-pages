package com.example.manetesartistes_game.colors

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.manetesartistes_game.R

class ColorAdapter(
    private val context: Context,
    private val colors: List<Color>, // Assuming Color class has id, image, name, hex properties
    private val onColorSelected: (Int) -> Unit
) : RecyclerView.Adapter<ColorAdapter.ColorViewHolder>() {

    inner class ColorViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val colorImageView: ImageView = itemView.findViewById(R.id.colorImage)

        fun bind(color: Color, position: Int) {
            val imageResId = context.resources.getIdentifier(color.image, "drawable", context.packageName)

            if (imageResId == 0) {
                println("Image not found: ${color.image}")
            }
            colorImageView.setImageResource(imageResId)

            // Apply a top margin of 10dp only to the first item
            if (position == 0) {
                val layoutParams = itemView.layoutParams as ViewGroup.MarginLayoutParams
                layoutParams.topMargin = context.resources.getDimensionPixelSize(R.dimen.top_margin_firstcol)
                itemView.layoutParams = layoutParams
            } else {
                // Reset the margin for other items
                val layoutParams = itemView.layoutParams as ViewGroup.MarginLayoutParams
                layoutParams.topMargin = 0
                itemView.layoutParams = layoutParams
            }

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
        holder.bind(color, position)
    }

    override fun getItemCount(): Int {
        return colors.size
    }
}
