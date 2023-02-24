package com.example.weatherapp.ui

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.example.weatherapp.R
import com.example.weatherapp.adapters.ViewPagerAdapter
import com.example.weatherapp.data.AppDatabase
import com.example.weatherapp.data.AppPreferences
import com.example.weatherapp.databinding.ActivityHomeBinding
import com.example.weatherapp.repositories.HomeRepository
import com.example.weatherapp.rest.RetrofitClient
import com.example.weatherapp.utils.loggedInUser
import com.example.weatherapp.viewmodels.HomeViewModel
import com.example.weatherapp.viewmodels.HomeViewModelFactory
import com.google.android.material.tabs.TabLayoutMediator

class HomeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHomeBinding
    lateinit var viewModel: HomeViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        DataBindingUtil.setContentView<ActivityHomeBinding>(
            this,
            R.layout.activity_home
        ).also {
            binding = it
        }

        setSupportActionBar(binding.toolbar)

        val database = AppDatabase(applicationContext)
        val weatherApi = RetrofitClient.getInstance()
        val repository = HomeRepository(database, weatherApi)
        viewModel = ViewModelProvider(
            this,
            HomeViewModelFactory(repository)
        )[HomeViewModel::class.java]

        val adapter = ViewPagerAdapter(this)
        binding.viewpager2.adapter = adapter
        TabLayoutMediator(
            binding.tabLayout,
            binding.viewpager2
        ) { tab, position ->
            tab.text = if (position == 0) "Weather Details" else "Weather History"
        }.attach()

        viewModel.getLoggedInUser(
            AppPreferences(applicationContext).getLoggedInUsername()
        )

        subscribeUi()
    }

    private fun subscribeUi() {
        viewModel.userLiveData.observe(this) {
            loggedInUser = it
            binding.toolbar.title = "Hey ${loggedInUser.name}"
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.logout) {
            logoutUser()
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    private fun logoutUser() {
        AppPreferences(applicationContext).setUserLoggedIn(false)
        startActivity(Intent(this, LoginActivity::class.java))
        finishAffinity()
    }
}