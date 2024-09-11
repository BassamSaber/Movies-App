package com.samz.banquemisr.challenge05.data.datasource

import com.samz.banquemisr.challenge05.core.MoviesType
import com.samz.banquemisr.challenge05.data.remote.model.MovieDto

interface LocaleDataSource {
    fun checkMoviesCacheValidation(moviesType: MoviesType): Boolean
    fun checkMovieDetailsCacheValidation(movieId: Int): Boolean

    suspend fun insetMovies(moviesType: MoviesType, moviesList: List<MovieDto>)
    suspend fun getMovies(moviesType: MoviesType): List<MovieDto>

    suspend fun insertOrUpdateMovie(movie: MovieDto)
    suspend fun getMovieDetails(movieId: Int): MovieDto?

    suspend fun clearAll()
}