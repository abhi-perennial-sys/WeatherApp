package com.example.weatherapp.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weatherapp.models.User
import com.example.weatherapp.repositories.HomeRepository
import kotlinx.coroutines.launch

class HomeViewModel(private val repository: HomeRepository) : ViewModel() {

    private val _userLiveData: MutableLiveData<User> = MutableLiveData()
    val userLiveData: LiveData<User> = _userLiveData


    fun getWeatherDetails(lat: Double, lon: Double, appid: String){
        viewModelScope.launch{
            val result = repository.getWeatherDetails(lat, lon, appid)

        }
    }

    fun getLoggedInUser(username: String) {
        viewModelScope.launch {
            _userLiveData.postValue(repository.getLoggedInUser(username))
        }
    }
}