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

    fun startTimer(millisUntilFinished: Long) {
        showToast("$millisUntilFinished")
        object : CountDownTimer(millisUntilFinished, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                val second = floor((millisUntilFinished.toDouble() / 1000) % 60).toInt()
                val minutes = floor((millisUntilFinished.toDouble() / (1000 * 60)) % 60).toInt()
                liveDataSeconds.value = second.toString()
                liveDataMinutes.value = minutes.toString()
                liveDataMillisUntilFinished.value = millisUntilFinished.toString()

            }

            override fun onFinish() {

                showToast("Завершено")
            }

        }.start()
    }

}