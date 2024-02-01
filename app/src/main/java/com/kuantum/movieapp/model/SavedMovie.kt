package com.kuantum.movieapp.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "movies")
data class SavedMovie(
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
    val year: String,

    @PrimaryKey(autoGenerate = true)
    var id : Int? = null
)