package com.samz.banquemisr.challenge05.presentation.home

import app.cash.turbine.test
import com.samz.banquemisr.challenge05.core.MoviesType
import com.samz.banquemisr.challenge05.domain.usecase.GetMoviesListUseCase
import com.samz.banquemisr.challenge05.helpers.MainCoroutineRule
import com.samz.banquemisr.challenge05.helpers.awaitItemPrecededBy
import com.samz.banquemisr.challenge05.helpers.expectMoviesList
import com.samz.banquemisr.challenge05.helpers.expectedHomeData
import com.samz.banquemisr.challenge05.presentation.DataState
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class HomeViewModelTest {

    @get:Rule
    val mainDispatcherRule = MainCoroutineRule()

    private val useCase: GetMoviesListUseCase = mockk()

    private val viewModel = HomeViewModel(useCase)


    @Test
    fun `Show loading state on init`() = runBlocking {
        viewModel.state.test {
            assertEquals(
                DataState<Any>(isLoading = true),
                awaitItemPrecededBy(DataState(isLoading = false))
            )
        }
    }


    @Test
    fun `Get correct Popular Movies`() = runBlocking {
        val movieType = MoviesType.Popular
        coEvery {
            useCase.execute(movieType)
        } returns Result.success(expectMoviesList)

        viewModel.state.test {
            assertEquals(
                DataState<Any>(isLoading = true),
                awaitItemPrecededBy(DataState(isLoading = false))
            )
            expectNoEvents()
            viewModel.loadData(movieType).join()

            assertEquals(
                DataState(data = expectedHomeData),
                awaitItemPrecededBy(DataState(isLoading = true))
            )
        }
    }

    @Test
    fun `Error in API`() = runBlocking {
        val movieType = MoviesType.Popular
        coEvery {
            useCase.execute(movieType)
        } returns Result.failure(Exception("TestErrorInAPI"))

        viewModel.state.test {
            assertEquals(
                DataState<Any>(isLoading = true),
                awaitItemPrecededBy(DataState(isLoading = false))
            )
            expectNoEvents()
            viewModel.loadData(movieType)

            assertEquals(
                DataState<Any>(error = "TestErrorInAPI"),
                awaitItemPrecededBy(DataState(isLoading = true))
            )
        }
    }
}