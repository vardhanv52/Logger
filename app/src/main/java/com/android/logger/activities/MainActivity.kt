package com.android.logger.activities

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.android.logger.R
import com.android.logger.databinding.ActivityMainBinding
import com.android.logger.retrofit.APIManager
import com.android.logger.utils.Helper
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
        setListeners()
    }

    private fun setListeners() {
        binding.button1.setOnClickListener {
            makeAPICall1()
        }
        binding.button2.setOnClickListener {
            makeAPICall2()
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