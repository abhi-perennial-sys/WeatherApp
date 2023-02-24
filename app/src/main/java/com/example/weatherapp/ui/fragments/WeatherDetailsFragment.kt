package com.example.weatherapp.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.example.weatherapp.R
import com.example.weatherapp.databinding.FragmentWeatherDetailsBinding
import com.example.weatherapp.ui.HomeActivity
import com.example.weatherapp.viewmodels.HomeViewModel

class WeatherDetailsFragment : Fragment() {

    lateinit var binding: FragmentWeatherDetailsBinding
    lateinit var viewModel: HomeViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        DataBindingUtil.inflate<FragmentWeatherDetailsBinding>(
            inflater,
            R.layout.fragment_weather_details,
            container,
            false
        ).also {
            binding = it
            return binding.root
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = (activity as HomeActivity).viewModel

    }

}