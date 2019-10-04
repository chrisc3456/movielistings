package com.examples.movielistings.presentation.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.examples.movielistings.R
import com.examples.movielistings.domain.models.GenreListing
import com.examples.movielistings.domain.models.MovieListing
import kotlinx.android.synthetic.main.item_genre.view.*

class GenreListingAdapter(private val movieClicked: (MovieListing) -> Unit): RecyclerView.Adapter<GenreListingAdapter.GenreViewHolder>() {

    private var genreListings: List<GenreListing> = emptyList()
    private lateinit var parentContext: Context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GenreViewHolder {
        parentContext = parent.context
        val layoutInflater = LayoutInflater.from(parentContext)
        return GenreViewHolder(layoutInflater.inflate(R.layout.item_genre, parent, false))
    }

    override fun getItemCount(): Int {
        return genreListings.size
    }

    override fun onBindViewHolder(holder: GenreViewHolder, position: Int) {
        val currentListing = genreListings[position]
        holder.itemView.textViewGenre.text = currentListing.name

        val movieAdapter = MovieListingAdapter(movieClicked)
        holder.recyclerMovie.apply {
            layoutManager = LinearLayoutManager(holder.itemView.context, RecyclerView.HORIZONTAL, false)
            adapter = movieAdapter
            setRecycledViewPool(RecyclerView.RecycledViewPool())
        }

        // Set up the nested recycler to manage the discoverMovies in a horizontal display
        if (holder.recyclerMovie.itemDecorationCount == 0) {
            val itemDecorator = DividerItemDecoration(parentContext, DividerItemDecoration.HORIZONTAL)
            itemDecorator.setDrawable(ContextCompat.getDrawable(parentContext, R.drawable.divider_vertical_trans)!!)
            holder.recyclerMovie.addItemDecoration(itemDecorator)
        }

        movieAdapter.setMovieListing(currentListing.relatedMovies)
    }

    fun setGenreListing(genres: List<GenreListing>) {
        this.genreListings = genres
        notifyDataSetChanged()
    }

    inner class GenreViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val recyclerMovie = itemView.recyclerMovies!!
    }
}