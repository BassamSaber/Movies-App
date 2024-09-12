package com.samz.banquemisr.challenge05.helpers

import com.samz.banquemisr.challenge05.data.mappers.toMovie
import com.samz.banquemisr.challenge05.data.remote.model.MovieDto
import com.samz.banquemisr.challenge05.data.remote.model.MoviesListDto
import com.samz.banquemisr.challenge05.presentation.components.carsoule.toCarouselDataItem
import com.samz.banquemisr.challenge05.presentation.home.HomeData

val expectMovieDto = MovieDto(
    id = 1,
    title = "Trap",
    originalTitle = "Trap",
    originalLanguage = "en",
    posterPath = "/jwoaKYVqPgYemFpaANL941EF94R.jpg",
    backdropPath = "/p5kpFS0P3lIwzwzHBOULQovNWyj.jpg",
    isAnAdult = false,
    movieGenreDtos = null,
    overview = "A father and teen daughter attend a pop concert, where they realize they're at the center of a dark and sinister event.",
    popularity = 790.927,
    releaseDate = "2024-07-31",
    video = false,
    voteAverage = 6.53,
    voteCount = 968,
    runtime = null,
    revenue = null
)

val expectedMovieDtoList = listOf(expectMovieDto)
val expectMoviesListResultDto = MoviesListDto(expectedMovieDtoList, 1, 1, 1)

val movieModel = expectMovieDto.toMovie()
val expectMoviesList = listOf(movieModel)
val expectedHomeData = HomeData(expectMoviesList, expectMoviesList.map { it.toCarouselDataItem() })
