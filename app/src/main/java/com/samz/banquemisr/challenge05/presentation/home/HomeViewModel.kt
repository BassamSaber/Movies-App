package com.samz.banquemisr.challenge05.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.samz.banquemisr.challenge05.core.MoviesType
import com.samz.banquemisr.challenge05.domain.usecase.GetMoviesListUseCase
import com.samz.banquemisr.challenge05.presentation.DataState
import com.samz.banquemisr.challenge05.presentation.components.carsoule.getMoviesBanners
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val useCase: GetMoviesListUseCase) : ViewModel() {
    private val mutableState: MutableStateFlow<DataState<HomeData>> =
        MutableStateFlow(DataState(isLoading = true))

    val state: StateFlow<DataState<HomeData>> = mutableState

    init {
        loadData(MoviesType.NowPlaying)
    }

    private fun loadData(moviesType: MoviesType) {
        viewModelScope.launch {
            mutableState.update { it.copy(isLoading = true) }

            useCase.execute(moviesType)
                .onSuccess { moviesList ->
                    mutableState.update {
                        it.copy(
                            isLoading = false,
                            isEmpty = moviesList.isEmpty(),
                            data = HomeData(
                                movies = moviesList,
                                banners = getMoviesBanners(moviesList)
                            )
                        )
                    }
                }
                .onFailure { throwable ->
                    mutableState.update {
                        it.copy(
                            isLoading = false,
                            error = throwable.message ?: "Something went wrong"
                        )
                    }
                }
        }
    }

    fun sendEvent(movieIntent: MovieIntent) {
        when (movieIntent) {
            MovieIntent.LoadNowPlaying -> loadData(MoviesType.NowPlaying)
            MovieIntent.LoadPopular -> loadData(MoviesType.Popular)
            MovieIntent.LoadUpcoming -> loadData(MoviesType.Upcoming)
        }
    }
}