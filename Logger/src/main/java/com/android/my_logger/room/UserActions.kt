package com.android.my_logger.room

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity(tableName = "user_actions")
internal class UserActions {
    @PrimaryKey(autoGenerate = true)
    var id = 0L
    var activity: String? = null
    var message: String? = null
    var insertedAt = 0L

    constructor() {
        insertedAt = Calendar.getInstance().timeInMillis
    }

    constructor(activity: String?, msg: String?) {
        this.activity = activity
        this.message = msg
        insertedAt = Calendar.getInstance().timeInMillis
    }

    override fun toString(): String {
        return "=====User Action=========\nActivity => $activity,\nMessage => $message,\nTime => ${Date(insertedAt)}"
    }

}