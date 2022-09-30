package com.android.my_logger.jobs

import android.content.Context
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.android.my_logger.MyLogger
import com.android.my_logger.utils.Helper
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.tasks.await
import java.util.*
import java.util.concurrent.TimeUnit

class DeletingLogs(context: Context, workerParams: WorkerParameters) :
    Worker(context, workerParams) {

    override fun doWork(): Result {
        MyLogger.log("Deletion started!!")
        val threshold = getThresholdInMillis(MyLogger.options.getLogsHistory().toLong())
        MyLogger.roomAPI.deleteOldAPICalls(threshold)
        MyLogger.roomAPI.deleteOldActions(threshold)
        if (Helper.isFirebaseEnabled)
            clearOldRecordsFromFirestore()
        return Result.success()
    }

    // All the logs that are older than 5 days will be cleared
    private fun clearOldRecordsFromFirestore() {
        runBlocking {
            val threshold = getThresholdInMillis(MyLogger.options.getLogsHistory().toLong())
            val ref = Helper.getLogsCollectionPath()
            val records = ref.whereLessThanOrEqualTo("insertedAt", threshold)
                .get().await().documents
            val batch = FirebaseFirestore.getInstance().batch()
            records.forEach {
                batch.delete(it.reference)
            }
            batch.commit().await()
        }
    }

    private fun getThresholdInMillis(days: Long): Long {
        return Calendar.getInstance().timeInMillis - TimeUnit.DAYS.toMillis(days)
    }

}