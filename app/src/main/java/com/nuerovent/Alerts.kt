package com.nuerovent

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity

class Alerts : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_alerts)

        findViewById<ImageView>(R.id.home).setOnClickListener {
            startActivity(Intent(this, Home::class.java))
            finish()
        }

        findViewById<ImageView>(R.id.alerts_icon).setOnClickListener {
            // Already in Alerts, optionally refresh or do nothing
        }

        findViewById<ImageView>(R.id.stats_icon).setOnClickListener {
            startActivity(Intent(this, Stats::class.java))
            finish()
        }

        findViewById<ImageView>(R.id.option_image).setOnClickListener {
            startActivity(Intent(this, Options::class.java))
        }


        findViewById<ImageView>(R.id.home).setOnClickListener {
            startActivity(Intent(this, Home::class.java).apply {
                flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP
            })
        }


        findViewById<ImageView>(R.id.control_icon).setOnClickListener {
            startActivity(Intent(this, Control::class.java))
            finish()
        }
    }
}
