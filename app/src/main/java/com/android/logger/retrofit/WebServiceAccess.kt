package com.android.logger.retrofit

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class WebServiceAccess {
    private val baseUrl = "http://65.0.90.255:2000/api/"
    private var httpClient: OkHttpClient.Builder? = null

    init {
        httpClient = OkHttpClient.Builder()
        val logging = HttpLoggingInterceptor()
        logging.level = HttpLoggingInterceptor.Level.BODY
        httpClient?.addInterceptor(logging)
        httpClient?.connectTimeout(60, TimeUnit.SECONDS)
    }

    fun getAPI(): API {
        val retrofit: Retrofit = Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .client(httpClient!!.build())
            .build()
        return retrofit.create(API::class.java)
    }
}