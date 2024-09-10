package com.samz.banquemisr.challenge05.data.datasource

import com.samz.banquemisr.challenge05.data.remote.model.MovieDto
import com.samz.banquemisr.challenge05.data.remote.model.MoviesListDto
import retrofit2.Response

interface RemoteDataSource {
    suspend fun getNowPlayingMovies(pageNo: Int): Response<MoviesListDto>
    suspend fun getPopularMovies(pageNo: Int): Response<MoviesListDto>
    suspend fun getUpcomingMovies(pageNo: Int): Response<MoviesListDto>
    suspend fun getMovieDetails(movieId: Int): Response<MovieDto>
    suspend fun getMovieRecommendations(movieId: Int, pageNo: Int): Response<MoviesListDto>
}