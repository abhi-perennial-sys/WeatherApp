package com.example.weatherapp.models

import androidx.annotation.NonNull
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class User(
    val name: String,
    @NonNull
    @PrimaryKey
    val username: String,
    val password: String
)
