package com.android.my_logger.utils

import com.android.my_logger.MyLogger.options
import com.android.my_logger.MyLogger.roomAPI
import com.android.my_logger.dtos.EntryDTO
import com.android.my_logger.dtos.LibResponseDTO
import com.android.my_logger.room.APICalls
import com.android.my_logger.room.UserActions
import com.google.firebase.FirebaseApp
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.ArrayList

internal class SyncUtil(val apis: List<APICalls>, val actions: List<UserActions>) {
    private var entries = ArrayList<EntryDTO>()
    private lateinit var firestore: FirebaseFirestore

    private val isFirebaseEnabled: Boolean
        get() {
            return try {
                FirebaseApp.getInstance() != null
            } catch (e: IllegalStateException) {
                false
            }
        }

    init {
        apis.forEach {
            entries.add(EntryDTO(it))
        }
        actions.forEach {
            entries.add(EntryDTO(it))
        }
        val temp = entries.sortedWith(compareBy { it.insertedAt }).toList()
        entries.clear()
        entries.addAll(temp)
    }

    fun syncData(): LibResponseDTO {
        if (!isFirebaseEnabled) {
            LogUtil.logError("Firebase is not integrated!")
            return LibResponseDTO(false, Codes.DST_NOT_CONFIGURED.toString())
        }
        try {
            firestore = FirebaseFirestore.getInstance()
            val rounds = entries.size / Constants.MAX_BATCH_COMMIT_SIZE
            for (i in 0..rounds) {
                CoroutineScope(Dispatchers.Main).launch {
                    val start = i * Constants.MAX_BATCH_COMMIT_SIZE
                    val end = if ((i + 1) * Constants.MAX_BATCH_COMMIT_SIZE < entries.size)
                        (i + 1) * Constants.MAX_BATCH_COMMIT_SIZE
                    else
                        entries.size
                    val records = entries.subList(start, end)
                    syncRecords(records)
                }
            }
        } catch (ex: Exception) {
            ex.printStackTrace()
            return LibResponseDTO(false, Codes.DATA_SYNC_FAIL.toString())
        }
        return LibResponseDTO(true, Codes.DATA_SYNC_DONE.toString())
    }

    private suspend fun syncRecords(logs: List<EntryDTO>) {
        val batch = firestore.batch()
        logs.forEach {
            val ref = firestore.collection(options.firebase.logsCollection)
                .document(it.date ?: "N/A")
                .collection(Constants.COLLECTION_LOGS)
            batch.set(ref.document(), it.getOriginalDTO())
        }
        batch.commit().addOnCompleteListener { it ->
            if (it.isSuccessful) {
                // Mark Actions as synced
                val ids = ArrayList<Long>()
                logs.filter { it.url == null }.forEach { log ->
                    ids.add(log.id ?: 0L)
                }
                roomAPI.markActionsAsSynced(ids)

                // Mark API calls as synced
                ids.clear()
                logs.filter { it.url != null }.forEach { log ->
                    ids.add(log.id ?: 0L)
                }
                roomAPI.markAPIsAsSynced(ids)
            }
        }
    }
}