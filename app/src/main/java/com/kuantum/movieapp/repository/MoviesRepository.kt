package com.kuantum.movieapp.repository

import androidx.lifecycle.LiveData
import com.kuantum.movieapp.model.dto.MovieDetailsDTO
import com.kuantum.movieapp.model.dto.MoviesDTO
import com.kuantum.movieapp.model.Movie
import com.kuantum.movieapp.model.MovieDetails
import com.kuantum.movieapp.model.SavedMovie
import com.kuantum.movieapp.util.Resource

interface MoviesRepository {

    suspend fun getMovies(title : String, page : Int) : Resource<List<Movie>>

    suspend fun getMovieDetails(id : String) : Resource<MovieDetails>

    suspend fun insertMovie(movie: SavedMovie)

    suspend fun deleteMovie(id : String)

    fun getMovies() : LiveData<List<SavedMovie>>

    fun getIds() : LiveData<List<String>>

    fun getMovie(id : String) : LiveData<SavedMovie>
}