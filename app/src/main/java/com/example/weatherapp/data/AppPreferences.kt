package com.example.weatherapp.data

import android.content.Context
import android.content.SharedPreferences
import android.content.SharedPreferences.Editor

class AppPreferences(context: Context) {
    private val sharedPreferences: SharedPreferences
    private val editor: Editor
    private val IS_USER_LOGGEDIN_KEY = "isUserLoggedIn"
    private val LOGGEDIN_USERNAME_KEY = "LoggedInUsername"

    init {
        sharedPreferences = context.getSharedPreferences("ShoppingAppPreferences",
            Context.MODE_PRIVATE)
        editor = sharedPreferences.edit()
    }

    fun setUserLoggedIn(isUserLoggedIn: Boolean){
        editor.putBoolean(IS_USER_LOGGEDIN_KEY, isUserLoggedIn)
        editor.commit()
    }

    fun getUserLoggedIn(): Boolean {
        return sharedPreferences.getBoolean(IS_USER_LOGGEDIN_KEY, false)
    }

    fun setLoggedInUsername(username: String){
        editor.putString(LOGGEDIN_USERNAME_KEY, username)
        editor.commit()
    }

    fun getLoggedInUsername(): String {
        return sharedPreferences.getString(LOGGEDIN_USERNAME_KEY, "").toString()
    }


    companion object {
        private var instance: AppPreferences? = null
        private val LOCK = Any()

        operator fun invoke(context: Context) = instance ?: synchronized(LOCK){
            instance ?: createPreference(context).also {
                instance = it
            }
        }

        private fun createPreference(context: Context): AppPreferences {
            return  AppPreferences(context)
        }
    }


}