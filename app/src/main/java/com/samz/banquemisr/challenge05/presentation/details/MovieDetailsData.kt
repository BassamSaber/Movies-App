package com.samz.banquemisr.challenge05.presentation.details

import com.samz.banquemisr.challenge05.domain.model.Movie

data class MovieDetailsData(
    val movie: Movie?,
    val recommendationsList: List<Movie>
)
