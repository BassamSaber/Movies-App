package com.samz.banquemisr.challenge05.data.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity("movie_paging_key")
data class MovieTypePagingInfo(
    @PrimaryKey(autoGenerate = false)
    val movieTypeId: Int,
    val currentPageNo: Int,
    val totalPagesCount: Int
)