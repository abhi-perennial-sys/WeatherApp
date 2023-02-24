package com.example.weatherapp.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.weatherapp.models.User

@Database(entities = [User::class], version = 1)
abstract class AppDatabase : RoomDatabase() {

    abstract fun getUserDao(): UserDao

    companion object {
        private var instance: AppDatabase? = null
        private val LOCK = Any()

        operator fun invoke(context: Context):AppDatabase {
            return instance ?: synchronized(LOCK) {
                createDatabase(context).also {
                    instance = it
                }
            }
        }

        private fun createDatabase(context: Context): AppDatabase {
            return Room.databaseBuilder(
                context,
                AppDatabase::class.java,
                "app_database.db"
            ).build()
        }
    }
}