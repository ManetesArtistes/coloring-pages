package com.example.manetesartistes_game.draw

import android.content.Context
import com.example.manetesartistes_game.R
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.io.File

object DrawLoader {
    // HashMap to map IDs to Draw objects
    private val drawsMap: HashMap<Int, Draw> = HashMap()

    // Load draws into the HashMap from JSON file
    fun loadDraws(context: Context) {
        if (drawsMap.isNotEmpty()) return

        val inputStream = context.resources.openRawResource(R.raw.draws)
        val jsonString = inputStream.bufferedReader().use { it.readText() }
        val gson = Gson()

        val drawListType = object : TypeToken<List<Draw>>() {}.type
        val drawList: List<Draw> = gson.fromJson(jsonString, drawListType)

        // Convert the List<Draw> to a HashMap<Int, Draw>
        drawsMap.putAll(drawList.associateBy { it.id })
    }


    // Function to get a draw by ID
    fun getDrawById(id: Int): Draw? {
        return drawsMap[id]
    }

    // Function to get all draws
    fun getAllDraws(): Collection<Draw> {
        return drawsMap.values
    }
}