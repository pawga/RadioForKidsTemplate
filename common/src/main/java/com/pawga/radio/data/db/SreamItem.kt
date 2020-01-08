package com.pawga.radio.data.db

import androidx.room.*

/**
 * Created by sivannikov
 */

@Entity(
        tableName = "stream_item",
        indices = [Index("id_stream"), Index("statio_id")],
        foreignKeys = [ForeignKey(
                entity = RadioStationDBItem::class,
                parentColumns = ["id"],
                childColumns = ["statio_id"],
                onDelete = ForeignKey.CASCADE
        )]
)
data class SreamItem (
        @PrimaryKey(autoGenerate = true)
        @ColumnInfo(name = "id")
        val id: Long = 0L,
        @ColumnInfo(name = "statio_id") val statioId: Long = 0L,
        @ColumnInfo(name = "bitrate") val bitrate: Int = 0,
        @ColumnInfo(name = "id_stream") val idStream: String? = null,
        @ColumnInfo(name = "stream") val stream: String? = null
)