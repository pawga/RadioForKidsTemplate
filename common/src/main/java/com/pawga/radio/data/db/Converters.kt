package com.pawga.radio.data.db

import androidx.room.TypeConverter
import java.util.*

/**
 * Created by sivannikov
 */

class Converters {
    @TypeConverter
    fun fromTimestamp(value: Long): Date? {
        return if (value == 0L) {
            null
        } else {
            Date(value)
        }
    }

    @TypeConverter
    fun dateToTimestamp(date: Date?): Long {
        return date?.getTime() ?: 0L
    }
}