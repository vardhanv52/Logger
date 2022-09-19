package com.android.my_logger.room

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.android.my_logger.dtos.EntryDTO
import com.google.firebase.firestore.Exclude
import java.util.*

@Entity(tableName = "api_calls")
internal class APICalls {
    @get:Exclude
    @PrimaryKey(autoGenerate = true)
    var id = 0L
    var url: String? = null
    var headers: String? = null
    var body: String? = null
    var responseCode = 0
    var response: String? = null

    @get:Exclude
    var isSynced = false
    var insertedAt = 0L

    constructor() {
        insertedAt = Calendar.getInstance().timeInMillis
    }

    constructor(url: String?, headers: String?, body: String?, code: Int, response: String?) {
        this.url = url
        this.headers = headers
        this.body = body
        this.response = response
        responseCode = code
        insertedAt = Calendar.getInstance().timeInMillis
    }

    constructor(data: EntryDTO) {
        id = data.id ?: 0L
        url = data.url
        headers = data.headers
        body = data.body
        responseCode = data.responseCode ?: 0
        response = data.response
        insertedAt = data.insertedAt
    }

    override fun toString(): String {
        return "=====API call=========\nUrl => $url,\nHeaders => $headers,\nRequest Body => $body," +
                "\nResponse code => $responseCode,\nResponse => $response\nTime => ${Date(insertedAt)}"
    }

}