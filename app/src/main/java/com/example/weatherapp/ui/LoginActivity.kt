package com.example.weatherapp.ui

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.ViewModelProvider
import com.example.weatherapp.R
import com.example.weatherapp.data.AppDatabase
import com.example.weatherapp.data.AppPreferences
import com.example.weatherapp.repositories.LoginRepository
import com.example.weatherapp.viewmodels.LoginViewModel
import com.example.weatherapp.viewmodels.LoginViewModelFactory

class LoginActivity : AppCompatActivity() {

    lateinit var viewModel: LoginViewModel

    override fun onCreate(savedInstanceState: Bundle?) {

        val splashScreen = installSplashScreen()
        splashScreen.setKeepOnScreenCondition {
            false
        }
        if (AppPreferences(applicationContext).getUserLoggedIn()) {
            startActivity(Intent(this@LoginActivity,
                HomeActivity::class.java))
            finish()
            splashScreen.setKeepOnScreenCondition{
                true
            }
        }

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val appDatabase = AppDatabase(applicationContext)
        val factory = LoginViewModelFactory(LoginRepository(appDatabase))
        viewModel = ViewModelProvider(this, factory)[LoginViewModel::class.java]

    }

}