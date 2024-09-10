package com.samz.banquemisr.challenge05.data.remote.model

import com.google.gson.annotations.SerializedName

data class MovieGenreDto(
    @SerializedName("id")
    val id: Int,
    @SerializedName("name")
    val name: String
)