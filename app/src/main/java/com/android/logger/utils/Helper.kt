package com.android.logger.utils

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.util.Log
import android.view.WindowManager
import android.widget.Toast
import com.android.logger.R
import com.android.logger.utils.SampleLogger.Companion.appContext
import com.google.gson.Gson

object Helper {
    private var loader: Dialog? = null
    private var toast: Toast? = null

    fun showToast(msg: String?) {
        toast?.cancel()
        toast = Toast.makeText(appContext, msg, Toast.LENGTH_LONG)
        toast?.show()
    }

    fun showProgressDialog(context: Context) {
        dismissProgressDialog()
        try {
            loader = Dialog(context)
            loader?.setContentView(R.layout.dialog_loader)
            loader?.setCancelable(false)
            loader?.window?.setLayout(
                WindowManager.LayoutParams.WRAP_CONTENT,
                WindowManager.LayoutParams.WRAP_CONTENT
            )
            loader?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            loader?.show()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun dismissProgressDialog() {
        try {
            loader?.dismiss()
        } catch (err: Exception) {
            err.printStackTrace()
        }
    }
}