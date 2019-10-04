package com.examples.movielistings.presentation.viewmodels

import android.app.Application
import androidx.lifecycle.*
import com.examples.movielistings.data.repository.GenreListingRepository
import com.examples.movielistings.data.local.DatabaseManager
import com.examples.movielistings.domain.models.GenreListing
import com.examples.movielistings.domain.repository.GenreListingDataSource

class MovieListingViewModel(application: Application) : AndroidViewModel(application) {

    private val genreListingDataSource = getGenreListingDataSource(application)
    val genreMovieListings: LiveData<List<GenreListing>> = genreListingDataSource.getGenreListings()

    /**
     * Refresh the genre listings on initialisation
     */
    init {
        refreshGenreListings()
    }

    /**
     * Refresh the genre listings from the data source via coroutine to avoid blocking main thread
     */
    private fun refreshGenreListings() {
        genreListingDataSource.refreshGenreListings()
    }

    /**
     * Initialise the source of the genre listing data
     */
    private fun getGenreListingDataSource(application: Application): GenreListingDataSource {
        //TODO: Investigate dependency injection and use cases
        return GenreListingRepository(
            DatabaseManager.getDatabase(
                application
            )
        )
    }
}