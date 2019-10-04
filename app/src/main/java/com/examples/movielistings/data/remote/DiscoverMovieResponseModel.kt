package com.examples.movielistings.data.remote

import com.examples.movielistings.data.entity.Movie
import com.google.gson.annotations.SerializedName

/**
 * Movie model classes representing the data format in the raw source (in this case the TMDB service)
 **/
data class DiscoverMovieResponse(
    @SerializedName("results") val discoverMovies: MutableList<DiscoverMovieDetailResponse>
)

data class DiscoverMovieDetailResponse(
    @SerializedName("id") val id: Int,
    @SerializedName("title") val title: String,
    @SerializedName("poster_path") val posterImagePath: String?
)

//TODO: Consider moving these two functions to a separate class/classes???

/**
 * Map to a database level model equivalent
 */
fun DiscoverMovieResponse.asDatabaseModel(): List<Movie> {
    return discoverMovies.map {
        Movie(
            id = it.id,
            title = it.title,
            posterImagePath = it.posterImagePath
        )
    }
}