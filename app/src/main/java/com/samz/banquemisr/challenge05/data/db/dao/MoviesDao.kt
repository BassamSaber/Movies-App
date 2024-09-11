package com.samz.banquemisr.challenge05.data.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.samz.banquemisr.challenge05.data.remote.model.MovieDto

@Dao
interface MoviesDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(repos: List<MovieDto>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun createOrUpdate(movie: MovieDto)

    @Query("SELECT * FROM movies WHERE id=:id")
    suspend fun getMovieDetails(id: Int): MovieDto

    @Query("SELECT * FROM movies WHERE id IN (:ids)")
    suspend fun getMoviesByIds(ids: List<Int>): List<MovieDto>

    @Query("DELETE FROM movies")
    suspend fun clearAll()
}