package com.samz.banquemisr.challenge05.domain.model

import com.samz.banquemisr.challenge05.data.remote.model.MovieGenreDto
import java.util.Date

data class Movie(
    val id: Int,
    val title: String?,
    val originalTitle: String,
    val originalLanguage: String,
    val posterImgUrl: String?,
    val backdropImgUrl: String?,
    val isAnAdult: Boolean,
    val genres: List<MovieGenreDto>?,
    val overview: String? = "",
    val popularity: Double = 0.0,
    val releaseDate: Date?,
    val voteAverage: Float = 0.0f,
    val voteCount: Int = 0,
    val runtime: Int?
)