package com.examples.movielistings.presentation.viewmodels

import android.app.Application
import androidx.lifecycle.*
import com.examples.movielistings.data.local.DatabaseManager
import com.examples.movielistings.data.repository.MovieDetailRepository
import com.examples.movielistings.domain.models.MovieDetail
import com.examples.movielistings.domain.repository.MovieDetailDataSource

class MovieDetailViewModel(application: Application, private val movieId: Int) : AndroidViewModel(application) {

    private val movieDetailDataSource = getMovieDetailDataSource(application)

    val movieDetail: LiveData<MovieDetail> = movieDetailDataSource.getMovieDetail(movieId)

    /**
     * Refresh the movie detail on initialisation
     */
    init {
        refreshMovieDetail()
    }

    /**
     * Refresh the genre listings from the data source via coroutine to avoid blocking main thread
     */
    private fun refreshMovieDetail() {
        movieDetailDataSource.refreshMovieDetail(movieId)
    }

    /**
     * Initialise the source of the movie detail data
     */
    private fun getMovieDetailDataSource(application: Application): MovieDetailDataSource {
        //TODO: Investigate dependency injection and use cases
        return MovieDetailRepository(
            DatabaseManager.getDatabase(
                application
            )
        )
    }
}