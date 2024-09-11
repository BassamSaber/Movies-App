package com.samz.banquemisr.challenge05.data.datasource

import com.samz.banquemisr.challenge05.core.MoviesType
import com.samz.banquemisr.challenge05.data.db.CachesLifeManager
import com.samz.banquemisr.challenge05.data.db.dao.MoviesDao
import com.samz.banquemisr.challenge05.data.db.dao.MoviesListTypeIndexDao
import com.samz.banquemisr.challenge05.data.db.entity.MovieListTypeIndex
import com.samz.banquemisr.challenge05.data.remote.model.MovieDto
import javax.inject.Inject

class LocalDataSourceImpl @Inject constructor(
    private val moviesDao: MoviesDao,
    private val moviesListTypeIndexDao: MoviesListTypeIndexDao,
    private val cachesLifeManager: CachesLifeManager
) : LocaleDataSource {

    override fun checkMoviesCacheValidation(moviesType: MoviesType) =
        cachesLifeManager.listCacheIsValid(moviesType)

    override fun checkMovieDetailsCacheValidation(movieId: Int): Boolean =
        cachesLifeManager.movieCacheIsValid(movieId)

    override suspend fun insetMovies(moviesType: MoviesType, moviesList: List<MovieDto>) {
        val moviesIds = moviesList.map { it.id }

        moviesDao.insertAll(moviesList)
        moviesListTypeIndexDao.createOrUpdate(MovieListTypeIndex(moviesType.ordinal, moviesIds))

        cachesLifeManager.generateListCache(moviesType)
    }

    override suspend fun getMovies(moviesType: MoviesType): List<MovieDto> {
        val moviesTypeIndex = moviesListTypeIndexDao.getListIndex(moviesType.ordinal)
        return moviesDao.getMoviesByIds(moviesTypeIndex.moviesIds)
    }

    override suspend fun insertOrUpdateMovie(movie: MovieDto) {
        moviesDao.createOrUpdate(movie)
        cachesLifeManager.generateMovieDetailsCache(movie.id)
    }

    override suspend fun getMovieDetails(movieId: Int): MovieDto? =
        moviesDao.getMovieDetails(movieId)

    override suspend fun clearAll() {
        moviesDao.clearAll()
        moviesListTypeIndexDao.clearAll()

        cachesLifeManager.clearCache()
    }

}