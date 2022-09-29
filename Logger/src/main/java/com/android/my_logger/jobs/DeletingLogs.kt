package com.android.my_logger.jobs

import android.content.Context
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.android.my_logger.MyLogger
import java.util.*

class DeletingLogs(context: Context, workerParams: WorkerParameters) :
    Worker(context, workerParams) {

    override fun doWork(): Result {
        MyLogger.log("Deletion started!!")
        val threshold = getThresholdInMillis()
        MyLogger.roomAPI.deleteOldAPICalls(threshold)
        MyLogger.roomAPI.deleteOldActions(threshold)
        return Result.success()
    }

    private fun getThresholdInMillis(): Long {
        return Calendar.getInstance().timeInMillis - 5 * 60 * 1000L
    }

}