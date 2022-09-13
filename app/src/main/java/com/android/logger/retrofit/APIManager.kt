package com.android.logger.retrofit

import com.android.logger.responses.GeneralResponse
import com.android.logger.utils.Helper
import com.android.logger.utils.SampleLogger
import com.google.gson.Gson
import com.google.gson.JsonArray
import com.google.gson.reflect.TypeToken
import org.json.JSONObject
import java.util.*

object APIManager {

    suspend fun executeAPI1(): GeneralResponse {
        val resp = SampleLogger.webAPI.getUserServiceStatus()
        return if (resp.isSuccessful)
            resp.body() ?: GeneralResponse()
        else {
            Gson().fromJson(
                resp.errorBody()!!.string(),
                object : TypeToken<GeneralResponse>() {}.type
            )
        }
    }

    suspend fun executeAPI2(): GeneralResponse {
        val resp = SampleLogger.webAPI.getEventsServiceStatus()
        return if (resp.isSuccessful)
            resp.body() ?: GeneralResponse()
        else {
            Gson().fromJson(
                resp.errorBody()!!.string(),
                object : TypeToken<GeneralResponse>() {}.type
            )
        }
    }

}