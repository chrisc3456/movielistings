package com.examples.movielistings.domain.models

import java.util.*

data class MovieDetail(
    val id: Int,
    val title: String,
    val backdropImagePath: String?,
    val overview: String,
    val releaseDate: Date,
    val rating: Double
)