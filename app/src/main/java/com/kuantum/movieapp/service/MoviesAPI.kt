package com.kuantum.movieapp.service

import com.kuantum.movieapp.BuildConfig.API_KEY
import com.kuantum.movieapp.model.dto.MovieDetailsDTO
import com.kuantum.movieapp.model.dto.MoviesDTO
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface MoviesAPI {

    @GET(".")
    suspend fun getMovies(
        @Query("s") title : String,
        @Query("apikey") apiKey : String = API_KEY
    ) : Response<MoviesDTO>

    @GET(".")
    suspend fun getMovieDetails(
        @Query("i") id : String,
        @Query("apikey") apiKey : String = API_KEY
    ) : Response<MovieDetailsDTO>
}