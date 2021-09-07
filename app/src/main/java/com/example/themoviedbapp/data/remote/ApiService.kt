package com.example.themoviedbapp.data.remote

import com.example.themoviedbapp.BuildConfig
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    companion object {
        const val BASE_URL: String = "https://api.themoviedb.org/3/movie/"
        const val API_KEY: String = BuildConfig.MOVIEDB_API_KEY
    }

    @GET("movie/now_playing?api_key=$API_KEY")
    suspend fun getNowPlayingMovies(@Query("page") position: Int): MovieResponse
}