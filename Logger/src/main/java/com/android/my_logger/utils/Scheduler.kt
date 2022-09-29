package com.android.my_logger.utils

import androidx.work.*
import com.android.my_logger.MyLogger
import com.android.my_logger.jobs.DeletingLogs
import com.android.my_logger.jobs.SyncingLogs
import java.util.concurrent.TimeUnit

internal object Scheduler {
    private const val JOB_SYNCING_LOGS = "JOB_SYNCING_LOGS"
    private const val JOB_DELETING_LOGS = "JOB_DELETING_LOGS"

    fun schedule() {
        if (MyLogger.options.autoSyncing)
            scheduleSyncingJob()
        else
            clearSyncingJob()
        if (MyLogger.options.autoDeletion)
            scheduleDeletionJob()
        else
            clearDeletionJob()
    }

    private fun scheduleDeletionJob() {
        val work =
            PeriodicWorkRequestBuilder<DeletingLogs>(
                MyLogger.options.getLogsHistory().toLong(), TimeUnit.DAYS,
                2, TimeUnit.HOURS
            ).build()
        WorkManager.getInstance(MyLogger.context)
            .enqueueUniquePeriodicWork(JOB_DELETING_LOGS, ExistingPeriodicWorkPolicy.KEEP, work)
    }

    private fun clearDeletionJob() {
        WorkManager.getInstance(MyLogger.context)
            .cancelUniqueWork(JOB_DELETING_LOGS)
    }

    private fun scheduleSyncingJob() {
        val constraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.CONNECTED)
            .build()
        val work =
            PeriodicWorkRequestBuilder<SyncingLogs>(
                MyLogger.options.getSyncingInterval().toLong(), TimeUnit.MINUTES,
                5, TimeUnit.MINUTES
            )
                .setConstraints(constraints)
                .build()
        WorkManager.getInstance(MyLogger.context)
            .enqueueUniquePeriodicWork(JOB_SYNCING_LOGS, ExistingPeriodicWorkPolicy.KEEP, work)
    }

    private fun clearSyncingJob() {
        WorkManager.getInstance(MyLogger.context)
            .cancelUniqueWork(JOB_SYNCING_LOGS)
    }

}