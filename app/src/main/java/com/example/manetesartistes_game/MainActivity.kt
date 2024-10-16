package com.example.manetesartistes_game

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.MotionEvent
import android.widget.Button
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import java.util.LinkedList
import java.util.Queue
import android.graphics.drawable.BitmapDrawable

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
