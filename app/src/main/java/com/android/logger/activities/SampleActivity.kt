package com.android.logger.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.android.logger.R
import com.android.logger.databinding.ActivitySampleBinding
import com.android.logger.utils.Helper
import com.android.my_logger.listeners.MyOnClickListener

class SampleActivity : AppCompatActivity() {
    lateinit var binding: ActivitySampleBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySampleBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.clickMeBtn.setOnClickListener(listener)
    }

    val listener = object : MyOnClickListener() {
        override fun onUserClick(p0: View?) {
            Helper.showToast("Hello!")
        }
    }
}