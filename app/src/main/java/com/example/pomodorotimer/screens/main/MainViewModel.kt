package com.example.pomodorotimer.screens.main

import android.app.Application
import android.os.CountDownTimer
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.example.pomodorotimer.utilits.*

class MainViewModel(application: Application) : AndroidViewModel(application) {

    val liveDataSeconds = MutableLiveData<String>()
    val liveDataMinutes = MutableLiveData<String>()
    val liveDataForProgress = MutableLiveData<String>()
    val liveDataForProgressRest = MutableLiveData<String>()
    private lateinit var timer: CountDownTimer
    val liveDataIsWorking = MutableLiveData<Boolean>()

    init {
        liveDataIsWorking.value = true
    }

    fun timerStart(isStartOver: Boolean, modeTimer: String) {

        when (modeTimer) {
            MODE_REST -> {
                val minutes = AppPreferences.getTimeToRest()
                val millis = minutesToMillis(minutes)
                if (isStartOver) {
                    startTimer(millis)
                } else {
                    val millis = liveDataForProgress.value?.toLong()
                    millis?.let { startTimer(it) }
                }
            }
            MODE_WORK -> {
                val minutes = AppPreferences.getTimeToWork()
                val millis = minutesToMillis(minutes)
                if (isStartOver) {
                    startTimer(millis)
                } else {
                    val millis = liveDataForProgress.value?.toLong()
                    millis?.let { startTimer(it) }
                }
            }
        }

    }

    private fun startTimer(millisForTimer: Long) {
        timer = object : CountDownTimer(millisForTimer, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                val second = millisToSeconds(millisUntilFinished)
                val minutes = millisToMinutes(millisUntilFinished)
                liveDataSeconds.value = second.toString()
                liveDataMinutes.value = minutes.toString()
                liveDataForProgress.value = millisUntilFinished.toString()

            }

            override fun onFinish() {
//                timerModeIsRest = !timerModeIsRest
//                liveDataIsWorking.value = !timerModeIsRest
                liveDataIsWorking.value = !liveDataIsWorking.value!!

                showToast("Завершено")
            }

        }.start()
    }

    fun timerPause() {
        timer.cancel()
    }

}