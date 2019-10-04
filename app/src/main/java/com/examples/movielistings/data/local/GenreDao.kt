package com.examples.movielistings.data.local

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.examples.movielistings.data.entity.Genre

/**
 * Database Access Object interface for retrieving and manipulating genre records in the repository
 **/

@Dao
interface GenreDao {

    @Query("SELECT * FROM Genres")
    fun getGenres(): List<Genre>

    @Query("SELECT * FROM Genres")
    fun getGenresOLDLIVE(): LiveData<List<Genre>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAllGenres(genres: List<Genre>)

    @Query("SELECT COUNT(*) FROM Genres")
    fun getCount(): Int
}