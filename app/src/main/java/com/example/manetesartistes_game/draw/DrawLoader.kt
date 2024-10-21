package com.example.manetesartistes_game.draw

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.io.File

object DrawLoader {
    // HashMap to map IDs to Draw objects
    private val drawsMap: HashMap<Int, Draw> = HashMap()

    // Load draws into the HashMap from JSON file
    private fun loadDraws() {
        if (drawsMap.isNotEmpty()) return // Return if already loaded

        val jsonString = File("src/main/resources/raw/draws.json").readText()
        val gson = Gson()

        val drawListType = object : TypeToken<List<Draw>>() {}.type
        val drawList: List<Draw> = gson.fromJson(jsonString, drawListType)

        // Convert the List<Draw> to a HashMap<Int, Draw>
        drawsMap.putAll(drawList.associateBy { it.id })
    }

    // Function to get a draw by ID
    fun getDrawById(id: Int): Draw? {
        loadDraws() // Ensure draws are loaded before accessing
        return drawsMap[id]
    }

    // Function to get all draws
    fun getAllDraws(): Collection<Draw> {
        loadDraws() // Ensure draws are loaded before accessing
        return drawsMap.values
    }
}