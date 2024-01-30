package com.kuantum.movieapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kuantum.movieapp.model.Movie
import com.kuantum.movieapp.model.dto.MoviesDTO
import com.kuantum.movieapp.repository.MoviesRepository
import com.kuantum.movieapp.util.Constants.DEFAULT_SEARCH
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

    private val _page = MutableLiveData<Int>()
    val page : LiveData<Int>
        get() = _page

    fun setPage(selectedPage: Int) {
        _page.value = selectedPage
    }

    fun getMovies(title: String, page : Int) {
        if (title.isEmpty()) {
            return
        }

        _movies.value = Resource.Loading(null)

        viewModelScope.launch {
            val response = repository.getMovies(title = title, page = page)
            _movies.value = response
        }
    }

    init {
        getMovies(DEFAULT_SEARCH, 1)
    }

}