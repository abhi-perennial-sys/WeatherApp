package com.example.weatherapp.repositories

import com.example.weatherapp.data.AppDatabase
import com.example.weatherapp.models.WeatherResponse
import com.example.weatherapp.rest.WeatherApi

class HomeRepository(
    private val database: AppDatabase,
    private val weatherApi: WeatherApi
) {

    suspend fun getWeatherDetails(
        lat: Double,
        lon: Double,
        appid: String
    ): com.example.weatherapp.models.Result<WeatherResponse> {
        return try {
            weatherApi.getWeatherData(
                lat,
                lon,
                appid
            )
        } catch (e: Exception) {
            com.example.weatherapp.models.Result.Error(e.message.toString())
        }
    }

    suspend fun getLoggedInUser(username: String) =
        database.getUserDao().getUser(username)

}