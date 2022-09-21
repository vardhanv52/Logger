package com.android.my_logger.utils

import android.app.ActivityManager
import android.content.Context
import android.view.View
import androidx.core.content.ContextCompat


internal object Helper {

    fun getId(view: View?): String {
        if (view == null)
            return "null-view"
        if (view.id == View.NO_ID)
            return "no-id"
        return view.resources.getResourceName(view.id)
    }

}