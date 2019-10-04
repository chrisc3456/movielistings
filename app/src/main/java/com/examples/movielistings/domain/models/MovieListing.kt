package com.examples.movielistings.domain.models

data class MovieListing(
    val id: Int,
    val title: String,
    val posterImageUrl: String?
)