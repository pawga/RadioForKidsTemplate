package com.pawga.radio.data.db

import androidx.lifecycle.LiveData
import androidx.room.*

/**
 * Created by sivannikov
 */

@Dao
interface StreamDBDao {
    @Query("SELECT * FROM stream_item")
    fun getStreams(): LiveData<List<SreamItem>>

    @Transaction
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(streams: List<SreamItem>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertStreamItem(stream: SreamItem): Long

    @Query("DELETE FROM stream_item")
    fun deleteAll()
}