package com.android.logger.retrofit

import com.android.logger.responses.GeneralResponse
import org.json.JSONObject
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.QueryMap

interface API {
    @GET("user/ping")
    suspend fun getUserServiceStatus(
        @Header("Sample-Header") token: String,
        @QueryMap map: HashMap<String, Any>
    ): Response<GeneralResponse>

    @GET("events/ping")
    suspend fun getEventsServiceStatus(
        @Header("Sample-Header") token: String,
    ): Response<GeneralResponse>
}