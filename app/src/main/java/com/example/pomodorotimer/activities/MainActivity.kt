package com.example.pomodorotimer.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.example.pomodorotimer.R
import com.example.pomodorotimer.utilits.APP_ACTIVITY

class MainActivity : AppCompatActivity() {

    lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        APP_ACTIVITY = this
        navController = Navigation.findNavController(this, R.id.navHostFragmentContainer)
    }
}