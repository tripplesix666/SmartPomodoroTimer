package com.example.pomodorotimer.utilits

import com.example.pomodorotimer.activities.MainActivity
import kotlin.properties.Delegates

lateinit var APP_ACTIVITY: MainActivity

const val MODE_REST = "modeRest"
const val MODE_WORK = "modeWork"

var timerModeIsRest by Delegates.notNull<Boolean>()