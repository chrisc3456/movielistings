package com.examples.movielistings.domain.models

data class GenreListing(
    val name: String,
    val relatedMovies: List<MovieListing>
)