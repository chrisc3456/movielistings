package com.examples.movielistings.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.examples.movielistings.domain.models.MovieListing

/**
 * Movie database class for the repository
 **/
@Entity(tableName = "Movies")
data class Movie constructor(
    @PrimaryKey @ColumnInfo(name = "id") var id: Int,
    @ColumnInfo(name = "title") var title: String,
    @ColumnInfo(name = "posterImagePath") var posterImagePath: String?
)

/**
 * Maps the appropriate fields on the database entity model to the domain level equivalent
 */
fun List<Movie>.asDomainModel(): List<MovieListing> {
    return map {
        MovieListing(
            id = it.id,
            title = it.title,
            posterImageUrl = it.posterImagePath
        )
    }
}