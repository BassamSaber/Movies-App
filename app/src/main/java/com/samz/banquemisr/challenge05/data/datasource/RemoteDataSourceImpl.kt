package com.samz.banquemisr.challenge05.data.datasource

import com.samz.banquemisr.challenge05.data.remote.ApiService
import com.samz.banquemisr.challenge05.data.remote.model.MovieDto
import com.samz.banquemisr.challenge05.data.remote.model.MoviesListDto
import retrofit2.Response
import javax.inject.Inject

class RemoteDataSourceImpl @Inject constructor(
    private val apiService: ApiService
) : RemoteDataSource {
    override suspend fun getNowPlayingMovies(pageNo: Int): Response<MoviesListDto> =
        apiService.getNowPlayingMovies(pageNo)

    override suspend fun getPopularMovies(pageNo: Int): Response<MoviesListDto> =
        apiService.getPopularMovies(pageNo)

    override suspend fun getUpcomingMovies(pageNo: Int): Response<MoviesListDto> =
        apiService.getUpcomingMovies(pageNo)

    override suspend fun getMovieDetails(movieId: Int): Response<MovieDto> =
        apiService.getMovieDetails(movieId)

    override suspend fun getMovieRecommendations(
        movieId: Int,
        pageNo: Int
    ): Response<MoviesListDto> =
        apiService.getMovieRecommendations(movieId, pageNo)
}