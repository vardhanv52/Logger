package com.android.logger.utils

import android.app.Application
import android.content.Context
import com.android.logger.retrofit.API
import com.android.logger.retrofit.WebServiceAccess

class SampleLogger : Application() {

    override fun onCreate() {
        webAPI = WebServiceAccess().getAPI()
        appContext = this
        super.onCreate()
    }

    companion object {
        lateinit var appContext: Context
        lateinit var webAPI: API
    }

}