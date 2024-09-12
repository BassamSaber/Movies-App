package com.samz.banquemisr.challenge05.presentation.home

sealed class MovieIntent {
    object LoadNowPlaying : MovieIntent()
    object LoadUpcoming : MovieIntent()
    object LoadPopular : MovieIntent()
}
