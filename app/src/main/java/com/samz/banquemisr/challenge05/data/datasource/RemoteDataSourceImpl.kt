package com.samz.banquemisr.challenge05.data.datasource

import com.samz.banquemisr.challenge05.core.MoviesType
import com.samz.banquemisr.challenge05.data.remote.ApiService
import com.samz.banquemisr.challenge05.data.remote.model.MovieDto
import com.samz.banquemisr.challenge05.data.remote.model.MoviesListDto
import retrofit2.Response
import javax.inject.Inject

class RemoteDataSourceImpl @Inject constructor(
    private val apiService: ApiService
) : RemoteDataSource {

    private fun <T> handleFailure(response: Response<T>): Result<T> {
        when (response.code()) {
            403 -> return Result.failure(Exception("API rate limit exceeded."))
            404 -> return Result.failure(Exception("Not found."))
            405 -> return Result.failure(Exception("Method not allowed"))
        }

        return Result.failure(Exception("An error occurred while retrieving data"))
    }


    override suspend fun getMovies(moviesType: MoviesType, pageNo: Int): Result<MoviesListDto> =
        when (moviesType) {
            MoviesType.NowPlaying -> getNowPlayingMovies(pageNo)
            MoviesType.Popular -> getPopularMovies(pageNo)
            MoviesType.Upcoming -> getUpcomingMovies(pageNo)
        }


    private suspend fun getNowPlayingMovies(pageNo: Int): Result<MoviesListDto> {
        try {
            val response = apiService.getNowPlayingMovies(pageNo)
            if (response.isSuccessful)
                return Result.success(response.body()!!)

            return handleFailure(response)
        } catch (e: Exception) {
            return Result.failure(e)
        }
    }

    private suspend fun getPopularMovies(pageNo: Int): Result<MoviesListDto> {
        try {
            val response = apiService.getPopularMovies(pageNo)
            if (response.isSuccessful)
                return Result.success(response.body()!!)

            return handleFailure(response)
        } catch (e: Exception) {
            return Result.failure(e)
        }
    }

    private suspend fun getUpcomingMovies(pageNo: Int): Result<MoviesListDto> {
        try {
            val response = apiService.getUpcomingMovies(pageNo)
            if (response.isSuccessful)
                return Result.success(response.body()!!)

            return handleFailure(response)
        } catch (e: Exception) {
            return Result.failure(e)
        }
    }

    override suspend fun getMovieDetails(movieId: Int): Result<MovieDto> {
        try {
            val response = apiService.getMovieDetails(movieId)
            if (response.isSuccessful)
                return Result.success(response.body()!!)

            return handleFailure(response)
        } catch (e: Exception) {
            return Result.failure(e)
        }
    }

    override suspend fun getMovieRecommendations(
        movieId: Int,
        pageNo: Int
    ): Result<MoviesListDto> {
        try {
            val response = apiService.getMovieRecommendations(movieId, pageNo)
            if (response.isSuccessful)
                return Result.success(response.body()!!)

            return handleFailure(response)
        } catch (e: Exception) {
            return Result.failure(e)
        }
    }
}