package com.example.weatherapp.data

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.weatherapp.models.User


@Dao
interface UserDao {

    @Insert
    suspend fun insertUser(user: User):Long

    @Delete
    suspend fun deleteUser(user: User)

    @Query("SELECT * FROM User WHERE username = :username")
    suspend fun getUser(username: String):User

}