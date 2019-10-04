package com.examples.movielistings.data.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import com.examples.movielistings.BuildConfig
import com.examples.movielistings.data.entity.MovieSummary
import com.examples.movielistings.data.entity.asDomainModel
import com.examples.movielistings.data.local.MoviesDatabase
import com.examples.movielistings.data.remote.MovieDetailsResponse
import com.examples.movielistings.data.remote.MovieDetailsService
import com.examples.movielistings.data.remote.asDatabaseModel
import com.examples.movielistings.domain.models.MovieDetail
import com.examples.movielistings.domain.repository.MovieDetailDataSource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MovieDetailRepository(private val database: MoviesDatabase): MovieDetailDataSource {

    private var movieSummary = MutableLiveData<MovieSummary>()

    override fun getMovieDetail(movieId: Int): LiveData<MovieDetail> {
        return Transformations.map(movieSummary) { summary -> summary.asDomainModel() }
    }


    /**
     * Trigger a refresh of the movie detail data and cache the results
     */
    override fun refreshMovieDetail(movieId: Int) {
        getMovieDetailRemote(movieId)
    }

    /**
     * Make an external service call to retrieve the external movie detail data
     */
    private fun getMovieDetailRemote(movieId: Int) {

        val serviceCall = MovieDetailsService.create().fetchMovieDetails(
            movieId,
            BuildConfig.TMDBApiKey,
            "en-UK"
        )

        serviceCall.enqueue(object: Callback<MovieDetailsResponse> {
            override fun onResponse(call: Call<MovieDetailsResponse>, response: Response<MovieDetailsResponse>) {

                if (response.body() != null) {
                    val summary = response.body()!!.asDatabaseModel()
                    runBlocking(Dispatchers.IO) { database.movieSummaryDao().insertMovieSummary(summary) }

                    // Post the value back so the live data is updated
                    movieSummary.postValue(summary)
                }
            }

            override fun onFailure(call: Call<MovieDetailsResponse>, throwable: Throwable) {
                Log.d("##Repo.MovieDet.OnFail", "Failed to refresh movie detail: ${throwable.message}")
            }
        }
        )
    }
}