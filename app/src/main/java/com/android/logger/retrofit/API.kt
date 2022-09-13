package com.android.logger.retrofit

import com.android.logger.responses.GeneralResponse
import org.json.JSONObject
import retrofit2.Response
import retrofit2.http.GET

interface API {
    @GET("user/ping")
    suspend fun getUserServiceStatus(): Response<GeneralResponse>

    @GET("events/ping")
    suspend fun getEventsServiceStatus(): Response<GeneralResponse>
}