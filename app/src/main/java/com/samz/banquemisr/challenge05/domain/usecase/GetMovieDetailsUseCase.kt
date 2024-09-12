package com.samz.banquemisr.challenge05.domain.usecase

import com.samz.banquemisr.challenge05.domain.model.Movie
import com.samz.banquemisr.challenge05.domain.repository.MoviesRepository
import javax.inject.Inject

class GetMovieDetailsUseCase @Inject constructor(private val repository: MoviesRepository) {
    suspend fun execute(movieId: Int): Result<Movie?> =
        repository.getMovieDetails(movieId)
}