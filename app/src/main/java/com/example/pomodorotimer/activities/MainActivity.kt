package com.example.pomodorotimer.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.pomodorotimer.R
import com.example.pomodorotimer.utilits.APP_ACTIVITY

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        APP_ACTIVITY = this
    }
}