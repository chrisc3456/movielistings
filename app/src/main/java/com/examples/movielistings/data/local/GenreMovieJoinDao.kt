package com.examples.movielistings.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.examples.movielistings.data.entity.GenreMovieJoin
import com.examples.movielistings.data.entity.Movie

/**
 * Database Access Object interface for retrieving and manipulating genre records in the repository
 **/

@Dao
interface GenreMovieJoinDao {

    @Query("""SELECT Movies.id, Movies.title, Movies.posterImagePath
            FROM GenreMovieJoin
            INNER JOIN Movies
            ON GenreMovieJoin.movieId = Movies.id
            WHERE GenreMovieJoin.genreId = :genreId""")
    fun getMoviesForGenre(genreId: Int): List<Movie>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertRelationship(genreMovieJoin: GenreMovieJoin)


    @Query("SELECT COUNT(*) FROM GenreMovieJoin")
    fun getCount(): Int
}