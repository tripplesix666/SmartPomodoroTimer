package com.example.pomodorotimer.screens.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.pomodorotimer.R
import com.example.pomodorotimer.databinding.FragmentMainBinding
import com.example.pomodorotimer.utilits.*
import com.mikhaellopez.circularprogressbar.CircularProgressBar

class MainFragment : Fragment() {

    private var _binding: FragmentMainBinding? = null
    private val binding get() = _binding!!
    lateinit var viewModel: MainViewModel
    private lateinit var circularProgressBar: CircularProgressBar
    private var isStartOver = true

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMainBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onStart() {
        super.onStart()
        viewModel = ViewModelProvider(this)[MainViewModel::class.java]

        val minutes = AppPreferences.getTimeToWork()
        val millis = minutesToMillis(minutes)
        circularProgressBar = binding.circularProgressBar
        circularProgressBar.apply {
            progressMax = millis.toFloat()
        }
        initialization()
    }

    private fun initialization() {

        if(timerModeIsRest) {
            restTimerMode()
        } else {
            workTimerMode()
        }

        viewModel.liveDataSeconds.observe(this, Observer {
            binding.timerTimeSeconds.text = it
        })
        viewModel.liveDataMinutes.observe(this, Observer {
            binding.timerTimeMinutes.text = it
        })
        viewModel.liveDataMillisUntilFinished.observe(this, Observer {
            val minutes = AppPreferences.getTimeToWork()
            val millis = minutesToMillis(minutes)
            circularProgressBar.progress = millis - it.toFloat()
        })

        viewModel.liveDataIsWorking.observe(this, Observer {
            if (!it) {
                restTimerMode()
            } else {
                workTimerMode()
            }
        })
    }

    private fun restTimerMode() {
        val workTime = AppPreferences.getTimeToRest()
        binding.timerTimeMinutes.text = workTime.toString()

        binding.root.setBackgroundColor(ContextCompat.getColor(APP_ACTIVITY, R.color.rest))
        binding.labelTimerMode.text = getString(R.string.timerModeRest)

        binding.startBtnStop.visibility = View.GONE
        binding.startBtnPause.visibility = View.GONE
        binding.startBtnPlay.visibility = View.VISIBLE
        binding.startBtnSettings.visibility = View.VISIBLE

        binding.startBtnStop.setOnClickListener {
            binding.startBtnStop.visibility = View.GONE
            binding.startBtnPause.visibility = View.GONE
            binding.startBtnPlay.visibility = View.VISIBLE
            binding.startBtnSettings.visibility = View.VISIBLE

            isStartOver = true
            viewModel.timerStop()
        }


        binding.startBtnPause.setOnClickListener {
            binding.startBtnPlay.visibility = View.VISIBLE
            binding.startBtnPause.visibility = View.GONE
            binding.startBtnStop.visibility = View.VISIBLE

            isStartOver = false
            viewModel.timerPause()
        }

        binding.startBtnSettings.setOnClickListener {
            APP_ACTIVITY.navController.navigate(R.id.action_mainFragment_to_settingsFragment)
        }

        isStartOver = true

        binding.startBtnPlay.setOnClickListener {
            binding.startBtnPlay.visibility = View.GONE
            binding.startBtnPause.visibility = View.VISIBLE
            binding.startBtnStop.visibility = View.GONE
            binding.startBtnSettings.visibility = View.GONE

            viewModel.timerStart(isStartOver, MODE_REST)
        }

        val minutes = AppPreferences.getTimeToWork()
        val millis = minutesToMillis(minutes)
        circularProgressBar.progress = millis.toFloat()
    }

    private fun workTimerMode() {
        val workTime = AppPreferences.getTimeToWork()
        binding.timerTimeMinutes.text = workTime.toString()

        binding.labelTimerMode.text = getString(R.string.timerModeWork)

        binding.startBtnPause.visibility = View.GONE
        binding.startBtnPlay.visibility = View.VISIBLE
        binding.startBtnSettings.visibility = View.VISIBLE


        binding.root.setBackgroundColor(ContextCompat.getColor(APP_ACTIVITY, R.color.working))

        binding.startBtnPlay.setOnClickListener {
            binding.startBtnPlay.visibility = View.GONE
            binding.startBtnPause.visibility = View.VISIBLE
            binding.startBtnStop.visibility = View.GONE
            binding.startBtnSettings.visibility = View.GONE

            viewModel.timerStart(isStartOver, MODE_WORK)
        }

        binding.startBtnPause.setOnClickListener {
            binding.startBtnPlay.visibility = View.VISIBLE
            binding.startBtnPause.visibility = View.GONE
            binding.startBtnStop.visibility = View.VISIBLE

            isStartOver = false
            viewModel.timerPause()
        }

        binding.startBtnStop.setOnClickListener {
            binding.startBtnStop.visibility = View.GONE
            binding.startBtnPause.visibility = View.GONE
            binding.startBtnPlay.visibility = View.VISIBLE
            binding.startBtnSettings.visibility = View.VISIBLE

            isStartOver = true
            viewModel.timerStop()
        }

        binding.startBtnSettings.setOnClickListener {
            APP_ACTIVITY.navController.navigate(R.id.action_mainFragment_to_settingsFragment)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    fun restMode() {

    }

}