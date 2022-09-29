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

        initialization()
    }

    private fun initialization() {

        initBinding()
        circularProgressBar = binding.circularProgressBar

//        if (timerModeIsRest) {
//            restTimerMode()
//        } else {
//            workTimerMode()
//        }

        viewModel.liveDataSeconds.observe(this, Observer {
            binding.timerTimeSeconds.text = it
        })
        viewModel.liveDataMinutes.observe(this, Observer {
            binding.timerTimeMinutes.text = it
        })


        viewModel.liveDataIsWorking.observe(this, Observer {
            if (!it) {
                restTimerMode()
            } else {
                workTimerMode()
            }

        })
    }

    private fun initBinding() {

        binding.startBtnPause.setOnClickListener {
            binding.startBtnPlay.visibility = View.VISIBLE
            binding.startBtnPause.visibility = View.GONE
            binding.startBtnStop.visibility = View.VISIBLE

            isStartOver = false
            viewModel.timerPause()
        }

    }

    private fun restTimerMode() {
        val restMinutes = AppPreferences.getTimeToRest()
        val restMillis = minutesToMillis(restMinutes)

        circularProgressBar.progressMax = restMillis.toFloat()

        viewModel.liveDataForProgress.observe(this) {
                circularProgressBar.progress = restMillis - it.toFloat()
        }

        binding.timerTimeMinutes.text = restMinutes.toString()
        binding.labelTimerMode.text = getString(R.string.timerModeRest)
        binding.root.setBackgroundColor(ContextCompat.getColor(APP_ACTIVITY, R.color.rest))

        initBtns()

        binding.startBtnPlay.setOnClickListener {
            initBtnPlay()
            viewModel.timerStart(isStartOver, MODE_REST)
        }

        binding.startBtnStop.setOnClickListener {
            initBtns()
            binding.circularProgressBar.progress = 0f
            binding.timerTimeMinutes.text = restMinutes.toString()
            binding.timerTimeSeconds.text = "00"
            isStartOver = true
            viewModel.timerPause()
        }

        binding.startBtnSettings.setOnClickListener {
            APP_ACTIVITY.navController.navigate(R.id.action_mainFragment_to_settingsFragment)
        }

    }

    private fun workTimerMode() {
        val workMinutes = AppPreferences.getTimeToWork()
        val workMillis = minutesToMillis(workMinutes)

        circularProgressBar.progressMax = workMillis.toFloat()

        viewModel.liveDataForProgress.observe(this) {
            circularProgressBar.progress = workMillis - it.toFloat()
        }

        binding.timerTimeMinutes.text = workMinutes.toString()
        binding.labelTimerMode.text = getString(R.string.timerModeWork)
        binding.root.setBackgroundColor(ContextCompat.getColor(APP_ACTIVITY, R.color.working))

        initBtns()

        binding.startBtnPlay.setOnClickListener {
            initBtnPlay()
            viewModel.timerStart(isStartOver, MODE_WORK)
        }

        binding.startBtnStop.setOnClickListener {
            initBtns()
            binding.circularProgressBar.progress = 0f
            binding.timerTimeMinutes.text = workMinutes.toString()
            binding.timerTimeSeconds.text = "00"
            isStartOver = true
            viewModel.timerPause()
        }

        binding.startBtnSettings.setOnClickListener {
            APP_ACTIVITY.navController.navigate(R.id.action_mainFragment_to_settingsFragment)
        }

    }

    private fun initBtns() {
        binding.startBtnPlay.visibility = View.VISIBLE
        binding.startBtnStop.visibility = View.GONE
        binding.startBtnPause.visibility = View.GONE
        binding.startBtnSettings.visibility = View.VISIBLE
    }

    private fun initBtnPlay() {
        binding.startBtnPlay.visibility = View.GONE
        binding.startBtnStop.visibility = View.GONE
        binding.startBtnPause.visibility = View.VISIBLE
        binding.startBtnSettings.visibility = View.GONE
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}