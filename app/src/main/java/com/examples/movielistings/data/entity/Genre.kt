package com.examples.movielistings.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.examples.movielistings.domain.models.GenreListing

/**
 * Genre database class for the repository
 **/
@Entity(tableName = "Genres")
data class Genre constructor(
    @PrimaryKey @ColumnInfo(name = "id") var id: Int,
    @ColumnInfo(name = "name") var name: String
)

/**
 * Maps the appropriate fields on the database entity model to the domain level equivalent
 */
fun List<Genre>.asDomainModel(relatedMovies: List<Movie>): List<GenreListing> {
    return map {
        GenreListing(
            name = it.name,
            relatedMovies = relatedMovies.asDomainModel()
        )
    }
}