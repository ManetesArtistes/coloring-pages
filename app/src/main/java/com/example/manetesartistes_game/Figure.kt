package com.example.manetesartistes_game

import android.graphics.Path

class Figure {
    val path: Path
    var color: Int

    constructor(path: Path, color: Int) {
        this.path = path
        this.color = color
    }
}