package com.samz.banquemisr.challenge05.di

import com.samz.banquemisr.challenge05.domain.repository.MoviesRepository
import com.samz.banquemisr.challenge05.domain.usecase.GetMovieDetailsUseCase
import com.samz.banquemisr.challenge05.domain.usecase.GetMovieRecommendationsUseCase
import com.samz.banquemisr.challenge05.domain.usecase.GetMoviesListUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent


@Module
@InstallIn(SingletonComponent::class)
object UseCaseModule {
    @Provides
    fun provideGetMoviesListUseCase(repository: MoviesRepository): GetMoviesListUseCase =
        GetMoviesListUseCase(repository)

    @Provides
    fun provideGetMovieDetailsUseCase(repository: MoviesRepository): GetMovieDetailsUseCase =
        GetMovieDetailsUseCase(repository)

    @Provides
    fun provideGetMovieRecommendationsUseCase(repository: MoviesRepository): GetMovieRecommendationsUseCase =
        GetMovieRecommendationsUseCase(repository)
}