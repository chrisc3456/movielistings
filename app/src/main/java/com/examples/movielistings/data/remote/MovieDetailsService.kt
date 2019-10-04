package com.examples.movielistings.data.remote

import com.examples.movielistings.BuildConfig
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

/**
 * Interface for the external movie genre service
 */

interface MovieDetailsService {

    companion object {
        fun create(): MovieDetailsService {
            val retrofit = Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(BuildConfig.TMDBBaseUrl)
                .build()

            return retrofit.create(MovieDetailsService::class.java)
        }
    }

    @GET("movie/{movie_id}")
    fun fetchMovieDetails(
        @Path("movie_id") movie_id: Int,
        @Query("api_key") api_key: String,
        @Query("language") language: String        //en-UK
    ): Call<MovieDetailsResponse>
}