package com.android.logger.responses

import com.android.logger.R
import com.android.logger.utils.SampleLogger.Companion.appContext

class GeneralResponse {
    var status: Boolean = false
    var message: String = appContext.getString(R.string.went_wrong)
}