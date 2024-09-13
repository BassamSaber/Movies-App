package com.samz.banquemisr.challenge05.presentation.list

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import com.samz.banquemisr.challenge05.core.MoviesType
import com.samz.banquemisr.challenge05.domain.model.Movie
import com.samz.banquemisr.challenge05.domain.usecase.GetPaginatedMoviesListUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MoviesListViewModel @Inject constructor(private val useCase: GetPaginatedMoviesListUseCase) :
    ViewModel() {

    var pagingResult: Flow<PagingData<Movie>> = emptyFlow()

    fun loadMovies(moviesType: MoviesType) {
        viewModelScope.launch {
            try {
                pagingResult = useCase.execute(moviesType)
            } catch (e: Exception) {
                Log.v("MoviesListViewModel", "Error: ${e.message}")
            }
        }
    }
}