package com.example.themoviedbapp.data.local

import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MovieDbRepository @Inject constructor(private val favouriteMovieDao: MovieDao) {
    suspend fun addToFavourite(favouriteMovie: MovieEntity) =
        favouriteMovieDao.addToFavourite(favouriteMovie)

    suspend fun checkMovie(id: String) = favouriteMovieDao.checkMovie(id)

    suspend fun getAllMovies(): List<MovieEntity> {
        return favouriteMovieDao.getAllList()
    }

    suspend fun removeFromFavorite(id: String) {
        favouriteMovieDao.removeFromFavorite(id)
    }
}