package com.example.themoviedbapp.ui.movie

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import com.example.themoviedbapp.data.remote.MovieRepository

class MovieViewModel @ViewModelInject constructor(private val repository: MovieRepository) :
    ViewModel() {
    val movies = repository.getNowPlayingMovies()
}