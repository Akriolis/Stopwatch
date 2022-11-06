package com.akrio.stopwatch

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.SystemClock
import android.widget.Button
import android.widget.Chronometer

class MainActivity : AppCompatActivity() {

    companion object{
        private const val OFFSET_KEY = "offset"
        private const val RUNNING_KEY = "running"
        private const val BASE_KEY = "base"
    }

    private lateinit var startButton: Button
    private lateinit var pauseButton: Button
    private lateinit var resetButton: Button

    private lateinit var stopWatch: Chronometer

    private var running = false
    private var offset: Long = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        startButton = findViewById(R.id.start_button)
        pauseButton = findViewById(R.id.pause_button)
        resetButton = findViewById(R.id.reset_button)
        stopWatch = findViewById(R.id.stopwatch)

        if (savedInstanceState != null){
            offset = savedInstanceState.getLong(OFFSET_KEY)
            running = savedInstanceState.getBoolean(RUNNING_KEY)
            if (running){
                stopWatch.base = savedInstanceState.getLong(BASE_KEY)
                stopWatch.start()
            } else setBaseTime()
        }

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
            stopWatch.stop()
            running = false
        }
    }

    override fun onPause(){
        super.onPause()
        if(running){
            saveOffSet()
            stopWatch.stop()
        }
    }

    override fun onResume() {
        super.onResume()
        if(running){
            setBaseTime()
            stopWatch.start()
            offset = 0
        }
    }

    private fun setBaseTime(){
        stopWatch.base = SystemClock.elapsedRealtime() - offset
    }

    private fun saveOffSet(){
        offset = SystemClock.elapsedRealtime() - stopWatch.base
    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.putLong(OFFSET_KEY, offset)
        outState.putBoolean(RUNNING_KEY, running)
        outState.putLong(BASE_KEY,stopWatch.base)
        super.onSaveInstanceState(outState)
    }

}