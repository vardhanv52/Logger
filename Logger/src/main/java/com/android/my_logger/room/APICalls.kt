package com.android.my_logger.room

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity(tableName = "api_calls")
internal class APICalls {
    @PrimaryKey(autoGenerate = true)
    var id = 0L
    var url: String? = null
    var headers: String? = null
    var body: String? = null
    var responseCode = 0
    var response: String? = null
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

    override fun toString(): String {
        return "=====API call=========\nUrl => $url,\nHeaders => $headers,\nRequest Body => $body," +
                "\nResponse code => $responseCode,\nResponse => $response\nTime => ${Date(insertedAt)}"
    }

}