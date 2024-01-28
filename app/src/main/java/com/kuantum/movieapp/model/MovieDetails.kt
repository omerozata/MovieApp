package com.kuantum.movieapp.model

import com.google.gson.annotations.SerializedName
import com.kuantum.movieapp.model.dto.Rating

data class MovieDetails(
    val actors: String,
    val awards: String,
    val boxOffice: String,
    val country: String,
    val director: String,
    val imdbID: String,
    val imdbRating: String,
    val plot: String,
    val poster: String,
    val runtime: String,
    val title: String,
    val writer: String,
    val year: String
)