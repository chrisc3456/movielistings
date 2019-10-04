package com.examples.movielistings.presentation.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.examples.movielistings.R
import com.examples.movielistings.domain.models.MovieListing
import com.examples.movielistings.presentation.adapters.GenreListingAdapter
import com.examples.movielistings.presentation.utils.launch
import com.examples.movielistings.presentation.viewmodels.MovieListingViewModel
import kotlinx.android.synthetic.main.activity_movielisting.*

class MovieListingActivity : AppCompatActivity() {

    private lateinit var viewModel: MovieListingViewModel
    private val genreAdapter = GenreListingAdapter { movieListing -> onMovieListingClick(movieListing) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel = ViewModelProviders.of(this).get(MovieListingViewModel::class.java)
        addObservers()

        setContentView(R.layout.activity_movielisting)
        setupRecycler()
    }

    /**
     * Set up to observe changes to genre/movie listings results
     */
    private fun addObservers() {
        viewModel.genreMovieListings.observe(this, Observer { genreListing ->
            genreAdapter.setGenreListing(genreListing)
        })
    }

    /**
     * Set up the RecyclerView for displaying genre and movie listings
     */
    private fun setupRecycler() {
        val itemDecorator = DividerItemDecoration(this, DividerItemDecoration.VERTICAL)
        itemDecorator.setDrawable(ContextCompat.getDrawable(this,R.drawable.divider_horizontal_trans)!!)

        recyclerGenres.apply {
            addItemDecoration(itemDecorator)
            adapter = genreAdapter
            layoutManager = LinearLayoutManager(this@MovieListingActivity, RecyclerView.VERTICAL, false)
        }
    }

    /**
     * Launch a new activity for the full movie details when one is clicked
     */
    private fun onMovieListingClick(movieListing: MovieListing) {
        launch<MovieDetailActivity> { putExtra(ARG_MOVIE_ID, movieListing.id) }
    }
}
