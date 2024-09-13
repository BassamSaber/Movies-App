package com.samz.banquemisr.challenge05.data.datasource

import androidx.paging.PagingSource
import com.samz.banquemisr.challenge05.core.MoviesType
import com.samz.banquemisr.challenge05.data.db.entity.MovieTypePagingInfo
import com.samz.banquemisr.challenge05.data.remote.model.MovieDto

interface LocaleDataSource {
    fun checkMoviesCacheValidation(moviesType: MoviesType): Boolean
    fun checkMovieDetailsCacheValidation(movieId: Int): Boolean

    suspend fun insetMovies(
        moviesType: MoviesType, pageNo: Int, totalPagesNo: Int,
        moviesList: List<MovieDto>
    )

    suspend fun getMovies(moviesType: MoviesType): List<MovieDto>?

    suspend fun insertOrUpdateMovie(movie: MovieDto)
    suspend fun getMovieDetails(movieId: Int): MovieDto?

    suspend fun getMoviesPagingInfo(moviesType: MoviesType): MovieTypePagingInfo?

    suspend fun clearMovies(moviesType: MoviesType)
    suspend fun clearAll()

    fun moviesPagingSource(moviesType: MoviesType): PagingSource<Int, MovieDto>
}