package com.android.my_logger.utils

import android.view.View
import com.android.my_logger.MyLogger


internal object Helper {

    fun getId(view: View?): String {
        if (view == null)
            return "null-view"
        if (view.id == View.NO_ID)
            return "no-id"
        val name = view.resources.getResourceName(view.id)
        val packageName = MyLogger.context.packageName
        return name.replace(packageName, "")
    }

    fun getDeviceDetails(): String {
        return "${android.os.Build.MANUFACTURER} | ${android.os.Build.MODEL} | ${android.os.Build.VERSION.SDK_INT} | ${getVersionDetails()}"
    }

    private fun getVersionDetails(): String {
        return try {
            val pInfo =
                MyLogger.context.packageManager.getPackageInfo(MyLogger.context.packageName, 0)
            pInfo.versionName
        } catch (ex: Exception) {
            "N/A"
        }
    }

}