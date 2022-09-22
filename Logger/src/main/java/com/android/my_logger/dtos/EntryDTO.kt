package com.android.my_logger.dtos

import com.android.my_logger.room.APICalls
import com.android.my_logger.room.UserActions
import com.android.my_logger.utils.DateUtil
import com.google.gson.Gson

internal class EntryDTO {
    var id: Long? = null
    var activity: String? = null
    var message: String? = null
    var url: String? = null
    var headers: String? = null
    var body: String? = null
    var responseCode: Int? = null
    var response: String? = null
    var date: String? = null
    var time: String? = null
    var tags: String? = null
    var device: String? = null
    var insertedAt = 0L

    constructor(api: APICalls) {
        id = api.id
        url = api.url
        headers = api.headers
        body = api.body
        responseCode = api.responseCode
        response = api.response
        insertedAt = api.insertedAt
        tags = api.tags
        date = DateUtil.getDate(insertedAt)
        time = DateUtil.getTime(insertedAt)
    }

    constructor(action: UserActions) {
        id = action.id
        activity = action.activity
        message = action.message
        tags = action.tags
        insertedAt = action.insertedAt
        date = DateUtil.getDate(insertedAt)
        time = DateUtil.getTime(insertedAt)
    }

    override fun toString(): String {
        return Gson().toJson(this)
    }

    fun getOriginalDTO(): Any {
        return if (url == null)
            UserActions(this)
        else
            APICalls(this)
    }
}