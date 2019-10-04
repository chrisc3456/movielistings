package com.examples.movielistings.presentation.factories

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.examples.movielistings.presentation.viewmodels.MovieDetailViewModel

/**
 * Custom view model provider factory to allow set up with the movie id passed as an argument
 */
class MovieDetailViewModelFactory(private val application: Application, private val movieId: Int): ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return MovieDetailViewModel(application, movieId) as T
    }
}