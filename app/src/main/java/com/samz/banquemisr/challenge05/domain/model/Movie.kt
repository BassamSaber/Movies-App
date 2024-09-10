package com.samz.banquemisr.challenge05.domain.model

import com.samz.banquemisr.challenge05.data.remote.model.MovieGenreDto

data class Movie(
    val id: Int,
    val title: String?,
    val originalTitle: String,
    val originalLanguage: String,
    val posterPath: String?,
    val backdropPath: String?,
    val isAnAdult: Boolean,
    val genres: List<MovieGenreDto>?,
    val overview: String? = "",
    val popularity: Double? = 0.0,
    val releaseDate: String? = "",
    val voteAverage: Double? = 0.0,
    val voteCount: Int? = 0,
    val runtime: Int?
)