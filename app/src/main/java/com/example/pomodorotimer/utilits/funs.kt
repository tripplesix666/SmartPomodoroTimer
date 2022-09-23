package com.example.pomodorotimer.utilits

import android.widget.Toast
import kotlin.math.floor

fun showToast(message: String) {
    Toast.makeText(APP_ACTIVITY, message, Toast.LENGTH_SHORT).show()
}

fun millisToMinutes(millis: Long) =
    floor((millis.toDouble() / (1000 * 60)) % 60).toInt()

fun millisToSeconds(millis: Long) =
    floor((millis.toDouble() / 1000) % 60).toInt()

fun minutesToMillis(minutes: Int): Long {
    return minutes * 60_000L
}