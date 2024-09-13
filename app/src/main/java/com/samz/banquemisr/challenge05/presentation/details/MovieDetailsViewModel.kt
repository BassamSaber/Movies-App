package com.samz.banquemisr.challenge05.presentation.details

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.samz.banquemisr.challenge05.domain.usecase.GetMovieDetailsUseCase
import com.samz.banquemisr.challenge05.domain.usecase.GetMovieRecommendationsUseCase
import com.samz.banquemisr.challenge05.presentation.DataState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieDetailsViewModel @Inject constructor(
    private val useCase: GetMovieDetailsUseCase,
    private val recommendationsUseCase: GetMovieRecommendationsUseCase
) : ViewModel() {
    private val mutableState: MutableStateFlow<DataState<MovieDetailsData>> =
        MutableStateFlow(DataState(isLoading = true))

    val state: StateFlow<DataState<MovieDetailsData>> = mutableState

    fun loadData(movieId: Int): Job =
        viewModelScope.launch {
            mutableState.update { it.copy(isLoading = true, initialized = true) }
            useCase.execute(movieId).onSuccess { movie ->
                mutableState.update {
                    it.copy(
                        isLoading = false, data = MovieDetailsData(
                            movie, emptyList()
                        )
                    )
                }

                recommendationsUseCase.execute(movieId).onSuccess { moviesList ->
                    if (moviesList.isNotEmpty())
                        mutableState.update {
                            it.copy(
                                data = it.data?.copy(recommendationsList = moviesList)
                            )
                        }
                }.onFailure {
                    Log.v("Failure", "error: ${it.message}")
                }

            }.onFailure { throwable ->
                mutableState.update { it.copy(isLoading = false, error = throwable.message) }
            }
        }
}