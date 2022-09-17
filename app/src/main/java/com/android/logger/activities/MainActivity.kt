package com.android.logger.activities

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.app.ActivityCompat
import com.android.logger.R
import com.android.logger.databinding.ActivityMainBinding
import com.android.logger.retrofit.APIManager
import com.android.logger.utils.Helper
import com.android.my_logger.MyLogger
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val context: Context = this

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        MyLogger.log("MainActivity OnCreate")
        setListeners()
    }

    private fun setListeners() {
        binding.button1.setOnClickListener {
            makeAPICall1()
        }
        binding.button2.setOnClickListener {
            makeAPICall2()
        }
        binding.navigateBtn.setOnClickListener {
            startActivity(Intent(context, SampleActivity::class.java))
        }
        binding.clearDb.setOnClickListener {
            MyLogger.clearDatabase()
            Helper.showToast("Logs cleared")
        }
        binding.syncBtn.setOnClickListener {
            Helper.showToast("WIP")
            return@setOnClickListener
            Helper.showToast("Synced!")
        }
        binding.exportDb.setOnClickListener {
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.Q && ActivityCompat.checkSelfPermission(
                    context,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                ActivityCompat.requestPermissions(
                    this,
                    arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE),
                    200
                )
                return@setOnClickListener
            }
            Helper.showProgressDialog(context)
            val status = MyLogger.exportDatabase()
            Helper.showToast(
                if (status)
                    "Exported successfully"
                else
                    "Failed to Export!"
            )
            Helper.dismissProgressDialog()
        }
    }

    private fun makeAPICall1() {
        CoroutineScope(Dispatchers.Main).launch {
            Helper.showProgressDialog(context)
            val response = APIManager.executeAPI1()
            Helper.dismissProgressDialog()
            binding.response.text = response.message
        }
    }

    private fun makeAPICall2() {
        CoroutineScope(Dispatchers.Main).launch {
            Helper.showProgressDialog(context)
            val response = APIManager.executeAPI2()
            Helper.dismissProgressDialog()
            binding.response.text = response.message
        }
    }

}