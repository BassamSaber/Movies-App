package com.samz.banquemisr.challenge05.data.db.entity

import androidx.room.PrimaryKey
import com.samz.banquemisr.challenge05.data.remote.model.MovieGenre

//@Entity("movies")
data class MovieEntity(
    @PrimaryKey(autoGenerate = false)
    val id: Int,
    val movieTypeId: Int,
    val title: String?,
    val originalTitle: String,
    val originalLanguage: String,
    val posterPath: String?,
    val backdropPath: String?,
    val isAnAdult: Boolean,
    val genre: List<MovieGenre>?,
    val overview: String?,
    val popularity: Double,
    val releaseDate: String?,
    val video: Boolean?,
    val voteAverage: Double?,
    val voteCount: Int? = 0,
    val runtime: Int?,
    val revenue: Long?
)
