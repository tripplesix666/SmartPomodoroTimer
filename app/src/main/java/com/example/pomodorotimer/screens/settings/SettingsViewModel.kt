package com.example.pomodorotimer.screens.settings

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData

class SettingsViewModel(application: Application): AndroidViewModel(application) {

    val liveDataRestTime = MutableLiveData<String>()
}