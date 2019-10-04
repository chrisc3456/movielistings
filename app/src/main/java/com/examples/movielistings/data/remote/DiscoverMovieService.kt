package com.examples.movielistings.data.remote

import com.examples.movielistings.BuildConfig
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Interface for the external movie genre service
 */

interface DiscoverMovieService {

    companion object {
        fun create(): DiscoverMovieService {
            val retrofit = Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(BuildConfig.TMDBBaseUrl)
                .build()

            return retrofit.create(DiscoverMovieService::class.java)
        }
    }

    @GET("discover/movie")
    fun fetchMovies(
        @Query("api_key") api_key: String,
        @Query("language") language: String,        //en-UK
        @Query("page") page: Int,
        @Query("sort_by") sort_by: String,          //popularity.desc
        @Query("with_genres") with_genres: String   //28,5,10
    ): Call<DiscoverMovieResponse>
}