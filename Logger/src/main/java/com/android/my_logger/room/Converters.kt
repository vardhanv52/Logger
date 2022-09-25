package com.android.my_logger.room

import androidx.room.TypeConverter

class Converters {
    @TypeConverter
    fun fromStringList(list: List<String>): String {
        return list.joinToString(",")
    }

    @TypeConverter
    fun toStringList(list: String): List<String> {
        return list.split(",")
    }
}