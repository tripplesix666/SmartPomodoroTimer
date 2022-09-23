package com.example.pomodorotimer.utilits

import android.content.Context
import android.content.SharedPreferences

object AppPreferences {

    private const val NAME_PREF = "preference"
    private const val REST_TIME = "restTime"
    private const val WORK_TIME = "workTime"

    private lateinit var preferences: SharedPreferences

    fun getPreference(context: Context): SharedPreferences {
        preferences = context.getSharedPreferences(NAME_PREF, Context.MODE_PRIVATE)
        return preferences
    }

    fun setTimeToRest(restTime: Int) {
        preferences.edit()
            .putInt(REST_TIME, restTime)
            .apply()
    }

    fun setTimeToWork(workTime: Int) {
        preferences.edit()
            .putInt(WORK_TIME, workTime)
            .apply()
    }

    fun getTimeToWork(): Int {
        return preferences.getInt(WORK_TIME, 25)
    }

    fun getTimeToRest(): Int {
        return preferences.getInt(REST_TIME, 5)
    }


}