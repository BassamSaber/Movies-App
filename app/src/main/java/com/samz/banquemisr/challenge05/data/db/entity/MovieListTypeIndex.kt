package com.samz.banquemisr.challenge05.data.db.entity

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "movie_list_index",
    indices = [Index(value = ["movieTypeId", "movieId"], unique = true)]
)
data class MovieListTypeIndex(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val movieTypeId: Int = 0,
    val movieId: Int
)