package com.examples.movielistings.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.examples.movielistings.domain.models.MovieDetail
import java.text.SimpleDateFormat
import java.util.*

/**
 * Movie database class for the repository
 **/
@Entity(tableName = "MovieSummaries")
data class MovieSummary constructor(
    @PrimaryKey @ColumnInfo(name = "id") var id: Int,
    @ColumnInfo(name = "title") var title: String,
    @ColumnInfo(name = "overview") var overview: String,
    @ColumnInfo(name = "rating") var rating: Double,
    @ColumnInfo(name = "releaseDate") var releaseDate: String,
    @ColumnInfo(name = "backdropImagePath") var backdropImagePath: String?
)

/**
 * Maps the appropriate fields on the database entity model to the domain level equivalent
 */
fun MovieSummary.asDomainModel(): MovieDetail {
    return MovieDetail(
        id = this.id,
        title = this.title,
        backdropImagePath = this.backdropImagePath,
        overview = this.overview,
        rating = this.rating,
        releaseDate = SimpleDateFormat("yyyy-MM-dd", Locale.UK).parse(this.releaseDate)!!
    )
}