package com.nuerovent.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.nuerovent.R
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

        setupOptions()
    }

    private fun setupOptions() {
        val options = listOf(
            Pair("Audit checklist Form", R.drawable.file),
            Pair("Log Out", R.drawable.log_out),
            Pair("Request Maintenance", R.drawable.tools),
            Pair("Reboot System", R.drawable.reboot)
        )

        for ((index, option) in options.withIndex()) {
            val (text, iconRes) = option
            val itemView = LayoutInflater.from(this).inflate(R.layout.option_item, binding.optionsContainer, false) as LinearLayout

            itemView.findViewById<ImageView>(R.id.optionIcon).setImageResource(iconRes)
            itemView.findViewById<TextView>(R.id.optionText).text = text
            binding.optionsContainer.addView(itemView)

            itemView.setOnClickListener {
                when (index) {
                    0 -> { /* Handle Audit checklist Form */ }
                    1 -> { /* Handle Log Out */ }
                    2 -> { /* Handle Maintenance */ }
                    3 -> { /* Handle Reboot */ }
                }
            }
        }
    }

}
