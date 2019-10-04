package com.examples.movielistings.data.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import com.examples.movielistings.BuildConfig
import com.examples.movielistings.data.entity.Genre
import com.examples.movielistings.data.entity.GenreMovieJoin
import com.examples.movielistings.data.entity.Movie
import com.examples.movielistings.data.entity.asDomainModel
import com.examples.movielistings.data.local.MoviesDatabase
import com.examples.movielistings.data.remote.*
import com.examples.movielistings.domain.models.GenreListing
import com.examples.movielistings.domain.repository.GenreListingDataSource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class GenreListingRepository(private val database: MoviesDatabase): GenreListingDataSource {

    private var genreDataList: MutableLiveData<List<Genre>> = MutableLiveData()

    /**
     * Retrieve a domain model representation of the current genre/movie listings from the database
     */
    override fun getGenreListings(): LiveData<List<GenreListing>> {
        return Transformations.map(genreDataList) { list ->
            list.map { item ->
                GenreListing(
                    item.name,
                    runBlocking(Dispatchers.IO) { getMovieListForGenre(item.id) }.asDomainModel()
                )
            }
        }
    }

    /**
     * Trigger a refresh of the genre/movie listings data and cache the results
     */
    override fun refreshGenreListings() {
        getGenreDataRemote()
    }

    /**
     * Make an external service call to retrieve the external genre data
     */
    private fun getGenreDataRemote() {

        val serviceCall = GenreService.create().fetchGenres(BuildConfig.TMDBApiKey)

        serviceCall.enqueue(object: Callback<GenreResponse> {
            override fun onResponse(call: Call<GenreResponse>, response: Response<GenreResponse>) {

                if (response.body() != null) {
                    val genreListDatabaseModel = response.body()!!.asDatabaseModel()
                    runBlocking { insertGenresIntoDatabase(genreListDatabaseModel) }

                    // Post the value back so the live data is updated
                    genreDataList.postValue(genreListDatabaseModel)

                    refreshGenresWithMovieData(genreListDatabaseModel)
                }
            }

            override fun onFailure(call: Call<GenreResponse>, throwable: Throwable) {
                Log.d("##Repo.Genre.OnFailure", "Failed to refresh genres: ${throwable.message}")
            }
        }
        )
    }

    /**
     * Make an external service call to retrieve the external movie data for the specified genre
     */
    private fun refreshGenresWithMovieData(genreList: List<Genre>) {

        for (genre in genreList) {
            val serviceCall = DiscoverMovieService.create().fetchMovies(
                BuildConfig.TMDBApiKey,
                "en-UK",
                1,
                "popularity.desc",
                "${genre.id}"
            )

            serviceCall.enqueue(object : Callback<DiscoverMovieResponse> {
                override fun onResponse(call: Call<DiscoverMovieResponse>, responseDiscover: Response<DiscoverMovieResponse>) {
                    if (responseDiscover.body() != null) {
                        val moviesForCurrentGenre = responseDiscover.body()!!.asDatabaseModel()
                        runBlocking { insertMoviesForGenreIntoDatabase(genre, moviesForCurrentGenre) }

                        // Post the value back so the live data is updated
                        genreDataList.postValue(genreList)
                    }
                }

                override fun onFailure(call: Call<DiscoverMovieResponse>, throwable: Throwable) {
                    Log.d(
                        "##Repo.Movie.OnFailure",
                        "Failed to refresh discoverMovies for genre ${genre.id}: ${throwable.message}"
                    )
                }
            }
            )
        }
    }

    /**
     * Retrieve a list of movie records associated with the supplied genre
     */
    private fun getMovieListForGenre(genreId: Int): List<Movie> {
        return database.genreMovieDao().getMoviesForGenre(genreId)
    }

    private suspend fun insertGenresIntoDatabase(genres: List<Genre>) = withContext(Dispatchers.IO) {
        database.genreDao().insertAllGenres((genres))
    }

    private suspend fun insertMoviesForGenreIntoDatabase(genre: Genre, movies: List<Movie>) = withContext(Dispatchers.IO) {
        database.movieDao().insertAllMovies((movies))
        for (movie in movies) {
            database.genreMovieDao().insertRelationship(GenreMovieJoin(genre.id, movie.id))
        }
    }
}