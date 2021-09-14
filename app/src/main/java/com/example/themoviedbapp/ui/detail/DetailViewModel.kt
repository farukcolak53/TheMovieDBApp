package com.example.themoviedbapp.ui.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.themoviedbapp.data.local.MovieDbRepository
import com.example.themoviedbapp.data.local.MovieEntity
import com.example.themoviedbapp.data.remote.Movie
import kotlinx.coroutines.launch

class DetailViewModel(
    movieParam: Movie,
    private val repository: MovieDbRepository
) : ViewModel() {

    private val _movie = MutableLiveData(movieParam)
    val movie: LiveData<Movie> = _movie

    fun addToFavourite(movieEntity: MovieEntity) = viewModelScope.launch {
        repository.addToFavourite(movieEntity)
    }

    suspend fun checkMovie(id: String) = repository.checkMovie(id)

    fun removeFromFavorite(id: String) = viewModelScope.launch {
        repository.removeFromFavorite(id)
    }
}
