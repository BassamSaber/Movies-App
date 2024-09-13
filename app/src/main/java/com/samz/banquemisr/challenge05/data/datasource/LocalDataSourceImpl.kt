package com.samz.banquemisr.challenge05.data.datasource

import androidx.paging.PagingSource
import com.samz.banquemisr.challenge05.core.MoviesType
import com.samz.banquemisr.challenge05.data.db.CachesLifeManager
import com.samz.banquemisr.challenge05.data.db.dao.MovieTypePagingDao
import com.samz.banquemisr.challenge05.data.db.dao.MoviesDao
import com.samz.banquemisr.challenge05.data.db.dao.MoviesListTypeIndexDao
import com.samz.banquemisr.challenge05.data.db.entity.MovieListTypeIndex
import com.samz.banquemisr.challenge05.data.db.entity.MovieTypePagingInfo
import com.samz.banquemisr.challenge05.data.remote.model.MovieDto
import javax.inject.Inject

class LocalDataSourceImpl @Inject constructor(
    private val moviesDao: MoviesDao,
    private val moviesListTypeIndexDao: MoviesListTypeIndexDao,
    private val movieTypePagingDao: MovieTypePagingDao,
    private val cachesLifeManager: CachesLifeManager
) : LocaleDataSource {

    override fun checkMoviesCacheValidation(moviesType: MoviesType) =
        cachesLifeManager.listCacheIsValid(moviesType)

    override fun checkMovieDetailsCacheValidation(movieId: Int): Boolean =
        cachesLifeManager.movieCacheIsValid(movieId)

    override suspend fun insetMovies(
        moviesType: MoviesType,
        pageNo: Int,
        totalPagesNo: Int,
        moviesList: List<MovieDto>
    ) {
        val movieListTypeIndexes =
            moviesList.map { MovieListTypeIndex(movieTypeId = moviesType.ordinal, movieId = it.id) }

        moviesDao.insertAll(moviesList)
        moviesListTypeIndexDao.insertAll(movieListTypeIndexes)
        movieTypePagingDao.createOrUpdate(
            MovieTypePagingInfo(
                moviesType.ordinal,
                pageNo,
                totalPagesNo
            )
        )

        cachesLifeManager.generateListCache(moviesType)
    }

    override suspend fun getMovies(moviesType: MoviesType): List<MovieDto>? =
        moviesDao.getMoviesByType(moviesType.ordinal)


    override fun moviesPagingSource(moviesType: MoviesType): PagingSource<Int, MovieDto> =
        moviesDao.pagingSource(moviesType.ordinal)

    override suspend fun insertOrUpdateMovie(movie: MovieDto) {
        moviesDao.createOrUpdate(movie)
        cachesLifeManager.generateMovieDetailsCache(movie.id)
    }

    override suspend fun getMovieDetails(movieId: Int): MovieDto? =
        moviesDao.getMovieDetails(movieId)

    override suspend fun getMoviesPagingInfo(moviesType: MoviesType): MovieTypePagingInfo? =
        movieTypePagingDao.getMoviePagingInfo(moviesType.ordinal)


    override suspend fun clearMovies(moviesType: MoviesType) {
        val moviesIds =
            moviesListTypeIndexDao.getListIndex(moviesType.ordinal)?.map { it.movieId }
        if (moviesIds.isNullOrEmpty())
            return

        moviesDao.deleteByIds(moviesIds)
        moviesListTypeIndexDao.deleteByType(moviesType.ordinal)
        movieTypePagingDao.deleteByType(moviesType.ordinal)
    }

    override suspend fun clearAll() {
        moviesDao.clearAll()
        moviesListTypeIndexDao.clearAll()
        movieTypePagingDao.clearAll()

        cachesLifeManager.clearCache()
    }

}