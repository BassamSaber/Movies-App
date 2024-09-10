package com.samz.banquemisr.challenge05.data.remote.model

import com.google.gson.annotations.SerializedName

data class MoviesListDto(
    @SerializedName("results")
    val movies: List<MovieDto>?,
    @SerializedName("page")
    val pageNo: Int,
    @SerializedName("total_pages")
    val totalPages: Int,
    @SerializedName("total_results")
    val totalResults: Int
)