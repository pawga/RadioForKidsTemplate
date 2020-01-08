package com.pawga.radio.data.db

import androidx.lifecycle.LiveData
import androidx.paging.DataSource
import androidx.room.*

/**
 * Created by sivannikov
 */


@Dao
interface RadioStationDao {

    //insert

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertRadioStationItem(station: RadioStationDBItem): Long

    @Transaction
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAllRadioStationItems(list: List<RadioStationDBItem>): LongArray

    // delete

    @Delete
    suspend fun deleteList(list: List<RadioStationDBItem>)

    @Query("DELETE FROM radio_station")
    fun deleteAll()

    // query

    @Query("SELECT COUNT(*) FROM radio_station")
    fun getCount(): Long

    @Query("SELECT * FROM radio_station")
    fun getStations(): LiveData<List<RadioStationDBItem>>

    @Query("SELECT * FROM radio_station")
    fun getStationsV2(): List<RadioStationDBItem>

    @Query("SELECT * FROM radio_station ORDER BY radio_station.id ASC")
    fun getStationsWithStreams(): List<RadioStationItem>

    @Query("SELECT * FROM radio_station ORDER BY radio_station.id ASC")
    fun getAllStationsFactory(): DataSource.Factory<Int, RadioStationItem>

    @Query(
            "SELECT radio_station.* " +
                    "FROM radio_station, stream_item " +
                    "WHERE radio_station.id == stream_item.statio_id" +
                    " AND radio_station.locale" + " IN (:locales)" +
                    " AND stream_item.bitrate <= :bitrate" +
                    " group by radio_station.id" +
                    " ORDER BY radio_station.time DESC"
    )
    fun getStationsWithStreamsFilterByBitrate(locales: List<String>, bitrate: Long): List<RadioStationItem>
}