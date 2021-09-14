package com.example.themoviedbapp.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface MovieDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addToFavourite(movieEntity: MovieEntity)

    @Query("SELECT * FROM favourite_movies")
    suspend fun getAllList(): List<MovieEntity>

    @Query("SELECT count(*) from favourite_movies WHERE favourite_movies.id = :id")
    suspend fun checkMovie(id: String): Int

    @Query("DELETE FROM favourite_movies WHERE favourite_movies.id = :id")
    suspend fun removeFromFavorite(id: String): Int
}
