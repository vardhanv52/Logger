package com.android.my_logger

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import android.os.Environment
import androidx.room.Room
import com.android.my_logger.retrofit.APIInterceptor
import com.android.my_logger.room.RoomAPI
import com.android.my_logger.room.RoomDB
import com.android.my_logger.utils.*
import com.android.my_logger.utils.ActivityCallbacks
import com.android.my_logger.utils.Constants
import com.android.my_logger.utils.LogUtil
import java.io.File
import java.io.FileWriter
import java.util.*
import kotlin.collections.ArrayList

@SuppressLint("StaticFieldLeak")
object MyLogger {
    private lateinit var context: Context
    internal lateinit var roomAPI: RoomAPI
    internal lateinit var options: LogOptions
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
        val folder = File(Environment.getExternalStorageDirectory(), "")
        if(!folder.exists())
            folder.mkdirs()
        try {
            var data = ArrayList<ArrayList<String?>>()
            val apis = roomAPI.getAPICalls()
            var file = File(folder, "apis_${Date()}.csv")
            file.createNewFile()
            var writer = CSVWriter(FileWriter(file))
            apis.forEach {
                data.add(arrayListOf(it.url, it.headers, it.body, it.responseCode.toString(), it.response, Date(it.insertedAt).toString()))
            }
            writer.write(data)
            writer.close()

            data.clear()
            val actions = roomAPI.getActions()
            file = File(folder, "actions_${Date()}.csv")
            file.createNewFile()
            writer = CSVWriter(FileWriter(file))
            actions.forEach {
                data.add(arrayListOf(it.activity, it.message, Date(it.insertedAt).toString()))
            }
            writer.write(data)
            writer.close()
        } catch (ex: Exception) {
            ex.printStackTrace()
            return false
        }
        return true
    }

    fun clearDatabase() {
        roomAPI.deleteAPICalls()
        roomAPI.deleteActions()
    }

}