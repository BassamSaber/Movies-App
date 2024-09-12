package com.samz.banquemisr.challenge05.di

import com.samz.banquemisr.challenge05.data.datasource.LocalDataSourceImpl
import com.samz.banquemisr.challenge05.data.datasource.LocaleDataSource
import com.samz.banquemisr.challenge05.data.datasource.RemoteDataSource
import com.samz.banquemisr.challenge05.data.datasource.RemoteDataSourceImpl
import com.samz.banquemisr.challenge05.data.repository.MoviesRepositoryImpl
import com.samz.banquemisr.challenge05.domain.repository.MoviesRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
abstract class AppModule {

    @Binds
    @Singleton
    abstract fun bindRemoteDataSource(
        remoteDataSourceImpl: RemoteDataSourceImpl
    ): RemoteDataSource

    @Binds
    @Singleton
    abstract fun bindLocaleDataSource(
        localDataSourceImpl: LocalDataSourceImpl
    ): LocaleDataSource

    @Binds
    @Singleton
    abstract fun bindMoviesRepository(
        moviesRepositoryImpl: MoviesRepositoryImpl
    ): MoviesRepository
}