package com.examples.movielistings.data.remote

import com.examples.movielistings.data.entity.MovieSummary
import com.google.gson.annotations.SerializedName

/**
 * Movie model classes representing the data format in the raw source (in this case the TMDB service)
 **/
data class MovieDetailsResponse(
    @SerializedName("id") val id: Int,
    @SerializedName("title") val title: String,
    @SerializedName("backdrop_path") val backdropImagePath: String?,
    @SerializedName("overview") val overview: String,
    @SerializedName("release_date") val releaseDate: String,
    @SerializedName("vote_average") val voteAverage: Double
)

//TODO: Consider moving these two functions to a separate class/classes???

/**
 * Map to a database level model equivalent
 */
fun MovieDetailsResponse.asDatabaseModel(): MovieSummary {
    return MovieSummary(
        id = this.id,
        title = this.title,
        backdropImagePath = this.backdropImagePath,
        overview = this.overview,
        rating = this.voteAverage,
        releaseDate = this.releaseDate
    )
}