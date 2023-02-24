package com.example.weatherapp.adapters

import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.weatherapp.ui.fragments.HistoryFragment
import com.example.weatherapp.ui.fragments.WeatherDetailsFragment

class ViewPagerAdapter(activity: FragmentActivity) : FragmentStateAdapter(activity) {

    override fun getItemCount() = 2

    override fun createFragment(position: Int) =
        if (position == 0) WeatherDetailsFragment() else HistoryFragment()
}