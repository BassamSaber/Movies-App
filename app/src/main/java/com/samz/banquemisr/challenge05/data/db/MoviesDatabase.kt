package com.samz.banquemisr.challenge05.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.samz.banquemisr.challenge05.data.db.dao.MoviesDao
import com.samz.banquemisr.challenge05.data.db.dao.MoviesListTypeIndexDao
import com.samz.banquemisr.challenge05.data.db.entity.MovieListTypeIndex
import com.samz.banquemisr.challenge05.data.db.typeconverters.IntListTypeConverter
import com.samz.banquemisr.challenge05.data.db.typeconverters.MovieGenreTypeConverter
import com.samz.banquemisr.challenge05.data.remote.model.MovieDto

@Database(
    entities = [MovieDto::class, MovieListTypeIndex::class],
    version = 1
)
@TypeConverters(IntListTypeConverter::class, MovieGenreTypeConverter::class)
abstract class MoviesDatabase : RoomDatabase() {
    abstract val moviesDao: MoviesDao
    abstract val moviesListTypeIndexDao: MoviesListTypeIndexDao
}