package com.android.my_logger.dtos

import com.android.my_logger.room.APICalls
import com.android.my_logger.room.UserActions
import com.google.gson.Gson

internal class EntryDTO {
    var activity: String? = null
    var message: String? = null
    var url: String? = null
    var headers: String? = null
    var body: String? = null
    var responseCode = 0
    var response: String? = null
    var insertedAt = 0L

    constructor(api: APICalls) {
        url = api.url
        headers = api.headers
        body = api.body
        responseCode = api.responseCode
        response = api.response
        insertedAt = api.insertedAt
    }

    constructor(action: UserActions) {
        activity = action.activity
        message = action.message
        insertedAt = action.insertedAt
    }

    override fun toString(): String {
        return Gson().toJson(this)
    }
}