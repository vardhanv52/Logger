package com.android.my_logger.utils

class LogOptions {

    val apiCalls = APICalls()

    val userActions = UserActions()

    val customMessages = CustomMessages()

    val firebase = Firebase()

    var tags = ""

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
        var collection = Constants.COLLECTION_LOGS_PARENT
    }
}