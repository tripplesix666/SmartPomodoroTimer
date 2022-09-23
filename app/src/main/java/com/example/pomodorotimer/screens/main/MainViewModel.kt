package com.example.pomodorotimer.screens.main

import android.app.Application
import android.os.CountDownTimer
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.example.pomodorotimer.utilits.showToast
import kotlin.math.floor

class MainViewModel(application: Application): AndroidViewModel(application) {

    val liveDataSeconds = MutableLiveData<String>()
    val liveDataMinutes = MutableLiveData<String>()
    val liveDataMillisUntilFinished = MutableLiveData<String>()
    private lateinit var timer: CountDownTimer

    private val totalTime: Long = 1_500_000
    var millis = 0L

    fun timerStart(isStartOver: Boolean) {
        millis = if (isStartOver) {
            totalTime
        } else {
            liveDataMillisUntilFinished.value?.toLong() ?: totalTime
        }
        timer = object : CountDownTimer(millis, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                val second = millisToSeconds(millisUntilFinished)
                val minutes = millisToMinutes(millisUntilFinished)
                liveDataSeconds.value = second.toString()
                liveDataMinutes.value = minutes.toString()
                liveDataMillisUntilFinished.value = millisUntilFinished.toString()

            }

            override fun onFinish() {

                showToast("Завершено")
            }

        }.start()
    }

    private fun millisToMinutes(millis: Long) =
        floor((millis.toDouble() / (1000 * 60)) % 60).toInt()

    private fun millisToSeconds(millis: Long) =
        floor((millis.toDouble() / 1000) % 60).toInt()

//    fun timerResume() {
//        liveDataMillisUntilFinished.value?.let { timerStart(it.toLong()) }
//    }

    fun timerPause() {
        timer.cancel()
    }

//    fun convertAndStartTimer(minutes: Long) {
//        val millis = minutes * 60_000
//        timerStart()
//    }

    fun timerStop() {
        timerPause()
        liveDataMillisUntilFinished.value = totalTime.toString()
        liveDataMinutes.value = millisToMinutes(totalTime).toString()
        liveDataSeconds.value = millisToSeconds(totalTime).toString()
    }

}