package com.examples.movielistings.presentation.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.examples.movielistings.BuildConfig
import com.examples.movielistings.R
import com.examples.movielistings.domain.models.MovieListing
import kotlinx.android.synthetic.main.item_movie.view.*

class MovieListingAdapter(private val movieClicked: (MovieListing) -> Unit): RecyclerView.Adapter<MovieListingAdapter.MovieViewHolder>() {

    private var movies: List<MovieListing> = emptyList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return MovieViewHolder(layoutInflater.inflate(R.layout.item_movie, parent, false))
    }

    override fun getItemCount(): Int {
        return movies.size
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        val movieListingEntry = movies[position]
        holder.setListenerForMovieClick(movieListingEntry, movieClicked)

        Glide.with(holder.itemView.imageViewPoster)
            .load("${BuildConfig.TMDBBaseUrlImagePath}w185/${movieListingEntry.posterImageUrl}")
            .into(holder.itemView.imageViewPoster)
    }

    fun setMovieListing(movies: List<MovieListing>) {
        this.movies = movies
    }

    inner class MovieViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun setListenerForMovieClick(movieListing: MovieListing, listener: (MovieListing) -> Unit) {
            itemView.imageViewPoster.setOnClickListener { listener(movieListing) }
        }
    }
}