package com.samz.banquemisr.challenge05.data.mappers

import com.samz.banquemisr.challenge05.core.toDate
import com.samz.banquemisr.challenge05.core.toFullImageUrl
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
    movieGenreDtos,
    overview,
    popularity ?: 0.0,
    if (releaseDate.isNullOrEmpty()) null else releaseDate.toDate(),
    ((voteAverage ?: 0.0) / 10.0).toFloat(),
    voteCount ?: 0,
    runtime
)