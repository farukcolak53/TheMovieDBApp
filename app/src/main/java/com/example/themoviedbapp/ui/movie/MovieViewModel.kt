package com.example.themoviedbapp.ui.movie

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.switchMap
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.example.themoviedbapp.data.remote.MovieRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MovieViewModel @Inject constructor(private val repository: MovieRepository) :
    ViewModel() {

    companion object {
        private const val DEFAULT_QUERY = ""
    }

    private val currentQuery = MutableLiveData(DEFAULT_QUERY)

    fun searchPhotos(query: String) {
        currentQuery.value = query
    }

    val movies = currentQuery.switchMap { queryString ->
        if (queryString.isNotEmpty())
            repository.searchMovies(queryString)
        else
            repository.getNowPlayingMovies().cachedIn(viewModelScope)
    }

    private var _layoutManager = MutableLiveData<String>()
    val layoutManager: LiveData<String> = _layoutManager

    init {
        _layoutManager.value = "GridLayoutManager"
    }

    fun onClickChangeLayoutManager() {
        when (layoutManager.value) {
            "LinearLayoutManager" -> _layoutManager.value = "GridLayoutManager"
            "GridLayoutManager" -> _layoutManager.value = "LinearLayoutManager"
        }
    }
}