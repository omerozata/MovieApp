package com.kuantum.movieapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.kuantum.movieapp.model.SavedMovie
import com.kuantum.movieapp.repository.MoviesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class FavoriteListViewModel @Inject constructor(
    private val repository: MoviesRepository
) : ViewModel() {

    val movieList = repository.getMovies()

}