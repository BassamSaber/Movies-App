package com.samz.banquemisr.challenge05.domain.usecase

import com.samz.banquemisr.challenge05.core.MoviesType
import com.samz.banquemisr.challenge05.domain.model.Movie
import com.samz.banquemisr.challenge05.domain.repository.MoviesRepository
import javax.inject.Inject

class GetMoviesListUseCase @Inject constructor(private val repository: MoviesRepository) {
    suspend fun execute(moviesType: MoviesType): Result<List<Movie>> =
        repository.getMovies(moviesType)

}