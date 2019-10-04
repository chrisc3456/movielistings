package com.examples.movielistings.data.local

import android.content.Context
import androidx.room.Room

object DatabaseManager {

    private lateinit var INSTANCE: MoviesDatabase

    fun getDatabase(context: Context): MoviesDatabase {
        synchronized(MoviesDatabase::class.java) {
            if (!::INSTANCE.isInitialized) {
                INSTANCE = Room.databaseBuilder(context.applicationContext,
                    MoviesDatabase::class.java,
                    "discoverMovies").build()
            }
        }
        return INSTANCE
    }
}