package com.example.manetesartistes_game.colors

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.io.File

object ColorLoader {
    // HashMap to map IDs to Color objects
    private val colorsMap: HashMap<Int, Color> = HashMap()

    // Load colors into the HashMap if it's empty
    private fun loadColors() {
        if (colorsMap.isNotEmpty()) return // Return if already loaded

        val jsonString = File("src/main/resources/raw/colors.json").readText()
        val gson = Gson()

        val colorListType = object : TypeToken<List<Color>>() {}.type
        val colorList: List<Color> = gson.fromJson(jsonString, colorListType)

        colorsMap.putAll(colorList.associateBy { it.id })
    }

    fun getColorById(id: Int): Color? {
        loadColors()
        return colorsMap[id]
    }

    fun getColors(): Collection<Color> {
        loadColors()
        return colorsMap.values
    }

    fun getColors(colors: List<Int>): List<Color> {
        loadColors()
        return colors.mapNotNull { colorsMap[it] }
    }
}