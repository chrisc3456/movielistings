package com.examples.movielistings.data.entity

import androidx.room.Entity
import androidx.room.ForeignKey

/**
 * Movie database class for the repository
 **/
@Entity(tableName = "GenreMovieJoin", primaryKeys = ["genreId", "movieId"])
data class GenreMovieJoin constructor(
    //@PrimaryKey(autoGenerate = true) var id: Int,
    @ForeignKey(entity = Genre::class, parentColumns = ["id"], childColumns = ["genreId"]) var genreId: Int,
    @ForeignKey(entity = Movie::class, parentColumns = ["id"], childColumns = ["movieId"]) var movieId: Int
)

/**
 * Maps the appropriate fields on the database entity model to the domain level equivalent
 */
/*fun List<GenreMovieJoin>.asDomainModel(): List<GenreMovieJoinDetails> {
    return map {
        GenreMovieJoinDetails(
            genreId = it.genreId,
            movieId = it.movieId
        )
    }
}*/