package com.exam.swamiaynar.myapplication

import android.annotation.SuppressLint
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.Gravity
import android.widget.GridLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
class MainActivity : AppCompatActivity() {

    private val gridSize = 5
    private val textViews = Array(gridSize) { arrayOfNulls<TextView>(gridSize) }

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val gridLayout: GridLayout = findViewById(R.id.gridLayout)
        addPatternToGrid(gridLayout)
    }

    private fun addPatternToGrid(gridLayout: GridLayout) {
        for (i in 0 until gridSize) {
            for (j in 0 until gridSize) {
                val textView = TextView(this).apply {
                    text = String.format("%02d", i * 10 + j) // Formatting to two digits
                    gravity = Gravity.CENTER
                    setPadding(16, 16, 16, 16)
                    setBackgroundColor(resources.getColor(R.color.black))
                    setOnClickListener {
                        onTextViewClick(this)
                    }
                }

                val params = GridLayout.LayoutParams().apply {
                    rowSpec = GridLayout.spec(i)
                     columnSpec = GridLayout.spec(j)
                    //setGravity(Gravity.CENTER)
                }
                textView.layoutParams = params

                gridLayout.addView(textView)
                textViews[i][j] = textView
            }
        }
    }

    private fun onTextViewClick(textView: TextView) {
        val position = findTextViewPosition(textView)
        if (position != null) {
            blinkTextViews(position[0], position[1])
        }
    }

    private fun findTextViewPosition(textView: TextView): IntArray? {
        for (i in 0 until gridSize) {
            for (j in 0 until gridSize) {
                if (textViews[i][j] == textView) {
                    return intArrayOf(i, j)
                }
            }
        }
        return null
    }

    private fun blinkTextViews(row: Int, col: Int) {
        val handler = Handler(Looper.getMainLooper())
        val blinkCount = 6
        val blinkDuration = 100 // milliseconds

        for (i in 0 until blinkCount * 1) {
            val iteration = i
            handler.postDelayed({
                toggleBlink(row, col, iteration % 2 == 0)
            }, (i * blinkDuration).toLong())
        }
    }

    private fun toggleBlink(row: Int, col: Int, isBlinkOn: Boolean) {
        val color = if (isBlinkOn) resources.getColor(R.color.purple_700) else resources.getColor(R.color.black)
        // Horizontal and vertical
        for (i in 0 until gridSize) {
            textViews[row][i]?.setBackgroundColor(color)
            textViews[i][col]?.setBackgroundColor(color)
        }
        // Diagonals
        for (i in 0 until gridSize) {
            if (row + i < gridSize && col + i < gridSize) {
                textViews[row + i][col + i]?.setBackgroundColor(color)
            }
            if (row - i >= 0 && col - i >= 0) {
                textViews[row - i][col - i]?.setBackgroundColor(color)
            }
            if (row + i < gridSize && col - i >= 0) {
                textViews[row + i][col - i]?.setBackgroundColor(color)
            }
            if (row - i >= 0 && col + i < gridSize) {
                textViews[row - i][col + i]?.setBackgroundColor(color)
            }
        }
    }
}