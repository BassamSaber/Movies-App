package com.samz.banquemisr.challenge05.data.repository

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import com.samz.banquemisr.challenge05.core.MoviesType
import com.samz.banquemisr.challenge05.data.datasource.LocaleDataSource
import com.samz.banquemisr.challenge05.data.datasource.RemoteDataSource
import com.samz.banquemisr.challenge05.data.remote.model.MovieDto

@OptIn(ExperimentalPagingApi::class)
class MoviesRemoteMediator(
    private val remoteDataSource: RemoteDataSource,
    private val localeDataSource: LocaleDataSource,
) : RemoteMediator<Int, MovieDto>() {

    var moviesType: MoviesType = MoviesType.NowPlaying

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, MovieDto>
    ): MediatorResult {
        val page = when (loadType) {
            LoadType.REFRESH -> 1
            LoadType.PREPEND -> return MediatorResult.Success(endOfPaginationReached = true)
            LoadType.APPEND -> {
                state.lastItemOrNull()
                    ?: return MediatorResult.Success(endOfPaginationReached = true)

                // Calculate the next page
                val pagingInfo = localeDataSource.getMoviesPagingInfo(moviesType)

                val pageNo = pagingInfo?.currentPageNo ?: 0
//                if (pageNo > 2) return MediatorResult.Success(endOfPaginationReached = true)

                pageNo + 1
            }
        }

        return try {
            // Fetch movies using the dynamically set movieId
            val response = remoteDataSource.getMovies(moviesType, page).getOrThrow()

            if (loadType == LoadType.REFRESH) {
                localeDataSource.clearMovies(moviesType)
            }

            localeDataSource.insetMovies(
                moviesType,
                response.pageNo,
                response.totalPages,
                response.movies ?: emptyList()
            )

            MediatorResult.Success(
                endOfPaginationReached = response.movies.isNullOrEmpty() ||
                        response.pageNo == response.totalPages
            )
        } catch (e: Exception) {
            MediatorResult.Error(e)
        }
    }

}