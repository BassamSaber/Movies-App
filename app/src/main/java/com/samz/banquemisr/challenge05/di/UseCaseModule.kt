package com.samz.banquemisr.challenge05.di

import com.samz.banquemisr.challenge05.data.datasource.LocaleDataSource
import com.samz.banquemisr.challenge05.data.datasource.RemoteDataSource
import com.samz.banquemisr.challenge05.data.repository.MoviesRemoteMediator
import com.samz.banquemisr.challenge05.domain.repository.MoviesRepository
import com.samz.banquemisr.challenge05.domain.usecase.GetMovieDetailsUseCase
import com.samz.banquemisr.challenge05.domain.usecase.GetMovieRecommendationsUseCase
import com.samz.banquemisr.challenge05.domain.usecase.GetMoviesListUseCase
import com.samz.banquemisr.challenge05.domain.usecase.GetPaginatedMoviesListUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object UseCaseModule {

    @Provides
    @Singleton
    fun provideMoviesRemoteMediator(
        remoteDataSource: RemoteDataSource,
        localeDataSource: LocaleDataSource
    ): MoviesRemoteMediator =
        MoviesRemoteMediator(remoteDataSource, localeDataSource)


    @Provides
    fun provideGetMoviesListUseCase(repository: MoviesRepository): GetMoviesListUseCase =
        GetMoviesListUseCase(repository)

    @Provides
    fun provideGetPaginatedMoviesListUseCase(
        repository: MoviesRepository
    ): GetPaginatedMoviesListUseCase =
        GetPaginatedMoviesListUseCase(repository)

    @Provides
    fun provideGetMovieDetailsUseCase(repository: MoviesRepository): GetMovieDetailsUseCase =
        GetMovieDetailsUseCase(repository)

    @Provides
    fun provideGetMovieRecommendationsUseCase(repository: MoviesRepository): GetMovieRecommendationsUseCase =
        GetMovieRecommendationsUseCase(repository)
}