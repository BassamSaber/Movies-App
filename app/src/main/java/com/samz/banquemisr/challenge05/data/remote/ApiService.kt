package com.samz.banquemisr.challenge05.data.remote

import com.samz.banquemisr.challenge05.data.remote.model.MovieDto
import com.samz.banquemisr.challenge05.data.remote.model.MoviesListDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    @GET("movie/now_playing")
    suspend fun getNowPlayingMovies(
        @Query("page") page: Int

    ): Response<MoviesListDto>

    @GET("movie/popular")
    suspend fun getPopularMovies(
        @Query("page") page: Int
    ): Response<MoviesListDto>

    @GET("movie/upcoming")
    suspend fun getUpcomingMovies(
        @Query("page") page: Int
    ): Response<MoviesListDto>

    @GET("movie/{id}")
    suspend fun getMovieDetails(
        @Path("id") movieId: Int
    ): Response<MovieDto>

    @GET("movie/{id}/recommendations")
    suspend fun getMovieRecommendations(
        @Path("id") movieId: Int,
        @Query("page") page: Int
    ): Response<MoviesListDto>


    companion object {
        const val BASE_URL = "https://api.themoviedb.org/3/"
    }
}