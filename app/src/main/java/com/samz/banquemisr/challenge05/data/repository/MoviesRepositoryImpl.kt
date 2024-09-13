package com.samz.banquemisr.challenge05.data.repository

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import com.samz.banquemisr.challenge05.core.MoviesType
import com.samz.banquemisr.challenge05.data.datasource.LocaleDataSource
import com.samz.banquemisr.challenge05.data.datasource.RemoteDataSource
import com.samz.banquemisr.challenge05.data.mappers.toMovie
import com.samz.banquemisr.challenge05.data.remote.NoConnectionException
import com.samz.banquemisr.challenge05.domain.model.Movie
import com.samz.banquemisr.challenge05.domain.repository.MoviesRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class MoviesRepositoryImpl @Inject constructor(
    private val remoteMediator: MoviesRemoteMediator,
    private val remoteDataSource: RemoteDataSource,
    private val localeDataSource: LocaleDataSource,
) : MoviesRepository {

    @OptIn(ExperimentalPagingApi::class)
    override fun moviesPagingSource(moviesType: MoviesType): Flow<PagingData<Movie>> {
        remoteMediator.moviesType = moviesType
        return Pager(
            config = PagingConfig(pageSize = 20),
            remoteMediator = remoteMediator,
            pagingSourceFactory = { localeDataSource.moviesPagingSource(moviesType) }
        ).flow.map { pagingData -> pagingData.map { it.toMovie() } }
    }

    override suspend fun getMovies(moviesType: MoviesType): Result<List<Movie>> {
        try {
            return if (localeDataSource.checkMoviesCacheValidation(moviesType)) {
                Result.success(
                    localeDataSource.getMovies(moviesType)?.map { it.toMovie() } ?: emptyList()
                )

            } else {
                try {
                    val result = remoteDataSource.getMovies(moviesType, 1).getOrThrow()
                    result.movies?.let {
                        localeDataSource.insetMovies(
                            moviesType,
                            result.pageNo,
                            result.totalPages,
                            it
                        )
                    }
                    Result.success(result.movies?.map { it.toMovie() } ?: emptyList())
                } catch (e: NoConnectionException) {
                    Result.success(
                        localeDataSource.getMovies(moviesType)?.map { it.toMovie() } ?: emptyList()
                    )
                }
            }
        } catch (e: Exception) {
            return Result.failure(e)
        }
    }

    override suspend fun getMovieDetails(movieId: Int): Result<Movie?> {
        try {
            return if (localeDataSource.checkMovieDetailsCacheValidation(movieId)) {
                Result.success(localeDataSource.getMovieDetails(movieId)?.toMovie())

            } else {
                try {
                    val result = remoteDataSource.getMovieDetails(movieId).getOrThrow()
                    localeDataSource.insertOrUpdateMovie(result)
                    Result.success(result.toMovie())
                } catch (e: NoConnectionException) {
                    Result.success(localeDataSource.getMovieDetails(movieId)?.toMovie())
                }
            }
        } catch (e: Exception) {
            return Result.failure(e)
        }
    }

    override suspend fun getMovieRecommendation(movieId: Int): Result<List<Movie>> {
        return try {
            Result.success(
                remoteDataSource.getMovieRecommendations(movieId, 1)
                    .getOrThrow().movies?.map { it.toMovie() } ?: emptyList()
            )
        } catch (e: Exception) {
            Result.failure(e)
        }
    }


    override suspend fun clearAll() {
        localeDataSource.clearAll()
    }
}