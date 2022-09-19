package com.android.my_logger

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import androidx.room.Room
import com.android.my_logger.responses.LibResponse
import com.android.my_logger.retrofit.APIInterceptor
import com.android.my_logger.room.RoomAPI
import com.android.my_logger.room.RoomDB
import com.android.my_logger.utils.*
import com.android.my_logger.utils.ActivityCallbacks
import com.android.my_logger.utils.Constants
import com.android.my_logger.utils.LogUtil

@SuppressLint("StaticFieldLeak")
object MyLogger {
    lateinit var context: Context
    internal lateinit var roomAPI: RoomAPI
    lateinit var options: LogOptions
    private lateinit var application: Application

    fun launch(application: Application) {
        context = application.applicationContext
        options = LogOptions()
        application.registerActivityLifecycleCallbacks(ActivityCallbacks.getInstance())
        initRoom()
    }

    fun launch(application: Application, options: LogOptions) {
        context = application.applicationContext
        this.options = options
        application.registerActivityLifecycleCallbacks(ActivityCallbacks.getInstance())
        initRoom()
    }

    private fun initRoom() {
        roomAPI = Room.databaseBuilder(
            context, RoomDB::class.java, Constants.ROOM_DB_NAME
        )
            .allowMainThreadQueries()
            .addMigrations()
            .build().roomAPI()
    }

    fun getAPIInterceptor(): APIInterceptor {
        return APIInterceptor.getInstance()
    }

    fun log(msg: String?) {
        LogUtil.logMessage(msg)
    }

    fun log(data: Any?) {
        LogUtil.logMessage(data)
    }

    fun exportDatabase(): Boolean {
        val apis = roomAPI.getAPICalls()
        val actions = roomAPI.getActions()
        return ExportUtil(apis, actions).exportData()
    }

    fun clearDatabase() {
        roomAPI.deleteAPICalls()
        roomAPI.deleteActions()
    }

    fun syncDatabase(): LibResponse {
        val apis = roomAPI.getUnSyncedAPICalls()
        val actions = roomAPI.getUnSyncedActions()
        if (apis.isEmpty() && actions.isEmpty()) {
            return LibResponse(true, Codes.NOTHING_TO_SYNC.toString())
        }
        return SyncUtil(apis, actions).syncData()
    }

}