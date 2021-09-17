package com.example.themoviedbapp.data.local

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import com.example.themoviedbapp.data.remote.Movie
import com.example.themoviedbapp.toMovieEntity
import com.example.themoviedbapp.toMovieEntityList
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


// With RunWith annotation, we make sure that all the tests inside of this class will run on the
// emulator. Also tell to JUnit that these tests are instrumented tests.

// With SmallTest annotation, we tell JUnit that all the tests inside of this class are unit tests.
// MediumTest -> Integrated tests
// LargeTest -> UI tests
@RunWith(AndroidJUnit4::class)
@SmallTest
@ExperimentalCoroutinesApi
class MovieDaoTest {

    // Since the actual functions are suspend functions, they work async. Here, we need to tell
    // JUnit that run them in the same thread and one by one. -> we need to specify a so called rule
    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()


    // What we need to test MovieDao are listed here.

    // We shouldn't initialize fields here, because to make the unit tests independent, all of them
    // should have separate objects. For ex, we want new databases for each test case.
    // Let's say, we added an item to the db, we don't want that item exist in another test case.
    // That's why, we initialize them in @Before annotation.
    private lateinit var database: MovieDatabase
    private lateinit var dao: MovieDao

    // inMemoryDatabaseBuilder() -> is not a real db (not stay in persistent storage)
    // vs databaseBuilder()
    // allowMainThreadQueries() -> run test cases in main thread. Since we cannot know how threads
    // behaving, running them separate threads may manipulate each other. But, we want complete
    // independence of tests.

    @Before
    fun before() {
        database = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            MovieDatabase::class.java
        ).allowMainThreadQueries()
            .build()
        dao = database.movieDao()
    }

    @After
    fun after() {
        database.close()
    }

    @Test
    fun addToFavourite() = runBlockingTest {
        val movie = Movie(
            1,
            "Fight Club",
            "overview",
            "path",
            "1999",
            "Fight Club"
        )
        dao.addToFavourite(movie.toMovieEntity())
        val allMovies = dao.getAllList()
        assertThat(allMovies).contains(movie.toMovieEntity())
    }

    @Test
    fun removeFromFavourite() = runBlockingTest {
        val movie = Movie(
            1,
            "Fight Club",
            "overview",
            "path",
            "1999",
            "Fight Club"
        )
        dao.addToFavourite(movie.toMovieEntity())
        dao.removeFromFavorite(movie.id.toString())
        val allMovies = dao.getAllList()
        assertThat(allMovies).doesNotContain(movie.toMovieEntity())
    }

    @Test
    fun checkMovie() =
        runBlockingTest {
            val movie = Movie(
                1,
                "Fight Club",
                "overview",
                "path",
                "1999",
                "Fight Club"
            )
            dao.addToFavourite(movie.toMovieEntity())
            val allMovies = dao.getAllList()
            val count = dao.checkMovie(movie.id.toString())
            assertThat(allMovies).hasSize(count)
        }

    @Test
    fun getAllMovies() = runBlockingTest {
        val movie1 = Movie(
            1,
            "Fight Club",
            "overview",
            "path",
            "1999",
            "Fight Club"
        )
        val movie2 = Movie(
            2,
            "Fight Club",
            "overview",
            "path",
            "1999",
            "Fight Club"
        )
        val movie3 = Movie(
            3,
            "Fight Club",
            "overview",
            "path",
            "1999",
            "Fight Club"
        )
        val movieList = listOf(
            movie1, movie2, movie3
        ).toMovieEntityList()

        dao.addToFavourite(movie1.toMovieEntity())
        dao.addToFavourite(movie2.toMovieEntity())
        dao.addToFavourite(movie3.toMovieEntity())
        val allMovies = dao.getAllList()
        assertThat(allMovies).isEqualTo(movieList)
    }
}