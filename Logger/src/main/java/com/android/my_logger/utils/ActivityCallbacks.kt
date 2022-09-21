package com.android.my_logger.utils

import android.app.Activity
import android.app.Application
import android.os.Bundle

internal class ActivityCallbacks : Application.ActivityLifecycleCallbacks {

    override fun onActivityCreated(p0: Activity, p1: Bundle?) {
        currentActivity = p0.localClassName
    }

    override fun onActivityStarted(p0: Activity) {

    }

    override fun onActivityResumed(p0: Activity) {
        currentActivity = p0.localClassName
    }

    override fun onActivityPaused(p0: Activity) {

    }

    override fun onActivityStopped(p0: Activity) {

    }

    override fun onActivitySaveInstanceState(p0: Activity, p1: Bundle) {

    }

    override fun onActivityDestroyed(p0: Activity) {

    }

    companion object {
        var currentActivity: String? = null
        private var inst: ActivityCallbacks? = null
        fun getInstance(): ActivityCallbacks {
            if (inst == null)
                inst = ActivityCallbacks()
            return inst!!
        }
    }
}