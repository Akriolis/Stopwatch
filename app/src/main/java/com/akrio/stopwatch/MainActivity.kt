package com.akrio.stopwatch

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.SystemClock
import android.widget.Button
import android.widget.Chronometer

class MainActivity : AppCompatActivity() {

    private lateinit var startButton: Button
    private lateinit var pauseButton: Button
    private lateinit var resetButton: Button

    lateinit var stopWatch: Chronometer

    var offset: Long = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        startButton = findViewById(R.id.start_button)
        pauseButton = findViewById(R.id.pause_button)
        resetButton = findViewById(R.id.reset_button)

        stopWatch = findViewById(R.id.stopwatch)

        var running = stopWatch.isCountDown

        startButton.setOnClickListener {
            if(!running){
                setBaseTime()
                stopWatch.start()
                running = true
            }
        }

        pauseButton.setOnClickListener {
            if (running){
                saveOffSet()
                stopWatch.stop()
                running = false
            }
        }

        resetButton.setOnClickListener {
            offset = 0
            setBaseTime()
        }
    }

    private fun setBaseTime(){
        stopWatch.base = SystemClock.elapsedRealtime() - offset
    }

    private fun saveOffSet(){
        offset = SystemClock.elapsedRealtime() - stopWatch.base
    }

}