package com.samz.banquemisr.challenge05.data.repository

import com.samz.banquemisr.challenge05.core.MoviesType
import com.samz.banquemisr.challenge05.data.datasource.LocaleDataSource
import com.samz.banquemisr.challenge05.data.datasource.RemoteDataSource
import com.samz.banquemisr.challenge05.data.mappers.toMovie
import com.samz.banquemisr.challenge05.domain.model.Movie
import com.samz.banquemisr.challenge05.domain.repository.MoviesRepository
import retrofit2.HttpException
import javax.inject.Inject

class MoviesRepositoryImpl @Inject constructor(
    private val remoteDataSource: RemoteDataSource,
    private val localeDataSource: LocaleDataSource,
) : MoviesRepository {
    override suspend fun getMovies(moviesType: MoviesType): Result<List<Movie>> {
        return if (localeDataSource.checkMoviesCacheValidation(moviesType)) {
            getDataHelper(localeDataSource.getMovies(moviesType)) { result ->
                result.map { it.toMovie() }
            }
        } else {
            getDataResultHelper(
                remoteDataSource.getMovies(moviesType, 1)
            ) { result ->
                result.movies?.map { it.toMovie() } ?: emptyList()
            }
        }
    }

    override suspend fun getMovieDetails(movieId: Int): Result<Movie?> {
        return if (localeDataSource.checkMovieDetailsCacheValidation(movieId)) {
            getDataHelper(localeDataSource.getMovieDetails(movieId)) { result -> result?.toMovie() }
        } else {
            getDataResultHelper(
                remoteDataSource.getMovieDetails(movieId)
            ) { result -> result.toMovie() }
        }
    }

    override suspend fun getMovieRecommendation(movieId: Int): Result<List<Movie>> {
        return getDataResultHelper(
            remoteDataSource.getMovieRecommendations(movieId, 1)
        ) { result ->
            result.movies?.map { it.toMovie() } ?: emptyList()
        }
    }

    private fun <T, N> getDataResultHelper(result: Result<T>, mapper: (T) -> N): Result<N> {
        return try {
            Result.success(mapper(result.getOrThrow()))
        } catch (e: HttpException) {
            Result.failure(e)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    private fun <T, N> getDataHelper(result: T, mapper: (T) -> N): Result<N> {
        return try {
            Result.success(mapper(result))
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}