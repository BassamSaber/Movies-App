package com.samz.banquemisr.challenge05.data.repository

import com.samz.banquemisr.challenge05.core.MoviesType
import com.samz.banquemisr.challenge05.domain.model.Movie
import com.samz.banquemisr.challenge05.domain.repository.MoviesRepository

class MoviesRepositoryImpl : MoviesRepository {
    override suspend fun getMovies(moviesType: MoviesType, pageNo: Int): Result<List<Movie>> {
        TODO("Not yet implemented")
    }

    override suspend fun getMovieDetails(movieId: Int): Result<Movie?> {
        TODO("Not yet implemented")
    }

    override suspend fun getMovieRecommendation(movieId: Int): Result<List<Movie>> {
        TODO("Not yet implemented")
    }
}