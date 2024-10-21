package com.example.manetesartistes_game

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.manetesartistes_game.draw.Draw
import com.example.manetesartistes_game.draw.DrawLoader
import com.example.manetesartistes_game.imageEditor.ImageEditor


class MainActivity : AppCompatActivity() {


    @SuppressLint("ClickableViewAccessibility")
    override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            setContentView(R.layout.activity_main)

                val intent = Intent(this, ImageEditor::class.java)
                DrawLoader.loadDraws(this)
                val draw: Draw? = DrawLoader.getDrawById(1);
                println("DRAW_DATA_ALL")
        println(DrawLoader.getAllDraws())
                println("DRAW_DATA")
        println(draw)
                intent.putExtra("DRAW_DATA", draw)
                intent.putExtra("IMAGE_RES_ID", R.drawable.white_fish) // Replace with your image resource ID
                startActivity(intent)


    }

}
