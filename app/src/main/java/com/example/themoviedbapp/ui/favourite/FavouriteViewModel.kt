package com.example.themoviedbapp.ui.favourite

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.themoviedbapp.data.local.MovieDbRepository
import com.example.themoviedbapp.data.local.MovieEntity
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavouriteViewModel @Inject constructor(private val repository: MovieDbRepository) :
    ViewModel() {

    private val _favouriteMovies = MutableLiveData<List<MovieEntity>>()
    val favouriteMovies: LiveData<List<MovieEntity>> = _favouriteMovies

    init {
        fetchMovies()
    }

    private fun fetchMovies() = viewModelScope.launch {
        _favouriteMovies.postValue(repository.getAllMovies())
    }
}