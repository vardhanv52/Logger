package com.android.my_logger.retrofit

import com.android.my_logger.utils.LogUtil
import okhttp3.Headers
import okhttp3.Interceptor
import okhttp3.Response
import okhttp3.ResponseBody
import org.json.JSONObject

class APIInterceptor : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val url = request.url()
        val headers = getJson(request.headers())
        val requestBody = request.body()
        val response = chain.proceed(request)
        val responseBody = response.body()?.string()
        LogUtil.logAPICall(url, headers, requestBody, response.code(), responseBody)
        val body = ResponseBody.create(response.body()?.contentType(), responseBody ?: "")
        return response.newBuilder().body(body).build()
    }

    private fun getJson(pairs: Headers): JSONObject {
        val json = JSONObject()
        val keys = pairs.names()
        keys.forEach {
            json.put(it, pairs.values(it)[0])
        }
        return json
    }

    companion object {
        fun getInstance(): APIInterceptor {
            return APIInterceptor()
        }
    }

}