package com.example.game

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView

class LandPage : AppCompatActivity(),GameTask {

    lateinit var rootLayout :LinearLayout
    lateinit var  startBtn :Button
    lateinit var mGameView: GameView
    lateinit var score:TextView
    lateinit var image:ImageView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_land_page)
        startBtn = findViewById(R.id.startBtn)
        rootLayout = findViewById(R.id.rootLayout)
        score = findViewById(R.id.score)
        mGameView = GameView(this,this)
        image = findViewById(R.id.image)

        startBtn.setOnClickListener{
            mGameView.setBackgroundResource(R.drawable.sea1)
            rootLayout.addView(mGameView)
            startBtn.visibility = View.GONE
            score.visibility = View.GONE
            image.visibility = View.GONE
        }
    }

    override fun closeGame(mScore: Int) {

        score.text = "Score : $mScore"
        rootLayout.removeView(mGameView)
        startBtn.visibility=View.VISIBLE
        score.visibility = View.VISIBLE
        image.visibility =View.VISIBLE

    }


}