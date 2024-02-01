package com.kuantum.movieapp.model

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

fun MovieDetails.toSavedMovie(): SavedMovie {
    return SavedMovie(
        actors,
        awards,
        boxOffice,
        country,
        director,
        imdbID,
        imdbRating,
        plot,
        poster,
        runtime,
        title,
        writer,
        year
    )
}