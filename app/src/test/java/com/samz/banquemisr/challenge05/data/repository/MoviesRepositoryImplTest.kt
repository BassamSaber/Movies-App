package com.samz.banquemisr.challenge05.data.repository

import com.samz.banquemisr.challenge05.core.MoviesType
import com.samz.banquemisr.challenge05.data.datasource.LocaleDataSource
import com.samz.banquemisr.challenge05.data.datasource.RemoteDataSource
import com.samz.banquemisr.challenge05.data.mappers.toMovie
import com.samz.banquemisr.challenge05.helpers.expectMovieDto
import com.samz.banquemisr.challenge05.helpers.expectMoviesListResultDto
import com.samz.banquemisr.challenge05.helpers.expectedMovieDtoList
import io.mockk.coEvery
import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import io.mockk.runs
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class MoviesRepositoryImplTest {
    private val remote: RemoteDataSource = mockk()
    private val locale: LocaleDataSource = mockk()

    private val repository = MoviesRepositoryImpl(remote, locale)

    @Before
    fun setup() {
        Dispatchers.setMain(Dispatchers.Unconfined)
    }

    @After
    fun teardown() {
        Dispatchers.resetMain()
    }

    //popular movies tests
    @Test
    fun `Retrieve popular movies list from remote data source`() = runBlocking {
        val movieType = MoviesType.Popular
        coEvery { locale.checkMoviesCacheValidation(movieType) } returns false
        coEvery { locale.insetMovies(movieType, any()) } just runs
        coEvery {
            remote.getMovies(movieType, 1)
        } returns Result.success(expectMoviesListResultDto)

        val list = repository.getMovies(movieType).getOrNull()
        assertEquals(list, expectMoviesListResultDto.movies?.map { it.toMovie() })
    }

    @Test
    fun `Retrieve popular movies list from locale data source`() = runBlocking {
        val movieType = MoviesType.Popular
        every { locale.checkMoviesCacheValidation(movieType) } returns true
        coEvery { locale.getMovies(movieType) } returns expectedMovieDtoList

        val list = repository.getMovies(movieType).getOrNull()
        assertEquals(list, expectMoviesListResultDto.movies?.map { it.toMovie() })
    }

    @Test
    fun `Fail to retrieve popular movies list from remote data source`() = runBlocking {
        val movieType = MoviesType.Popular
        val ex = Exception("No API data")
        every { locale.checkMoviesCacheValidation(movieType) } returns false
        coEvery {
            remote.getMovies(movieType, 1)
        } returns Result.failure(ex)

        val exception = repository.getMovies(movieType).exceptionOrNull()
        assertEquals(exception, ex)
    }

    //upcoming movies tests
    @Test
    fun `Retrieve upcoming movies list from remote data source`() = runBlocking {
        val movieType = MoviesType.Upcoming
        coEvery { locale.checkMoviesCacheValidation(movieType) } returns false
        coEvery { locale.insetMovies(movieType, any()) } just runs
        coEvery {
            remote.getMovies(movieType, 1)
        } returns Result.success(expectMoviesListResultDto)

        val list = repository.getMovies(movieType).getOrNull()
        assertEquals(list, expectMoviesListResultDto.movies?.map { it.toMovie() })
    }

    @Test
    fun `Retrieve upcoming movies list from locale data source`() = runBlocking {
        val movieType = MoviesType.Upcoming
        every { locale.checkMoviesCacheValidation(movieType) } returns true
        coEvery { locale.getMovies(movieType) } returns expectedMovieDtoList

        val list = repository.getMovies(movieType).getOrNull()
        assertEquals(list, expectMoviesListResultDto.movies?.map { it.toMovie() })
    }

    @Test
    fun `Fail to retrieve upcoming movies list from remote data source`() = runBlocking {
        val movieType = MoviesType.Upcoming
        val ex = Exception("No API data")
        every { locale.checkMoviesCacheValidation(movieType) } returns false
        coEvery {
            remote.getMovies(movieType, 1)
        } returns Result.failure(ex)

        val exception = repository.getMovies(movieType).exceptionOrNull()
        assertEquals(exception, ex)
    }

    //now playing movies tests
    @Test
    fun `Retrieve now playing movies list from remote data source`() = runBlocking {
        val movieType = MoviesType.NowPlaying
        coEvery { locale.checkMoviesCacheValidation(movieType) } returns false
        coEvery { locale.insetMovies(movieType, any()) } just runs
        coEvery {
            remote.getMovies(movieType, 1)
        } returns Result.success(expectMoviesListResultDto)

        val list = repository.getMovies(movieType).getOrNull()
        assertEquals(list, expectMoviesListResultDto.movies?.map { it.toMovie() })
    }

    @Test
    fun `Retrieve now playing movies list from locale data source`() = runBlocking {
        val movieType = MoviesType.NowPlaying
        every { locale.checkMoviesCacheValidation(movieType) } returns true
        coEvery { locale.getMovies(movieType) } returns expectedMovieDtoList

        val list = repository.getMovies(movieType).getOrNull()
        assertEquals(list, expectMoviesListResultDto.movies?.map { it.toMovie() })
    }

    @Test
    fun `Fail to retrieve now playing movies list from remote data source`() = runBlocking {
        val movieType = MoviesType.NowPlaying
        val ex = Exception("No API data")
        every { locale.checkMoviesCacheValidation(movieType) } returns false
        coEvery {
            remote.getMovies(movieType, 1)
        } returns Result.failure(ex)

        val exception = repository.getMovies(movieType).exceptionOrNull()
        assertEquals(exception, ex)
    }

    //movie details tests
    @Test
    fun `Retrieve movie details from remote data source`() = runBlocking {
        val movieId = 1
        coEvery { locale.checkMovieDetailsCacheValidation(movieId) } returns false
        coEvery { locale.insertOrUpdateMovie(expectMovieDto) } just runs
        coEvery {
            remote.getMovieDetails(movieId)
        } returns Result.success(expectMovieDto)

        val list = repository.getMovieDetails(movieId).getOrNull()
        assertEquals(list, expectMovieDto.toMovie())
    }

    @Test
    fun `Retrieve movie details from locale data source`() = runBlocking {
        val movieId = 1
        coEvery { locale.checkMovieDetailsCacheValidation(movieId) } returns true
        coEvery {
            locale.getMovieDetails(movieId)
        } returns expectMovieDto

        val list = repository.getMovieDetails(movieId).getOrNull()
        assertEquals(list, expectMovieDto.toMovie())
    }

    @Test
    fun `Fail to retrieve movie details from remote data source`() = runBlocking {
        val ex = Exception("No API data")
        val movieId = 1
        coEvery { locale.checkMovieDetailsCacheValidation(movieId) } returns false
        coEvery {
            remote.getMovieDetails(movieId)
        } returns Result.failure(ex)

        val exception = repository.getMovieDetails(movieId).exceptionOrNull()
        assertEquals(exception, ex)
    }

    //movie recommendations tests
    @Test
    fun `Retrieve movie recommendations list from remote data source`() = runBlocking {
        val movieId = 1
        coEvery {
            remote.getMovieRecommendations(movieId, 1)
        } returns Result.success(expectMoviesListResultDto)

        val list = repository.getMovieRecommendation(movieId).getOrNull()
        assertEquals(list, expectMoviesListResultDto.movies?.map { it.toMovie() })
    }

    @Test
    fun `Fail to retrieve movie recommendations list from remote data source`() = runBlocking {
        val movieId = 1
        val ex = Exception("No API data")
        coEvery {
            remote.getMovieRecommendations(movieId, 1)
        } returns Result.failure(ex)

        val exception = repository.getMovieRecommendation(movieId).exceptionOrNull()
        assertEquals(exception, ex)
    }


}