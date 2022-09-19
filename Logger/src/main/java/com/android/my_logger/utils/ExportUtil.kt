package com.android.my_logger.utils

import android.content.ContentValues
import android.os.Build
import android.os.Environment
import android.provider.MediaStore
import com.android.my_logger.MyLogger
import com.android.my_logger.dtos.EntryDTO
import com.android.my_logger.room.APICalls
import com.android.my_logger.room.UserActions
import java.io.File
import java.io.FileWriter
import java.util.*

internal class ExportUtil(val apis: List<APICalls>, val actions: List<UserActions>) {
    private var entries = ArrayList<EntryDTO>()
    private val headers = ArrayList<String?>().apply {
        addAll(
            arrayListOf(
                "Screen", "Message", "Url", "Headers", "Body", "Response code",
                "Response", "Date", "Time"
            )
        )
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

    private fun isSDK29AndUp(): Boolean {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q
    }

    fun exportData(): Boolean {
        try {
            val data = ArrayList<ArrayList<String?>>()
            formatData(data)
            if (isSDK29AndUp()) {
                val values = ContentValues().apply {
                    put(MediaStore.MediaColumns.DISPLAY_NAME, getFileName())
                    put(MediaStore.MediaColumns.MIME_TYPE, "text/csv")
                    put(MediaStore.MediaColumns.RELATIVE_PATH, Environment.DIRECTORY_DOCUMENTS)
                }
                val uri = MyLogger.context.contentResolver.insert(
                    MediaStore.Files.getContentUri(MediaStore.VOLUME_EXTERNAL),
                    values
                )
                val outputStream =
                    MyLogger.context.contentResolver.openOutputStream(uri ?: return false)
                val writer = CSVWriter(outputStream ?: return false)
                writer.write(data)
                writer.close()
            } else {
                val folder = File(
                    Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS),
                    ""
                )
                if (!folder.exists())
                    folder.mkdirs()
                val file = File(folder, getFileName())
                file.createNewFile()
                val writer = CSVWriter(FileWriter(file))
                writer.write(data)
                writer.close()
            }
            return true
        } catch (ex: Exception) {
            ex.printStackTrace()
            return false
        }
    }

    private fun getFileName(): String {
        return "logs_${Date()}.csv"
    }

    private fun formatData(
        data: ArrayList<ArrayList<String?>>,
    ) {
        data.clear()
        data.add(headers)
        entries.forEach {
            data.add(
                arrayListOf(
                    it.activity, it.message, it.url, it.headers, it.body,
                    it.responseCode.toString(), it.response,
                    it.date, it.time
                )
            )
        }
    }

}