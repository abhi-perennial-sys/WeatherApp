package com.example.weatherapp.repositories

import android.util.Log
import com.example.weatherapp.data.AppDatabase
import com.example.weatherapp.models.Result
import com.example.weatherapp.models.User
import kotlinx.coroutines.delay

class LoginRepository(private val appDatabase: AppDatabase) {

    suspend fun loginUser(username: String, password: String): Result<User?> {
        return try {
            delay(1000)
            val user = appDatabase.getUserDao().getUser(username)
            Result.Success(user)
        } catch (e: Exception) {
            Result.Error(e.message.toString())
        }

    }

    suspend fun insertUser(name: String, username: String, password: String): Result<User> {
        return try {
            delay(1000)
            val user = User(name, username, password)
            appDatabase.getUserDao().insertUser(user)
            Result.Success(user)
        } catch (e: java.lang.Exception) {
            Result.Error(e.message.toString())
        }

    }

}