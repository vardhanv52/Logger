package com.android.my_logger.utils

import androidx.work.*
import com.android.my_logger.MyLogger
import com.android.my_logger.jobs.DeletingLogs
import com.android.my_logger.jobs.SyncingLogs
import java.util.concurrent.TimeUnit

internal object Scheduler {
    private const val JOB_SYNCING_LOGS = "JOB_SYNCING_LOGS"
    private const val JOB_DELETING_LOGS = "JOB_DELETING_LOGS"
    private const val SYNC_JOB_VERSION = 1
    private const val DELETE_JOB_VERSION = 1

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
        val constraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.CONNECTED)
            .build()
        val work =
            PeriodicWorkRequestBuilder<DeletingLogs>(
                1, TimeUnit.DAYS,
                2, TimeUnit.HOURS
            )
                .setConstraints(constraints)
                .build()
        WorkManager.getInstance(MyLogger.context)
            .enqueueUniquePeriodicWork(JOB_DELETING_LOGS, getWorkPolicy(JOB_DELETING_LOGS), work)
    }

    private fun getWorkPolicy(job: String): ExistingPeriodicWorkPolicy {
        return if (job == JOB_SYNCING_LOGS) {
            if (PrefUtil.getInt(PrefUtil.keySyncJobVersion) < SYNC_JOB_VERSION) {
                PrefUtil.saveData(PrefUtil.keySyncJobVersion, SYNC_JOB_VERSION)
                ExistingPeriodicWorkPolicy.REPLACE
            } else
                ExistingPeriodicWorkPolicy.KEEP
        } else {
            if (PrefUtil.getInt(PrefUtil.keyDeleteJobVersion) < DELETE_JOB_VERSION) {
                PrefUtil.saveData(PrefUtil.keyDeleteJobVersion, DELETE_JOB_VERSION)
                ExistingPeriodicWorkPolicy.REPLACE
            } else
                ExistingPeriodicWorkPolicy.KEEP
        }
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
            .enqueueUniquePeriodicWork(JOB_SYNCING_LOGS, getWorkPolicy(JOB_SYNCING_LOGS), work)
    }

    private fun clearSyncingJob() {
        WorkManager.getInstance(MyLogger.context)
            .cancelUniqueWork(JOB_SYNCING_LOGS)
    }

}