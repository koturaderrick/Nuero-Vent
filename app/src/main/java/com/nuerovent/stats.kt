package com.nuerovent

import android.os.Bundle
import android.widget.ImageView
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet

class Stats : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_stats)

        findViewById<ImageView>(R.id.home).setOnClickListener {
            startActivity(Intent(this, Home::class.java))
            finish()
        }

        findViewById<ImageView>(R.id.alerts_icon).setOnClickListener {
            startActivity(Intent(this, Alerts::class.java))
            finish()
        }

        findViewById<ImageView>(R.id.stats_icon).setOnClickListener {
            // current activity, no action needed
        }

        findViewById<ImageView>(R.id.option_image).setOnClickListener {
            startActivity(Intent(this, Options::class.java))
        }


        findViewById<ImageView>(R.id.control_icon).setOnClickListener {
            startActivity(Intent(this, Control::class.java))
            finish()
        }


    }
}
