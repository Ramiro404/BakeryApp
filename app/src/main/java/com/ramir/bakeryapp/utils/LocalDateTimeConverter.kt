package com.ramir.bakeryapp.utils

import androidx.room.TypeConverter
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneOffset

class LocalDateTimeConverter {

    @TypeConverter
    fun toTimestamp(localDateTime: LocalDateTime?): Long? {
        // Converts LocalDateTime to milliseconds since epoch
        return localDateTime?.atZone(ZoneOffset.UTC)?.toInstant()?.toEpochMilli()
    }

    @TypeConverter
    fun toLocalDateTime(timestamp: Long?): LocalDateTime? {
        // Converts milliseconds since epoch back to LocalDateTime
        return timestamp?.let {
            Instant.ofEpochMilli(it).atZone(ZoneOffset.UTC).toLocalDateTime()
        }
    }
}