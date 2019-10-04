package com.examples.movielistings.domain.repository

import androidx.lifecycle.LiveData
import com.examples.movielistings.domain.models.MovieDetail

interface MovieDetailDataSource {

    /**
     * Provide a function to obtain domain level model representation of movie details
     */
    fun getMovieDetail(movieId: Int): LiveData<MovieDetail>

    /**
     * Provide a function for the data source to retrieve the latest data from appropriate sources
     */
    fun refreshMovieDetail(movieId: Int)
}