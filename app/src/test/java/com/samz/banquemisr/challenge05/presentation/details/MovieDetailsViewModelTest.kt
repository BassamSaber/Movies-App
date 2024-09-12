package com.samz.banquemisr.challenge05.presentation.details

import app.cash.turbine.test
import com.samz.banquemisr.challenge05.domain.usecase.GetMovieDetailsUseCase
import com.samz.banquemisr.challenge05.domain.usecase.GetMovieRecommendationsUseCase
import com.samz.banquemisr.challenge05.helpers.MainCoroutineRule
import com.samz.banquemisr.challenge05.helpers.awaitItemPrecededBy
import com.samz.banquemisr.challenge05.helpers.movieModel
import com.samz.banquemisr.challenge05.presentation.DataState
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class MovieDetailsViewModelTest {

    @get:Rule
    val mainDispatcherRule = MainCoroutineRule()

    private val useCase: GetMovieDetailsUseCase = mockk()
    private val recommendationsUseCase: GetMovieRecommendationsUseCase = mockk()

    private val viewModel = MovieDetailsViewModel(useCase, recommendationsUseCase)


    @Test
    fun `Show loading state on init`() = runBlocking {
        viewModel.state.test {
            Assert.assertEquals(
                DataState<Any>(isLoading = true),
                awaitItemPrecededBy(DataState(isLoading = false))
            )
        }
    }


    @Test
    fun `Get correct Popular Movies`() = runBlocking {
        coEvery {
            useCase.execute(1)
        } returns Result.success(movieModel)
        coEvery {
            recommendationsUseCase.execute(1)
        } returns Result.success(emptyList())

        viewModel.state.test {
            Assert.assertEquals(
                DataState<Any>(isLoading = true),
                awaitItemPrecededBy(DataState(isLoading = false))
            )
            expectNoEvents()
            viewModel.loadData(1).join()

            Assert.assertEquals(
                DataState(
                    data = MovieDetailsData(
                        movie = movieModel,
                        recommendationsList = emptyList()
                    )
                ),
                awaitItemPrecededBy(DataState(isLoading = true))
            )
        }
    }

    @Test
    fun `Error in API`() = runBlocking {
        coEvery {
            useCase.execute(1)
        } returns Result.failure(Exception("TestErrorInAPI"))

        viewModel.state.test {
            Assert.assertEquals(
                DataState<Any>(isLoading = true),
                awaitItemPrecededBy(DataState(isLoading = false))
            )
            expectNoEvents()
            viewModel.loadData(1).join()

            Assert.assertEquals(
                DataState<Any>(error = "TestErrorInAPI"),
                awaitItemPrecededBy(DataState(isLoading = true))
            )
        }
    }
}