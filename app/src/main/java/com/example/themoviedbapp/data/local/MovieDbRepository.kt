package com.example.themoviedbapp.data.local

import javax.inject.Inject

class MovieDbRepository @Inject constructor(private val favouriteMovieDao: MovieDao) {
    suspend fun addToFavourite(favouriteMovie: MovieEntity) =
        favouriteMovieDao.addToFavourite(favouriteMovie)

    suspend fun checkMovie(id: Int) = favouriteMovieDao.checkMovie(id)

    suspend fun removeFromFavourite(movieEntity: MovieEntity) =
        favouriteMovieDao.deleteMovie(movieEntity)
}