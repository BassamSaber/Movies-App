package com.samz.banquemisr.challenge05.presentation.home

import com.samz.banquemisr.challenge05.core.MoviesType
import com.samz.banquemisr.challenge05.domain.model.Movie
import com.samz.banquemisr.challenge05.presentation.components.carsoule.CarouselDataItem

data class HomeData(
    val moviesType: MoviesType = MoviesType.NowPlaying,
    val movies: List<Movie>,
    val banners: List<CarouselDataItem>? = null
)