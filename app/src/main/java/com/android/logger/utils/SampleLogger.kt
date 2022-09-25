package com.android.logger.utils

import android.app.Application
import android.content.Context
import com.android.logger.retrofit.API
import com.android.logger.retrofit.WebServiceAccess
import com.android.my_logger.MyLogger
import com.android.my_logger.utils.LogOptions
import java.util.*

class SampleLogger : Application() {

    override fun onCreate() {
        webAPI = WebServiceAccess().getAPI()
        appContext = this
        MyLogger.launch(this, LogOptions().apply {
            apiCalls.terminalLogging = true
            firebase.logsCollection = "library-logs"
        })
        MyLogger.setTags(arrayListOf("OrderId", "User Email"))
        super.onCreate()
    }

    companion object {
        lateinit var appContext: Context
        lateinit var webAPI: API
    }

}