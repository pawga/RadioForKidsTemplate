package com.pawga.radio.data.db

import androidx.room.*
import java.util.*

/**
 * Created by sivannikov
 */


@Entity(
        tableName = "radio_station",
        indices = [Index("name_station"), Index("type"), Index("locale")]
)
data class RadioStationDBItem (
        @PrimaryKey(autoGenerate = true)
        @ColumnInfo(name = "id")
        val id: Long = 0L,
        @ColumnInfo(name = "description") val description: String? = null,
        @ColumnInfo(name = "id_station") val idStation: String? = null,
        @ColumnInfo(name = "image") val image: String? = null,
        @ColumnInfo(name = "locale") val locale: String? = null,
        @ColumnInfo(name = "name_station") val nameStation: String? = null,
        @ColumnInfo(name = "type") val type: String? = null,
        @ColumnInfo(name = "time") val time: Date? = null
)

class RadioStationItem {
        @Embedded
        var station: RadioStationDBItem? = null

        @Relation(parentColumn = "id", entityColumn = "statio_id")
        var streams: List<SreamItem>? = null
}
