package com.kuantum.movieapp.repository

import com.kuantum.movieapp.model.dto.MovieDetailsDTO
import com.kuantum.movieapp.model.dto.MoviesDTO
import com.kuantum.movieapp.model.Movie
import com.kuantum.movieapp.model.MovieDetails
import com.kuantum.movieapp.util.Resource

interface MoviesRepository {

    suspend fun getMovies(title : String) : Resource<List<Movie>>

    suspend fun getMovieDetails(id : String) : Resource<MovieDetails>
}