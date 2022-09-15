package com.android.my_logger.listeners

import android.view.View
import com.android.my_logger.utils.LogUtil

abstract class MyOnClickListener : View.OnClickListener {

    final override fun onClick(p0: View?) {
        LogUtil.logUserAction(p0)
        onUserClick(p0)
    }

    abstract fun onUserClick(p0: View?)

}