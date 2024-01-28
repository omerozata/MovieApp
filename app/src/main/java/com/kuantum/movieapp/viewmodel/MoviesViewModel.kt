package com.kuantum.movieapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kuantum.movieapp.model.Movie
import com.kuantum.movieapp.model.dto.MoviesDTO
import com.kuantum.movieapp.repository.MoviesRepository
import com.kuantum.movieapp.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MoviesViewModel @Inject constructor(
    private val repository: MoviesRepository
) : ViewModel() {

    private val _movies = MutableLiveData<Resource<List<Movie>>>()
    val movies: LiveData<Resource<List<Movie>>>
        get() = _movies

    init {
        getMovies("Terminator")
    }



    fun getMovies(title: String) {

        if (title.isEmpty()) {
            return
        }

        _movies.value = Resource.Loading(null)

        viewModelScope.launch {
            val response = repository.getMovies(title = title)
            _movies.value = response
        }
    }

}