package com.android.my_logger.jobs

import android.annotation.SuppressLint
import android.content.Context
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.android.my_logger.MyLogger

class SyncingLogs(context: Context, workerParams: WorkerParameters) :
    Worker(context, workerParams) {

    @SuppressLint("RestrictedApi")
    override fun doWork(): Result {
        MyLogger.log("Sync started!!")
        MyLogger.syncDatabase()
        return Result.Success()
    }

}