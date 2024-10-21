package com.example.manetesartistes_game.draw

data class Draw(
    val id: Int,
    val whiteImage: String,
    val coloredImage: String,
    val squareBackgroundImage: String,
    val backgroundImage: String,
    val colors: List<Int>
)