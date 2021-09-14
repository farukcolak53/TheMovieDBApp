package com.example.themoviedbapp.ui.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.themoviedbapp.data.local.MovieDbRepository
import com.example.themoviedbapp.data.remote.Movie

class DetailViewModelFactory(
    private val movie: Movie,
    private val repository: MovieDbRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T =
        DetailViewModel(movie, repository) as T
}