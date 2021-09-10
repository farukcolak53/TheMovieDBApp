package com.example.themoviedbapp.ui.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.themoviedbapp.data.remote.Movie

class MovieDetailViewModelFactory(
    private val movie: Movie
) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T =
        MovieDetailViewModel(movie) as T
}