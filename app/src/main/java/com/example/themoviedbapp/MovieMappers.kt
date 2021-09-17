package com.example.themoviedbapp

import com.example.themoviedbapp.data.local.MovieEntity
import com.example.themoviedbapp.data.remote.Movie

@JvmName("movieListToMovieEntityList")
fun List<Movie>.toMovieEntityList() = map {
    it.toMovieEntity()
}

fun Movie.toMovieEntity() = MovieEntity(
    id = id.toString(),
    title = title,
    originalTitle = originalTitle,
    overview = overview,
    posterPath = posterPath,
    releaseDate = releaseDate
)

@JvmName("movieEntityListToMovieList")
fun List<MovieEntity>.toMovieList() = map {
    it.toMovie()
}

fun MovieEntity.toMovie() = Movie(
    id = id.toInt(),
    title = title,
    originalTitle = originalTitle,
    overview = overview,
    posterPath = posterPath,
    releaseDate = releaseDate
)
