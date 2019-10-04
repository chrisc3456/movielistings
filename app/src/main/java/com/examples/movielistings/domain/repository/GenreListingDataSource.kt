package com.examples.movielistings.domain.repository

import androidx.lifecycle.LiveData
import com.examples.movielistings.domain.models.GenreListing

interface GenreListingDataSource {

    /**
     * Provide a function to obtain domain level model representation of genre/movie listings
     */
    fun getGenreListings(): LiveData<List<GenreListing>>

    /**
     * Provide a function for the data source to retrieve the latest data from appropriate sources
     */
    fun refreshGenreListings()
}