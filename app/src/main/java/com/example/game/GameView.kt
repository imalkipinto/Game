package com.example.game

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Rect
import android.graphics.drawable.Drawable
import android.view.MotionEvent
import android.view.View

class GameView(var c: Context, var gameTask: GameTask) : View(c) {
    private var myPaint: Paint? = null
    private var speed = 1
    private var time = 0
    private var score = 0
    private var myFishPosition = 0f // Changed to Float
    private val otherFish = ArrayList<HashMap<String, Any>>()

    private var fish1Drawable: Drawable? = null
    private var fish2Drawable: Drawable? = null

    var viewWidth = 0
    var viewHeight = 0

    init {
        myPaint = Paint()
        fish1Drawable = resources.getDrawable(R.drawable.fish1, null)
        fish2Drawable = resources.getDrawable(R.drawable.net2, null)
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        viewWidth = this.measuredWidth
        viewHeight = this.measuredHeight

        if (time % 700 < 10 + speed) {
            val map = HashMap<String, Any>()
            map["lane"] = (0..2).random()
            map["startTime"] = time
            otherFish.add(map)
        }

        time += 10 + speed
        val carWidth = viewWidth / 5
        val carHeight = carWidth + 10

        // Draw player fish
        val fish1Left = myFishPosition.toInt() // Convert to Int
        val fish1Top = viewHeight - 2 - carHeight
        fish1Drawable?.setBounds(
            fish1Left,
            fish1Top,
            fish1Left + carWidth - 50,
            fish1Top + carHeight
        )
        fish1Drawable?.draw(canvas)

        // Draw other fish
        for (i in otherFish.indices) {
            try {
                val fishX = otherFish[i]["lane"] as Int * viewWidth / 3 + viewWidth / 15
                val fishY = time - otherFish[i]["startTime"] as Int
                fish2Drawable?.setBounds(
                    fishX + 25,
                    fishY - carHeight,
                    fishX + carWidth - 25,
                    fishY
                )
                fish2Drawable?.draw(canvas)

                // Collision detection
                if (fish1Left < fishX + carWidth - 25 && fish1Left + carWidth - 50 > fishX + 25 &&
                    fish1Top < fishY && fish1Top + carHeight > fishY - carHeight
                ) {
                    gameTask.closeGame(score)
                    return
                }

                if (fishY > viewHeight + carHeight) {
                    otherFish.removeAt(i)
                    score++
                    speed = 1 + Math.abs(score / 8)
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }

        myPaint!!.color = Color.WHITE
        myPaint!!.textSize = 40f
        canvas.drawText("Score: $score", 80f, 80f, myPaint!!)
        canvas.drawText("Speed : $speed", 380f, 80f, myPaint!!)
        invalidate()
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        when (event!!.action) {
            MotionEvent.ACTION_DOWN -> {
                val x1 = event.x
                if (x1 < viewWidth / 2 && myFishPosition > 0) {
                    myFishPosition -= viewWidth / 3 // Move left
                }
                if (x1 > viewWidth / 2 && myFishPosition < (viewWidth / 3) * 2) {
                    myFishPosition += viewWidth / 3 // Move right
                }
                invalidate()
            }
            MotionEvent.ACTION_UP -> {
            }
        }
        return true
    }
}
