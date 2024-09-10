package com.samz.banquemisr.challenge05.domain.repository

import com.samz.banquemisr.challenge05.core.MoviesType
import com.samz.banquemisr.challenge05.domain.model.Movie

interface MoviesRepository {

    suspend fun getMovies(moviesType: MoviesType, pageNo: Int): Result<List<Movie>>

    suspend fun getMovieDetails(movieId: Int): Result<Movie?>

    suspend fun getMovieRecommendation(movieId: Int): Result<List<Movie>>
}