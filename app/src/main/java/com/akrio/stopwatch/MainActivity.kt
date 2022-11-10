package com.akrio.stopwatch

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.SystemClock
import android.widget.Button
import android.widget.Chronometer
import com.akrio.stopwatch.databinding.ActivityMainBinding
import kotlinx.coroutines.NonCancellable.start

class MainActivity : AppCompatActivity() {

    companion object{
        private const val OFFSET_KEY = "offset"
        private const val RUNNING_KEY = "running"
        private const val BASE_KEY = "base"
    }

    private lateinit var binding: ActivityMainBinding

    private var running = false
    private var offset: Long = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        if (savedInstanceState != null){
            offset = savedInstanceState.getLong(OFFSET_KEY)
            running = savedInstanceState.getBoolean(RUNNING_KEY)
            if (running){
                binding.stopwatch.base = savedInstanceState.getLong(BASE_KEY)
                binding.stopwatch.start()
            } else setBaseTime()
        }

        binding.startButton.setOnClickListener {
            if(!running){
                setBaseTime()
                binding.stopwatch.start()
                running = true
            }
        }

        binding.pauseButton.setOnClickListener {
            if (running){
                saveOffSet()
                binding.stopwatch.stop()
                running = false
            }
        }

        binding.resetButton.setOnClickListener {
            offset = 0
            setBaseTime()
            binding.stopwatch.stop()
            running = false
        }
    }

    override fun onPause(){
        super.onPause()
        if(running){
            saveOffSet()
            binding.stopwatch.stop()
        }
    }

    override fun onResume() {
        super.onResume()
        if(running){
            setBaseTime()
            binding.stopwatch.start()
            offset = 0
        }
    }

    private fun setBaseTime(){
        binding.stopwatch.base = SystemClock.elapsedRealtime() - offset
    }

    private fun saveOffSet(){
        offset = SystemClock.elapsedRealtime() - binding.stopwatch.base
    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.putLong(OFFSET_KEY, offset)
        outState.putBoolean(RUNNING_KEY, running)
        outState.putLong(BASE_KEY,binding.stopwatch.base)
        super.onSaveInstanceState(outState)
    }

}