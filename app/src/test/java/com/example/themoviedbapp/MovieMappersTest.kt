package com.example.themoviedbapp

import com.example.themoviedbapp.data.local.MovieEntity
import com.example.themoviedbapp.data.remote.Movie
import com.google.common.truth.Truth.assertThat
import org.junit.Before
import org.junit.Test
import org.mockito.MockitoAnnotations

class MovieMappersTest {

    private lateinit var movie: Movie

    private lateinit var movieEntity: MovieEntity

    @Before
    fun before() {
        MockitoAnnotations.initMocks(this)
        movie = Movie(
            id = 1,
            originalTitle = "",
            overview = "",
            title = "",
            releaseDate = "",
            posterPath = ""
        )

        movieEntity = MovieEntity(
            id = "1",
            originalTitle = "",
            overview = "",
            title = "",
            releaseDate = "",
            posterPath = ""
        )
    }

    @Test
    fun `given Movie, when toMovieEntity is called, then MovieEntity should return`() {
        val result = movie.toMovieEntity() is MovieEntity
        assertThat(result).isTrue()
    }

    @Test
    fun `given MovieEntity, when toMovie is called, then Movie should return`() {
        val result = movieEntity.toMovie() is Movie
        assertThat(result).isTrue()
    }
}