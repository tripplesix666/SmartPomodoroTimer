package com.example.pomodorotimer.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.MutableLiveData
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.example.pomodorotimer.R
import com.example.pomodorotimer.utilits.APP_ACTIVITY
import com.example.pomodorotimer.utilits.AppPreferences
import com.example.pomodorotimer.utilits.timerModeIsRest

class MainActivity : AppCompatActivity() {

    lateinit var navController: NavController
    val liveDataIsRest = MutableLiveData<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        timerModeIsRest = true
        APP_ACTIVITY = this
        navController = Navigation.findNavController(this, R.id.navHostFragmentContainer)
        AppPreferences.getPreference(this)

        var bool = true
    }
}