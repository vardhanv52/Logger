package com.android.my_logger.utils

import android.util.Log
import android.view.View
import com.android.my_logger.MyLogger.options
import com.android.my_logger.MyLogger.roomAPI
import com.android.my_logger.utils.Constants.TAG
import com.android.my_logger.room.APICalls
import com.android.my_logger.room.UserActions
import com.google.gson.Gson
import okhttp3.HttpUrl
import okhttp3.RequestBody
import org.json.JSONObject

internal object LogUtil {

    fun log(msg: String) {
        Log.d(TAG, msg)
    }

    fun log(data: Any?) {
        log(getString(data))
    }

    fun getString(data: Any?): String {
        return if (data == null)
            "Null"
        else
            Gson().toJson(data).toString()
    }

    fun logError(str: String?) {
        Log.e(TAG, "$str")
    }

    fun logAPICall(
        url: HttpUrl, headers: JSONObject, requestBody: RequestBody?,
        code: Int, responseBody: String?
    ) {
        if (!options.apiCalls.enabled)
            return
        val record =
            APICalls(
                url.url().toString(), headers.toString(), getString(requestBody), code, responseBody
            )
        if (options.apiCalls.dbLogging)
            roomAPI.insertAPI(record)
        if (options.apiCalls.terminalLogging)
            log(record.toString())
    }

    fun logUserAction(p0: View?) {
        if (p0 == null || !options.userActions.enabled)
            return
        val action = UserActions(ActivityCallbacks.currentActivity, "Clicked on ${Helper.getId(p0)}")
        if (options.userActions.dbLogging)
            roomAPI.insertAction(action)
        if (options.userActions.terminalLogging)
            log(action.toString())
    }

    fun logMessage(msg: String?) {
        if(msg == null || !options.customMessages.enabled)
            return
        val action = UserActions(ActivityCallbacks.currentActivity, msg)
        if (options.userActions.dbLogging)
            roomAPI.insertAction(action)
        if (options.userActions.terminalLogging)
            log(action.toString())
    }

    fun logMessage(data: Any?) {
        if(data == null || !options.customMessages.enabled)
            return
        val action = UserActions(ActivityCallbacks.currentActivity, getString(data))
        if (options.userActions.dbLogging)
            roomAPI.insertAction(action)
        if (options.userActions.terminalLogging)
            log(action.toString())
    }

}