package com.android.my_logger

import android.util.Log
import com.android.my_logger.Constants.tag

object LogUtil {

    fun log(msg: String) {
        Log.d(tag, msg)
    }

}