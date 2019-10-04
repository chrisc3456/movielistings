package com.examples.movielistings.data.local

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.examples.movielistings.data.entity.MovieSummary

/**
 * Database Access Object interface for retrieving and manipulating movie summary records in the repository
 **/

@Dao
interface MovieSummaryDao {

    @Query("""SELECT * FROM MovieSummaries
            WHERE MovieSummaries.id = :movieId""")
    fun getMovieSummary(movieId: Int): LiveData<MovieSummary>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertMovieSummary(summary: MovieSummary)
}