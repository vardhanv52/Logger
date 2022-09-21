package com.android.my_logger.utils

import java.text.SimpleDateFormat
import java.util.*

internal object DateUtil {

    private val DD_MM_YYY = SimpleDateFormat("dd-MM-yyyy", Locale.ROOT)
    private val HH_MM_A = SimpleDateFormat("hh:mm a", Locale.ROOT)

    init {
        DD_MM_YYY.timeZone = TimeZone.getTimeZone("UTC")
        HH_MM_A.timeZone = TimeZone.getTimeZone("UTC")
    }

    fun getDate(time: Long): String {
        val date = Date(time)
        return DD_MM_YYY.format(date)
    }

    fun getTime(time: Long): String {
        val date = Date(time)
        return HH_MM_A.format(date)
    }

}