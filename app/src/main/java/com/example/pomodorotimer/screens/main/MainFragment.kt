package com.example.pomodorotimer.screens.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.pomodorotimer.databinding.FragmentMainBinding

class MainFragment : Fragment() {

    private var _binding: FragmentMainBinding? = null
    private val binding get() = _binding!!
    lateinit var viewModel: MainViewModel

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
        val circularProgressBar = binding.circularProgressBar
        circularProgressBar.apply {
            progress = 10f
        }
    }

    private fun initialization() {
        binding.startBtnStart.setOnClickListener {
            viewModel.startTimer()
        }
        viewModel.liveDataSeconds.observe(this, Observer {
            binding.startTimeSeconds.text = it
        })
        viewModel.liveDataMinutes.observe(this, Observer {
            binding.startTimeMinutes.text = it
        })
    }



    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}