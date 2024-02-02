package com.kuantum.movieapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kuantum.movieapp.model.SavedMovie
import com.kuantum.movieapp.repository.MoviesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavMovieDetailsViewModel @Inject constructor(
    private val repository: MoviesRepository
) : ViewModel() {

    private val _isFavorite = MutableLiveData<Boolean>()
    val isFavorite : LiveData<Boolean>
        get() = _isFavorite


    fun setIsFavorite(boolean: Boolean? = null) {
        boolean?.let {
            _isFavorite.value = it
            println("boolean")
            return
        }

        isFavorite.value?.let {
            _isFavorite.value = !it
            println("it")
        }
    }

    fun insertOrDeleteMovie(movie: SavedMovie) {
        isFavorite.value?.let {
            if (it) {
                println("delete")
                deleteMovie(movie.imdbID)
            } else {
                println("insert")
                insertMovie(movie)
            }
        }
    }

    private fun insertMovie(movie: SavedMovie) = viewModelScope.launch {
        repository.insertMovie(movie)
    }

    private fun deleteMovie(id : String) = viewModelScope.launch {
        repository.deleteMovie(id)
    }

    lateinit var movie : LiveData<SavedMovie>

    fun getMovie(id: String) {
        movie = repository.getMovie(id)
    }
}