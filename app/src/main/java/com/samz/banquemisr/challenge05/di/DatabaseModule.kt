package com.samz.banquemisr.challenge05.di

import android.content.Context
import androidx.room.Room
import com.samz.banquemisr.challenge05.data.db.MoviesDatabase
import com.samz.banquemisr.challenge05.data.db.dao.MoviesDao
import com.samz.banquemisr.challenge05.data.db.dao.MoviesListTypeIndexDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {
    @Provides
    @Singleton
    fun provideBeerDatabase(@ApplicationContext context: Context): MoviesDatabase {
        return Room.databaseBuilder(
            context,
            MoviesDatabase::class.java,
            "repos.db"
        ).build()
    }

    @Provides
    @Singleton
    fun provideMoviesDao(database: MoviesDatabase): MoviesDao =
        database.moviesDao

    @Provides
    @Singleton
    fun provideMoviesListTypeIndexDao(database: MoviesDatabase): MoviesListTypeIndexDao =
        database.moviesListTypeIndexDao

}