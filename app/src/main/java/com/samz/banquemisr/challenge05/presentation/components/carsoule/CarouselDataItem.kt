package com.samz.banquemisr.challenge05.presentation.components.carsoule

import androidx.annotation.DrawableRes
import com.samz.banquemisr.challenge05.domain.model.Movie

data class CarouselDataItem(
    val id: Int,
    val imageUrl: String?,
    @DrawableRes val imagePlaceholderResId: Int? = null,
    val contentDescription: String? = ""
)

fun Movie.toCarouselDataItem(): CarouselDataItem =
    CarouselDataItem(id = id, imageUrl = backdropImgUrl)


fun getMoviesBanners(movies: List<Movie>): List<CarouselDataItem> {
    val list = movies.sortedByDescending { it.voteAverage }
    return (if (list.size > 3) list.subList(0, 3) else list).map { it.toCarouselDataItem() }
}