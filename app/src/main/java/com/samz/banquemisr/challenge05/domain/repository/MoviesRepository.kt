package com.samz.banquemisr.challenge05.domain.repository

import androidx.paging.PagingData
import com.samz.banquemisr.challenge05.core.MoviesType
import com.samz.banquemisr.challenge05.domain.model.Movie
import kotlinx.coroutines.flow.Flow

interface MoviesRepository {

    suspend fun getMovies(moviesType: MoviesType): Result<List<Movie>>

    fun moviesPagingSource(moviesType: MoviesType): Flow<PagingData<Movie>>

    suspend fun getMovieDetails(movieId: Int): Result<Movie?>

    suspend fun getMovieRecommendation(movieId: Int): Result<List<Movie>>

    suspend fun clearAll()
}