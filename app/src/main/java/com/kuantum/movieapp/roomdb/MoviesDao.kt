package com.kuantum.movieapp.roomdb

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.kuantum.movieapp.model.SavedMovie

@Dao
interface MoviesDao {

    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insertMovie(movie: SavedMovie)

    @Query("DELETE FROM movies WHERE imdbID = :id")
    suspend fun deleteMovie(id : String)

    @Query("SELECT * FROM movies")
    fun getMovies() : LiveData<List<SavedMovie>>

    @Query("SELECT imdbID FROM movies")
    fun getIds() : LiveData<List<String>>

    @Query("SELECT * FROM movies WHERE imdbID = :id")
    fun getMovie(id : String) : LiveData<SavedMovie>
}