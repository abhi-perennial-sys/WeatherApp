package com.example.weatherapp.utils

import android.view.View
import androidx.databinding.BindingAdapter
import com.example.weatherapp.models.User
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputLayout

const val BASE_URL = "https://api.openweathermap.org/"

const val please_enter_creds = "Please enter Credentials"
const val please_enter_username = "Please enter Username"
const val please_enter_pwd = "Please enter Password"
const val please_enter_name = "Please enter Name"
const val success = "Success"
const val invalid_creds = "Invalid Credentials"
const val login_success = "Login Success"
const val no_user_found = "No User Found"
const val registration_success = "Registration Success"
const val user_already_exist = "User already exist"
const val unique_constraint_failed = "UNIQUE constraint failed"

lateinit var loggedInUser : User

fun String.showSnackBar(view: View){
    Snackbar.make(view, this, Snackbar.LENGTH_SHORT).show()
}

@BindingAdapter("app:errorText")
fun bindError(view: TextInputLayout, error: String){
    view.error = error
}