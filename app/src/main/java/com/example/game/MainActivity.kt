package com.example.game

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.content.Intent

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val buttonExploreStories = findViewById<Button>(R.id.button)
        buttonExploreStories.setOnClickListener {
            val intent = Intent(this@MainActivity, LandPage::class.java)
            startActivity(intent)
        }
    }
}
