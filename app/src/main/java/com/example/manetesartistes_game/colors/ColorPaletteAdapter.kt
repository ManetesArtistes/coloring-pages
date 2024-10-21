package com.example.manetesartistes_game.colors

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.manetesartistes_game.R

class ColorPaletteAdapter(
    private val colors: List<Int>,
    private val onColorSelected: (Int) -> Unit
) : RecyclerView.Adapter<ColorPaletteAdapter.ColorViewHolder>() {

    private var selectedPosition = RecyclerView.NO_POSITION

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ColorViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.color_item, parent, false)
        return ColorViewHolder(view)
    }

    override fun onBindViewHolder(holder: ColorViewHolder, position: Int) {
        val color = colors[position]
        holder.bind(color, position == selectedPosition)

        // Handle color selection
        holder.itemView.setOnClickListener {
            val oldPosition = selectedPosition
            selectedPosition = position
            notifyItemChanged(oldPosition)
            notifyItemChanged(selectedPosition)

            onColorSelected(color)
        }
    }

    override fun getItemCount(): Int = colors.size

    class ColorViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(color: Int, isSelected: Boolean) {
            itemView.setBackgroundColor(color)

            itemView.alpha = if (isSelected) 0.5f else 1f
        }
    }
}
