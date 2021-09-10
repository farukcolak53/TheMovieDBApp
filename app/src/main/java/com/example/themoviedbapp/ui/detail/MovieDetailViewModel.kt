package com.example.themoviedbapp.ui.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.themoviedbapp.data.remote.Movie

class MovieDetailViewModel(
    private val movieParam: Movie
) : ViewModel() {

    private val _movie = MutableLiveData<Movie>(movieParam)
    val movie: LiveData<Movie> = _movie

}