package com.examples.movielistings.presentation.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.bumptech.glide.Glide
import com.examples.movielistings.BuildConfig
import com.examples.movielistings.R
import com.examples.movielistings.domain.models.MovieDetail
import com.examples.movielistings.presentation.factories.MovieDetailViewModelFactory
import com.examples.movielistings.presentation.viewmodels.MovieDetailViewModel
import kotlinx.android.synthetic.main.activity_movie_detail.*
import java.text.SimpleDateFormat

const val ARG_MOVIE_ID = "movieId"

class MovieDetailActivity : AppCompatActivity() {

    private lateinit var viewModel: MovieDetailViewModel
    private var movieId: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        movieId = intent.getIntExtra(ARG_MOVIE_ID, 0)
        viewModel = ViewModelProviders.of(this, MovieDetailViewModelFactory(application, movieId)).get(MovieDetailViewModel::class.java)
        addObservers()
        setContentView(R.layout.activity_movie_detail)
    }

    private fun addObservers() {
        viewModel.movieDetail.observe(this, Observer { movie ->
            displayMovieDetails(movie)
        })
    }

    private fun displayMovieDetails(movie: MovieDetail) {
        textViewTitle.text = movie.title
        textViewOverview.text = movie.overview
        textViewReleaseDate.text = "Release Date - " + SimpleDateFormat("dd/MM/yyyy").format(movie.releaseDate)
        textViewRating.text = "Rating - ${movie.rating} / 10"

        Glide.with(imageViewHeader)
            .load("${BuildConfig.TMDBBaseUrlImagePath}original/${movie.backdropImagePath}")
            .into(imageViewHeader)
    }
}
