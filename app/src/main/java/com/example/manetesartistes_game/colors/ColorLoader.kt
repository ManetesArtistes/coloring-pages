package com.example.manetesartistes_game.colors

import android.content.Context
import com.example.manetesartistes_game.R
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

object ColorLoader {
    // HashMap to map IDs to Color objects
    private val colorsMap: HashMap<Int, Color> = HashMap()

    // Load colors into the HashMap from JSON file
    fun loadColors(context: Context) {
        if (colorsMap.isNotEmpty()) return // Return if already loaded

        val inputStream = context.resources.openRawResource(R.raw.colors)
        val jsonString = inputStream.bufferedReader().use { it.readText() }
        val gson = Gson()

        val colorListType = object : TypeToken<List<Color>>() {}.type
        val colorList: List<Color> = gson.fromJson(jsonString, colorListType)

        // Convert the List<Color> to a HashMap<Int, Color>
        colorsMap.putAll(colorList.associateBy { it.id })
    }

    // Function to get a color by ID
    fun getColorById(id: Int, context: Context): Color? {
        loadColors(context) // Ensure colors are loaded before accessing
        return colorsMap[id]
    }

    // Function to get all colors
    fun getColors(context: Context): Collection<Color> {
        loadColors(context) // Ensure colors are loaded before accessing
        return colorsMap.values
    }

    // Function to get specific colors by a list of IDs
    fun getColorsByIds(colorIds: List<Int>, context: Context): List<Color> {
        loadColors(context) // Ensure colors are loaded before accessing
        return colorIds.mapNotNull { colorsMap[it] }
    }
}
