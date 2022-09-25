package com.android.my_logger.room

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(
    entities = [APICalls::class, UserActions::class],
    version = 1, exportSchema = false,
)
@TypeConverters(Converters::class)
internal abstract class RoomDB : RoomDatabase() {
    abstract fun roomAPI(): RoomAPI
}