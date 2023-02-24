package com.example.weatherapp.rest

import com.example.weatherapp.utils.BASE_URL
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitClient {

    companion object {
        @Volatile
        private var weatherApi: WeatherApi? = null
        private val LOCK = Any()

        fun getInstance(): WeatherApi {
            return weatherApi ?: synchronized(LOCK) {
                weatherApi ?: createInstance().also {
                    weatherApi = it
                }
            }
        }

        private fun createInstance(): WeatherApi = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(WeatherApi::class.java)

    }
}