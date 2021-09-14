package com.example.themoviedbapp.data.remote

import androidx.paging.PagingSource
import androidx.paging.PagingState
import java.io.IOException
import retrofit2.HttpException

private const val STARTING_PAGE_INDEX = 1

// PagingSource is a class that knows how to load data from REST API, then turn it into pages.

class MoviePagingSource(
    private val apiService: ApiService,
    private val query: String?
) : PagingSource<Int, Movie>() {

    // Function which will trigger the api request and turn the data into pages
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Movie> {

        // We used try-catch because we cannot trust to backend. 3 options to return.
        return try {
            val position: Int = params.key ?: STARTING_PAGE_INDEX
            val response =
                if (query != null) apiService.searchMovies(
                    query,
                    position
                ) else apiService.getNowPlayingMovies(
                    position
                )
            val movies = response.results

            // 1 page of results in our recycler view. (OK case)
            LoadResult.Page(
                data = movies,
                prevKey = if (position == STARTING_PAGE_INDEX) null else position - 1,
                nextKey = if (movies.isEmpty()) null else position + 1
            )
        } catch (e: IOException) {
            LoadResult.Error(e)
        } catch (e: HttpException) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, Movie>): Int? {
        TODO("Not yet implemented")
    }
}
