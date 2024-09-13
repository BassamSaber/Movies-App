package com.samz.banquemisr.challenge05.data.remote.model

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "movies", indices = [Index(value = ["id"], unique = true)])
data class MovieDto(
    @PrimaryKey(autoGenerate = true)
    val autoGenerateKey: Int = 0,
    @SerializedName("id")
    val id: Int,
    @SerializedName("title")
    val title: String?,
    @SerializedName("original_title")
    val originalTitle: String,
    @SerializedName("original_language")
    val originalLanguage: String,
    @SerializedName("poster_path")
    val posterPath: String?,
    @SerializedName("backdrop_path")
    val backdropPath: String?,
    @SerializedName("adult")
    val isAnAdult: Boolean,
//    @SerializedName("genre_ids")
//    val genreIds: List<Int>?,
    @SerializedName("genres")
    val genres: List<MovieGenre>?,
    @SerializedName("overview")
    val overview: String? = "",
    @SerializedName("popularity")
    val popularity: Double? = 0.0,
    @SerializedName("release_date")
    val releaseDate: String? = "",
    @SerializedName("video")
    val video: Boolean? = false,
    @SerializedName("vote_average")
    val voteAverage: Double? = 0.0,
    @SerializedName("vote_count")
    val voteCount: Int? = 0,
    @SerializedName("runtime")
    val runtime: Int?,
    @SerializedName("revenue")
    val revenue: Long?
)