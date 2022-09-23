package com.example.pomodorotimer.screens.main

import android.app.Application
import android.os.CountDownTimer
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.example.pomodorotimer.utilits.*

class MainViewModel(application: Application) : AndroidViewModel(application) {

    val liveDataSeconds = MutableLiveData<String>()
    val liveDataMinutes = MutableLiveData<String>()
    val liveDataMillisUntilFinished = MutableLiveData<String>()
    private lateinit var timer: CountDownTimer

    fun timerStart(isStartOver: Boolean) {
        val minutes = AppPreferences.getTimeToWork()
        val millis = minutesToMillis(minutes)
        val millisForTimer = if (isStartOver) {
            millis
        } else {
            liveDataMillisUntilFinished.value?.toLong() ?: millis
        }
        timer = object : CountDownTimer(millisForTimer, 1000) {
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

    fun timerPause() {
        timer.cancel()
    }

    fun timerStop() {
        timerPause()
        val minutes = AppPreferences.getTimeToWork()
        val millis = minutesToMillis(minutes)
        liveDataMillisUntilFinished.value = millis.toString()
        liveDataMinutes.value = millisToMinutes(millis).toString()
        liveDataSeconds.value = millisToSeconds(millis).toString()
    }

}