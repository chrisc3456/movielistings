package com.examples.movielistings.data.local

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.examples.movielistings.data.entity.Movie

/**
 * Database Access Object interface for retrieving and manipulating genre records in the repository
 **/

@Dao
interface MovieDao {

    @Query("SELECT * FROM Movies")
    fun getMovies(): LiveData<List<Movie>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAllMovies(movies: List<Movie>)

    @Query("SELECT COUNT(*) FROM Movies")
    fun getCount(): Int
}