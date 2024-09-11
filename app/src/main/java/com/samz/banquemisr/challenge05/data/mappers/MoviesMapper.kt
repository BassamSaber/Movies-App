package com.samz.banquemisr.challenge05.data.mappers

import com.samz.banquemisr.challenge05.data.remote.model.MovieDto
import com.samz.banquemisr.challenge05.domain.model.Movie

fun MovieDto.toMovie(): Movie = Movie(
    id, title, originalTitle, originalLanguage, posterPath, backdropPath,
    isAnAdult, movieGenreDtos, overview, popularity, releaseDate,
    voteAverage, voteCount, runtime
)