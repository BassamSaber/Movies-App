package com.samz.banquemisr.challenge05.data.datasource

import com.samz.banquemisr.challenge05.core.MoviesType
import com.samz.banquemisr.challenge05.data.remote.model.MovieDto
import com.samz.banquemisr.challenge05.data.remote.model.MoviesListDto

interface RemoteDataSource {
    suspend fun getMovies(moviesType: MoviesType, pageNo: Int): Result<MoviesListDto>
    suspend fun getMovieDetails(movieId: Int): Result<MovieDto>
    suspend fun getMovieRecommendations(movieId: Int, pageNo: Int): Result<MoviesListDto>
}