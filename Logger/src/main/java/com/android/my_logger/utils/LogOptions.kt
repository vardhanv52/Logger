package com.android.my_logger.utils

class LogOptions {

    var autoSyncing = true

    // Min value has to be 15 Mins. The logs will be synced with
    // firestore periodically based on following interval.
    private var syncingInterval = 15 // In Mins

    var autoDeletion = true

    // Min value is 1 day. The following number specifies how many days of logs do we have to maintain in the local db at any time.
    private var logsHistory = 7 // In days


    fun setSyncingInterval(interval: Int) {
        syncingInterval = if (interval < 15)
            15
        else
            interval
    }

    fun setLogsHistory(days: Int) {
        logsHistory = if (days < 1)
            1
        else
            days
    }

    fun getLogsHistory(): Int {
        return logsHistory
    }

    fun getSyncingInterval(): Int {
        return syncingInterval
    }

    val apiCalls = APICalls()

    val userActions = UserActions()

    val messages = CustomMessages()

    val firebase = Firebase()

    class CustomMessages {
        // On/Off the logging
        var enabled = true

        // On/Off the logging in Logcat
        var terminalLogging = false

        // On/Off the logging to db
        var dbLogging = true
    }

    class UserActions {
        // On/Off the logging
        var enabled = true

        // On/Off the logging in Logcat
        var terminalLogging = false

        // On/Off the logging to db
        var dbLogging = true
    }

    class APICalls {
        // On/Off the logging
        var enabled = true

        // On/Off the logging in Logcat
        var terminalLogging = false

        // On/Off the logging to db
        var dbLogging = true
    }

    class Firebase {
        var logsCollection = Constants.COLLECTION_LOGS
    }
}