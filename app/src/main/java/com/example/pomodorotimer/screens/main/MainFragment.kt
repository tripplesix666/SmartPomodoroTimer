package com.example.pomodorotimer.screens.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.pomodorotimer.databinding.FragmentMainBinding
import com.mikhaellopez.circularprogressbar.CircularProgressBar

class MainFragment : Fragment() {

    private var _binding: FragmentMainBinding? = null
    private val binding get() = _binding!!
    lateinit var viewModel: MainViewModel
    private lateinit var circularProgressBar: CircularProgressBar
    private var millis: Long = 1_500_000

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMainBinding.inflate(inflater, container, false)

        viewModel = ViewModelProvider(this)[MainViewModel::class.java]
        return binding.root
    }

    override fun onStart() {
        super.onStart()
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
            circularProgressBar.progress = 10_000f - it.toFloat()
        })
    }

    private fun initBinding() {
        binding.startBtnPlay.setOnClickListener {
            binding.startBtnPlay.visibility = View.GONE
            binding.startBtnPause.visibility = View.VISIBLE

            if (binding.minutesToWorking.text.isNotEmpty()) {
                val minutes = binding.minutesToWorking.text.toString().toInt()
                millis = convertMinutesToMillis(minutes)
            }
            viewModel.startTimer(millis)
        }

        binding.startBtnPause.setOnClickListener {
            binding.startBtnPlay.visibility = View.VISIBLE
            binding.startBtnPause.visibility = View.GONE

            viewModel.timerPause()
        }
    }

    private fun convertMinutesToMillis(testMinutes: Int): Long {
        val millis = testMinutes * 60_000
        return millis.toLong()
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}