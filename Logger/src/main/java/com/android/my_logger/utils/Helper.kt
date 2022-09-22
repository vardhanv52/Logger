package com.android.my_logger.utils

import android.view.View


internal object Helper {

    fun getId(view: View?): String {
        if (view == null)
            return "null-view"
        if (view.id == View.NO_ID)
            return "no-id"
        return view.resources.getResourceName(view.id)
    }

    fun getDeviceDetails(): String {
        return "${android.os.Build.MANUFACTURER} | ${android.os.Build.MODEL} | ${android.os.Build.VERSION.SDK_INT}"
    }

}