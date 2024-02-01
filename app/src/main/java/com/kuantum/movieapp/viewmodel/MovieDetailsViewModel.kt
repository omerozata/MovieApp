package com.kuantum.movieapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kuantum.movieapp.model.Movie
import com.kuantum.movieapp.model.MovieDetails
import com.kuantum.movieapp.model.SavedMovie
import com.kuantum.movieapp.repository.MoviesRepository
import com.kuantum.movieapp.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieDetailsViewModel @Inject constructor(
    private val repository: MoviesRepository
) : ViewModel() {

    private val _movieDetails = MutableLiveData<Resource<MovieDetails>>()
    val movieDetails: LiveData<Resource<MovieDetails>>
        get() = _movieDetails

    private val _isFavorite = MutableLiveData<Boolean>()
    val isFavorite : LiveData<Boolean>
        get() = _isFavorite

    val idList = repository.getIds()

    fun checkIsFavorite(id: String, idList : List<String>) {
        _isFavorite.value = idList.contains(id)
    }

    fun setIsFavorite() {
        isFavorite.value?.let {
            _isFavorite.value = !it
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

    fun getMovieDetails(id: String) {

        _movieDetails.value = Resource.Loading(null)

        viewModelScope.launch {
            val response = repository.getMovieDetails(id = id)
            _movieDetails.value = response
        }

    }

}