package com.example.pomodorotimer.screens.settings

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.pomodorotimer.R
import com.example.pomodorotimer.databinding.FragmentSettingsBinding
import com.example.pomodorotimer.utilits.APP_ACTIVITY
import com.example.pomodorotimer.utilits.AppPreferences
import com.google.android.material.slider.Slider

class SettingsFragment : Fragment() {

    private var _binding: FragmentSettingsBinding? = null
    private val binding get() = _binding!!
    lateinit var viewModel: SettingsViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSettingsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onStart() {
        super.onStart()
        viewModel = ViewModelProvider(this)[SettingsViewModel::class.java]
        initBinding()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun initBinding() {

        binding.workingSlider.value = AppPreferences.getTimeToWork().toFloat()
        binding.timeToWork.text = binding.workingSlider.value.toInt().toString()

        binding.restSlider.value = AppPreferences.getTimeToRest().toFloat()
        binding.timeToRest.text = binding.restSlider.value.toInt().toString()

        binding.workingSlider.addOnChangeListener(Slider.OnChangeListener { _, value, _ ->
            binding.timeToWork.text = value.toInt().toString()
        })

        binding.restSlider.addOnChangeListener(Slider.OnChangeListener { _, value, _ ->
            binding.timeToRest.text = value.toInt().toString()
        })

        binding.btnSave.setOnClickListener {
            val restTime = binding.restSlider.value.toInt()
            val workTime = binding.workingSlider.value.toInt()
            AppPreferences.setTimeToRest(restTime)
            AppPreferences.setTimeToWork(workTime)
            APP_ACTIVITY.navController.navigate(R.id.action_settingsFragment_to_mainFragment)
        }

    }
}