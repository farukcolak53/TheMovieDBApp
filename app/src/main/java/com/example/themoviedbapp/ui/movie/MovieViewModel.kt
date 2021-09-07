package com.example.themoviedbapp.ui.movie

import androidx.lifecycle.ViewModel
import com.example.themoviedbapp.data.remote.MovieRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MovieViewModel @Inject constructor(private val repository: MovieRepository) :
    ViewModel() {
    val movies = repository.getNowPlayingMovies()
}