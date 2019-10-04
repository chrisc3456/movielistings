package com.examples.movielistings.data.remote

import com.examples.movielistings.data.entity.Genre
import com.google.gson.annotations.SerializedName

/**
 * Genre model classes representing the data format in the raw source (in this case the TMDB service)
 **/
data class GenreResponse(
    @SerializedName("genres") val genres: MutableList<GenreDetailResponse>
)

data class GenreDetailResponse(
    @SerializedName("id") val id: Int,
    @SerializedName("name") val name: String
)

//TODO: Consider moving these two functions to a separate class/classes

/**
 * Map to a domain level model equivalent
 */
/*fun GenreResponse.asDomainModel(): List<GenreDetails> {
    return genres.map {
        GenreDetails(
            id = it.id,
            name = it.name,
            relatedMovies = mutableListOf()
            )
    }
}*/

/**
 * Map to a database level model equivalent
 */
fun GenreResponse.asDatabaseModel(): List<Genre> {
    return genres.map {
        Genre(
            id = it.id,
            name = it.name
        )
    }
}