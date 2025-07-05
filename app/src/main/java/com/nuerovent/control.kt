package com.nuerovent

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity

class Control : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_control)

        // Bottom Nav
        findViewById<ImageView>(R.id.home).setOnClickListener {
            startActivity(Intent(this, Home::class.java).apply {
                flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP
            })
        }

        findViewById<ImageView>(R.id.alerts_icon).setOnClickListener {
            startActivity(Intent(this, Alerts::class.java))
            finish()
        }

        findViewById<ImageView>(R.id.stats_icon).setOnClickListener {
            startActivity(Intent(this, Stats::class.java))
            finish()
        }

        findViewById<ImageView>(R.id.control_icon).setOnClickListener {
            // Stay on this activity
        }

        findViewById<ImageView>(R.id.option_image).setOnClickListener {
            startActivity(Intent(this, Options::class.java))
        }

        // Drop-down logic
        setupDropdown(R.id.temp_dropdown_container, R.id.temp_arrow)
        setupDropdown(R.id.humidity_dropdown_container, R.id.humidity_arrow)
        setupDropdown(R.id.pressure_dropdown_container, R.id.pressure_arrow)
        setupDropdown(R.id.air_dropdown_container, R.id.air_arrow)
    }

    private fun setupDropdown(containerId: Int, arrowId: Int) {
        val container = findViewById<View>(containerId)
        val arrow = findViewById<ImageView>(arrowId)

        arrow.setOnClickListener {
            if (container.visibility == View.GONE) {
                container.visibility = View.VISIBLE
            } else {
                container.visibility = View.GONE
            }
        }
    }
}
