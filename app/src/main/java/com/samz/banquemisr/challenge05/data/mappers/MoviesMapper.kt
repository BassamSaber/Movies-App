package com.samz.banquemisr.challenge05.data.mappers

import com.samz.banquemisr.challenge05.core.MoviesType
import com.samz.banquemisr.challenge05.core.toDate
import com.samz.banquemisr.challenge05.core.toFullImageUrl
import com.samz.banquemisr.challenge05.data.db.entity.MovieEntity
import com.samz.banquemisr.challenge05.data.remote.model.MovieDto
import com.samz.banquemisr.challenge05.domain.model.Movie

fun MovieDto.toMovie(): Movie = Movie(
    id,
    title,
    originalTitle,
    originalLanguage,
    posterPath?.toFullImageUrl(),
    backdropPath?.toFullImageUrl(),
    isAnAdult,
    genres,
    overview,
    popularity ?: 0.0,
    if (releaseDate.isNullOrEmpty()) null else releaseDate.toDate(),
    ((voteAverage ?: 0.0) / 10.0).toFloat(),
    voteCount ?: 0,
    runtime
)

fun MovieDto.toMovieEntity(moviesType: MoviesType): MovieEntity = MovieEntity(
    id,
    moviesType.ordinal,
    title,
    originalTitle,
    originalLanguage,
    posterPath?.toFullImageUrl(),
    backdropPath?.toFullImageUrl(),
    isAnAdult,
    genres,
    overview,
    popularity ?: 0.0,
    releaseDate,
    video,
    voteAverage,
    voteCount ?: 0,
    runtime,
    revenue
)

fun MovieEntity.toMovie(): Movie = Movie(
    id,
    title,
    originalTitle,
    originalLanguage,
    posterPath?.toFullImageUrl(),
    backdropPath?.toFullImageUrl(),
    isAnAdult,
    genre,
    overview,
    popularity ?: 0.0,
    if (releaseDate.isNullOrEmpty()) null else releaseDate.toDate(),
    ((voteAverage ?: 0.0) / 10.0).toFloat(),
    voteCount ?: 0,
    runtime
)

