package com.android.my_logger.room

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.android.my_logger.MyLogger
import com.android.my_logger.dtos.EntryDTO
import com.google.firebase.firestore.Exclude
import java.util.*

@Entity(tableName = "user_actions")
internal class UserActions {
    @get:Exclude
    @PrimaryKey(autoGenerate = true)
    var id = 0L
    var activity: String? = null
    var message: String? = null

    @get:Exclude
    var isSynced = false
    var tags: String? = null
    var insertedAt = 0L

    constructor() {
        insertedAt = Calendar.getInstance().timeInMillis
    }

    constructor(activity: String?, msg: String?) {
        this.activity = activity
        this.message = msg
        this.tags = MyLogger.options.tags
        insertedAt = Calendar.getInstance().timeInMillis
    }

    constructor(data: EntryDTO) {
        id = data.id ?: 0L
        activity = data.activity
        message = data.message
        insertedAt = data.insertedAt
        tags = data.tags
    }

    override fun toString(): String {
        return "=====User Action=========\nActivity => $activity,\nMessage => $message,\ntags => $tags" +
                "\nTime => ${Date(insertedAt)}"
    }

}