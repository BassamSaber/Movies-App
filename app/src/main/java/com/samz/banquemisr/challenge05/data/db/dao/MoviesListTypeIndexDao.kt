package com.samz.banquemisr.challenge05.data.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.samz.banquemisr.challenge05.data.db.entity.MovieListTypeIndex

@Dao
interface MoviesListTypeIndexDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(moviesIndexList: List<MovieListTypeIndex>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun createOrUpdate(movieIndex: MovieListTypeIndex)

    /**
     * Reads the index of a list type.
     * The integer is retrieved from the [MoviesType] enum class ordinal value.
     */
    @Query("SELECT * FROM movie_list_index WHERE movieTypeId ==:type")
    suspend fun getListIndex(type: Int): List<MovieListTypeIndex>?

//    @Delete
//    suspend fun delete(movieIndex: MovieListTypeIndex)

    @Query("DELETE FROM movie_list_index WHERE movieTypeId ==:type")
    suspend fun deleteByType(type: Int)

    @Query("DELETE FROM movie_list_index")
    suspend fun clearAll()
}