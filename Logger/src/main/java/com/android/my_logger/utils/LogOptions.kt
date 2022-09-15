package com.android.my_logger.utils

class LogOptions {

    var apiCalls = APICalls()

    var userActions = UserActions()

    var customMessages = CustomMessages()

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
}