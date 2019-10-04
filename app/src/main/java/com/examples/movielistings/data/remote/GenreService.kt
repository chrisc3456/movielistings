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

interface GenreService {

    companion object {
        fun create(): GenreService {
            val retrofit = Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(BuildConfig.TMDBBaseUrl)
                .build()

            return retrofit.create(GenreService::class.java)
        }
    }

    @GET("genre/movie/list")
    fun fetchGenres(
        @Query("api_key") api_key: String
    ): Call<GenreResponse>
}