package com.example.manetesartistes_game.draw

import com.google.gson.annotations.SerializedName
import java.io.Serializable


data class Draw(
    val id: Int,

    @SerializedName("white_image")
    val whiteImage: String,

    @SerializedName("colored_image")
    val coloredImage: String,

    @SerializedName("square_background_image")
    val squareBackgroundImage: String,

    @SerializedName("background_image")
    val backgroundImage: String,

    val colors: List<Int>
): Serializable