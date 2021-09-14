package com.example.themoviedbapp.data.remote

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.liveData
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MovieRepository @Inject constructor(private val apiService: ApiService) {
    fun getNowPlayingMovies() = Pager(
        config = PagingConfig(
            pageSize = 5,
            maxSize = 20,
            enablePlaceholders = false
        ),
        pagingSourceFactory = { MoviePagingSource(apiService, null) }
    ).liveData

    // .liveData -> to turn this pager into a stream of paging data contained in liveData. Later,
    // we will observe this live data in the fragment.

    fun searchMovies(query: String) = Pager(
        config = PagingConfig(
            pageSize = 5,
            maxSize = 20,
            enablePlaceholders = false
        ),
        pagingSourceFactory = { MoviePagingSource(apiService, query) }
    ).liveData
}
