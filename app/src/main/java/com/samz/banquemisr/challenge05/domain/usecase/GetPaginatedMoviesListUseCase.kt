package com.samz.banquemisr.challenge05.domain.usecase

import androidx.paging.PagingData
import com.samz.banquemisr.challenge05.core.MoviesType
import com.samz.banquemisr.challenge05.domain.model.Movie
import com.samz.banquemisr.challenge05.domain.repository.MoviesRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetPaginatedMoviesListUseCase @Inject constructor(
    private val repository: MoviesRepository
) {
    fun execute(moviesType: MoviesType): Flow<PagingData<Movie>> =
        repository.moviesPagingSource(moviesType)

}