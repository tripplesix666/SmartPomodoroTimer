package com.example.pomodorotimer.screens.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.pomodorotimer.R
import com.example.pomodorotimer.databinding.FragmentMainBinding
import com.example.pomodorotimer.utilits.APP_ACTIVITY
import com.mikhaellopez.circularprogressbar.CircularProgressBar

class MainFragment : Fragment() {

    private var _binding: FragmentMainBinding? = null
    private val binding get() = _binding!!
    lateinit var viewModel: MainViewModel
    private lateinit var circularProgressBar: CircularProgressBar
    private var millis: Long = 1_500_000
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
        circularProgressBar = binding.circularProgressBar
        circularProgressBar.apply {
            progressMax = millis.toFloat()
        }
    }

    private fun initialization() {

        initBinding()

        viewModel.liveDataSeconds.observe(this, Observer {
            binding.startTimeSeconds.text = it
        })
        viewModel.liveDataMinutes.observe(this, Observer {
            binding.startTimeMinutes.text = it
        })
        viewModel.liveDataMillisUntilFinished.observe(this, Observer {
            millis = it.toLong()
            circularProgressBar.progress = 1_500_000 - it.toFloat()
        })
    }

    private fun initBinding() {
        binding.startBtnPlay.setOnClickListener {
            binding.startBtnPlay.visibility = View.GONE
            binding.startBtnPause.visibility = View.VISIBLE
            binding.startBtnStop.visibility = View.GONE
            binding.startBtnSettings.visibility = View.GONE

            viewModel.timerStart(isStartOver)
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

}