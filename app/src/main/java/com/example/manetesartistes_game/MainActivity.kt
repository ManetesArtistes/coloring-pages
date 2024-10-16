package com.example.manetesartistes_game

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity


class MainActivity : AppCompatActivity() {


    @SuppressLint("ClickableViewAccessibility")
    override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            setContentView(R.layout.activity_main)

                val intent = Intent(this, ImageEditor::class.java)
                intent.putExtra("IMAGE_RES_ID", R.drawable.fish) // Replace with your image resource ID
                startActivity(intent)


    }

}
