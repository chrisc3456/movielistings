package com.examples.movielistings.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.examples.movielistings.data.entity.Genre
import com.examples.movielistings.data.entity.GenreMovieJoin
import com.examples.movielistings.data.entity.Movie
import com.examples.movielistings.data.entity.MovieSummary

/**
 * Movie database definition (Room DB)
 */

@Database(entities = [Genre::class, Movie::class, GenreMovieJoin::class, MovieSummary::class], version = 1, exportSchema = false)
abstract class MoviesDatabase : RoomDatabase() {
    abstract fun genreDao(): GenreDao
    abstract fun movieDao(): MovieDao
    abstract fun genreMovieDao(): GenreMovieJoinDao
    abstract fun movieSummaryDao(): MovieSummaryDao
}