package com.kuantum.movieapp.model.dto

import com.google.gson.annotations.SerializedName
import com.kuantum.movieapp.model.Movie

data class MoviesDTO(
    @SerializedName("Response")
    val response: String,
    @SerializedName("Search")
    val search: List<Search>,
    @SerializedName("totalResults")
    val totalResults: String
)

fun MoviesDTO.toMovieList() : List<Movie> {
    return search.map {search -> Movie(search.imdbID, search.poster, search.title, search.year) }
}