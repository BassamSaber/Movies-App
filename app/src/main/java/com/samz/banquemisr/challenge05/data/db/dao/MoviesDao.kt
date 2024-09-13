package com.samz.banquemisr.challenge05.data.db.dao

import androidx.paging.PagingSource
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

    @Query("SELECT * FROM movies WHERE id==:id")
    suspend fun getMovieDetails(id: Int): MovieDto?

    @Query("SELECT movies.* FROM movies INNER JOIN movie_list_index ON movies.id == movie_list_index.movieId WHERE movie_list_index.movieTypeId=:type ORDER BY movie_list_index.id ASC LIMIT 20")
    suspend fun getMoviesByType(type: Int): List<MovieDto>?

    @Query("SELECT movies.* FROM movies INNER JOIN movie_list_index ON movies.id == movie_list_index.movieId WHERE movie_list_index.movieTypeId=:type ORDER BY movie_list_index.id ASC")
    fun pagingSource(type: Int): PagingSource<Int, MovieDto>


    @Query("DELETE FROM movies WHERE id IN (:ids)")
    suspend fun deleteByIds(ids: List<Int>)

    @Query("DELETE FROM movies")
    suspend fun clearAll()
}