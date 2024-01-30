package com.kuantum.movieapp.repository

import com.kuantum.movieapp.model.dto.MovieDetailsDTO
import com.kuantum.movieapp.model.dto.MoviesDTO
import com.kuantum.movieapp.model.dto.toMovieList
import com.kuantum.movieapp.model.Movie
import com.kuantum.movieapp.model.MovieDetails
import com.kuantum.movieapp.model.dto.toMovieDetails
import com.kuantum.movieapp.service.MoviesAPI
import com.kuantum.movieapp.util.Resource
import javax.inject.Inject

class MoviesRepositoryImpl @Inject constructor(
    private val api: MoviesAPI
) : MoviesRepository {

    override suspend fun getMovies(title: String, page : Int): Resource<List<Movie>> {
        return try {
            val response = api.getMovies(title = title, page = page)

            if (response.isSuccessful) {
                response.body()?.let {
                    return@let Resource.Success(it.toMovieList())
                } ?:  Resource.Error("Error", null)
            } else {
                Resource.Error("Error", null)
            }
        } catch (e : Exception) {
            println(e.localizedMessage)
            Resource.Error(e.localizedMessage ?: "Error", null)
        }
    }

    override suspend fun getMovieDetails(id: String): Resource<MovieDetails> {
        return try {
            val response = api.getMovieDetails(id = id)

            if (response.isSuccessful) {
                response.body()?.let {
                    return@let Resource.Success(it.toMovieDetails())
                } ?: Resource.Error("Error", null)
            } else {
                Resource.Error("Error", null)
            }
        } catch (e : Exception) {
            Resource.Error(e.localizedMessage ?: "Error", null)
        }
    }
}