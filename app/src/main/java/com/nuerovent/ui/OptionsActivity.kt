package com.nuerovent.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.nuerovent.databinding.ActivityOptionsBinding

class Options : AppCompatActivity() {
    private lateinit var binding: ActivityOptionsBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityOptionsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.back.setOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }

    }
}
