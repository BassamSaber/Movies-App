package com.samz.banquemisr.challenge05.data.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "movie_list_index")
data class MovieListTypeIndex(
    @PrimaryKey(autoGenerate = false)
    val id: Int = 0,
    val moviesIds: List<Int> = emptyList()
)