package com.example.manetesartistes_game.imageEditor

import com.google.gson.annotations.SerializedName
import java.io.Serializable


data class Draw(
    val id: Int,

    @SerializedName("white_image")
    val whiteImage: String,

    @SerializedName("draw_colored_image")
    val coloredImage: String,

    @SerializedName("square_background_image")
    val squareBackgroundImage: String,

    @SerializedName("background_image")
    val backgroundImage: String,

    val colors: List<Int>
): Serializable