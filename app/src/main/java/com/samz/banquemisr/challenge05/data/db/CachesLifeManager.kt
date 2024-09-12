package com.samz.banquemisr.challenge05.data.db


import android.app.Application
import android.content.Context
import com.samz.banquemisr.challenge05.core.MoviesType
import java.util.Calendar
import java.util.TimeZone
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class CachesLifeManager @Inject constructor(app: Application) {
    companion object {
        private const val FILE_NAME = "caches"

        /* Caches data keys */
        private const val MOVIE_DETAILS_CACHE = "movie_"
        private const val MOVIES_LIST_POPULAR_KEY = "movies_list_popular"
        private const val MOVIES_LIST_UPCOMING_KEY = "movies_list_upcoming"
        private const val MOVIES_LIST_NO_PLAYING_KEY = "movies_list_top_rated"

        /* Caches lifetime keys */
        private val MOVIE_DETAILS_LIFE_TIME = TimeUnit.MILLISECONDS.convert(15, TimeUnit.DAYS)
        private val MOVIES_LIST_POPULAR_LIFE_TIME =
            TimeUnit.MILLISECONDS.convert(2, TimeUnit.DAYS)
        private val MOVIES_LIST_UPCOMING_LIFE_TIME =
            TimeUnit.MILLISECONDS.convert(12, TimeUnit.HOURS)
        private val MOVIES_LIST_NO_PLAYING_LIFE_TIME =
            TimeUnit.MILLISECONDS.convert(1, TimeUnit.DAYS)
    }

    private val preferences = app.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE)

    private fun getCurrentDate() = Calendar.getInstance(TimeZone.getDefault()).timeInMillis

    private fun isCacheValid(key: String): Boolean {
        val currentDate = getCurrentDate()
        val lastFetch = preferences.getLong(key, currentDate)
        return lastFetch > currentDate
    }

    private fun generateCache(key: String = "", lifeTime: Long = 0L) =
        preferences.edit().apply {
            putLong(key, getCurrentDate() + lifeTime)
        }.apply()

    fun generateMovieDetailsCache(movieId: Int) {
        val movieKey = "$MOVIE_DETAILS_CACHE$movieId"
        generateCache(movieKey, MOVIE_DETAILS_LIFE_TIME)
    }

    fun movieCacheIsValid(movieId: Int) = "$MOVIE_DETAILS_CACHE$movieId".run {
        isCacheValid(this)
    }

    fun listCacheIsValid(type: MoviesType) = when (type) {
        MoviesType.Popular -> isCacheValid(MOVIES_LIST_POPULAR_KEY)
        MoviesType.Upcoming -> isCacheValid(MOVIES_LIST_UPCOMING_KEY)
        MoviesType.NowPlaying -> isCacheValid(MOVIES_LIST_NO_PLAYING_KEY)
    }

    fun generateListCache(type: MoviesType) = when (type) {
        MoviesType.Popular ->
            generateCache(MOVIES_LIST_POPULAR_KEY, MOVIES_LIST_POPULAR_LIFE_TIME)

        MoviesType.Upcoming ->
            generateCache(MOVIES_LIST_UPCOMING_KEY, MOVIES_LIST_UPCOMING_LIFE_TIME)

        MoviesType.NowPlaying ->
            generateCache(MOVIES_LIST_NO_PLAYING_KEY, MOVIES_LIST_NO_PLAYING_LIFE_TIME)
    }

    fun clearCache() {
        preferences.edit().clear().apply()
    }
}