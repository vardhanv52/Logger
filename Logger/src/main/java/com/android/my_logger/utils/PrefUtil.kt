package com.android.my_logger.utils

import android.content.Context
import android.content.SharedPreferences
import com.android.my_logger.MyLogger

object PrefUtil {
    private const val fileName = "app_logger_prefs"

    const val keyTags = "tags"

    private fun getSharedPreferences(): SharedPreferences? {
        return MyLogger.context.getSharedPreferences(fileName, Context.MODE_PRIVATE)
    }

    fun saveData(key: String, value: Any?) {
        val editor = getSharedPreferences()?.edit()
        when (value) {
            is String -> editor?.putString(key, value)
            is Boolean -> editor?.putBoolean(key, value)
            is Float -> editor?.putFloat(key, value)
            is Int -> editor?.putInt(key, value)
            is Long -> editor?.putLong(key, value)
        }
        editor?.apply()
    }

    fun getString(key: String): String? {
        return getSharedPreferences()?.getString(key, null)
    }
}