package com.samz.banquemisr.challenge05.data.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.samz.banquemisr.challenge05.data.db.entity.MovieTypePagingInfo

@Dao
interface MovieTypePagingDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(moviesPagingInfoList: List<MovieTypePagingInfo>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun createOrUpdate(movieTypePagingInfo: MovieTypePagingInfo)

    @Query("SELECT * FROM movie_paging_key WHERE movieTypeId ==:type")
    suspend fun getMoviePagingInfo(type: Int): MovieTypePagingInfo?

    @Query("DELETE FROM movie_paging_key WHERE movieTypeId ==:type")
    suspend fun deleteByType(type: Int)

    @Query("DELETE FROM movie_paging_key")
    suspend fun clearAll()
}