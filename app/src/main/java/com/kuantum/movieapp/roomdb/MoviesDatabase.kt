package com.kuantum.movieapp.roomdb

import androidx.room.Database
import androidx.room.RoomDatabase
import com.kuantum.movieapp.model.SavedMovie

@Database(entities = [SavedMovie::class], version = 1)
abstract class MoviesDatabase : RoomDatabase() {
    abstract fun dao() : MoviesDao
}