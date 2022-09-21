package com.android.my_logger.room

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [APICalls::class, UserActions::class],
    version = 1, exportSchema = false,
)
internal abstract class RoomDB : RoomDatabase() {
    abstract fun roomAPI(): RoomAPI
}