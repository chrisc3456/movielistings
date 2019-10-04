package com.examples.movielistings.domain.usecases

import android.app.Application
import com.examples.movielistings.data.repository.GenreListingRepository
import com.examples.movielistings.data.local.DatabaseManager
import com.examples.movielistings.domain.repository.GenreListingDataSource

class GetGenreListingsUseCase(application: Application) {

    private val genreListingDataSource = getGenreListingDataSource(application)
    val genreListings = getGenreListingDataSource(application).getGenreListings()

    /**
     * Execute the use case
     */
    fun execute() {
        genreListingDataSource.refreshGenreListings()
        //return genreListingDataSource.getGenreListings()
    }

    /**
     * Initialise the source of the genre listing data
     */
    private fun getGenreListingDataSource(application: Application): GenreListingDataSource {
        //TODO: Investigate dependency injection for this
        return GenreListingRepository(
            DatabaseManager.getDatabase(
                application
            )
        )
    }
}