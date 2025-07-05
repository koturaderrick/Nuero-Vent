package com.nuerovent

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import okhttp3.*
import org.json.JSONObject
import java.io.IOException

class Home : AppCompatActivity() {

    private lateinit var textTemperature: TextView
    private lateinit var textHumidity: TextView

    private val handler = Handler(Looper.getMainLooper())
    private val client = OkHttpClient()
    private val updateInterval: Long = 5000 // 5 seconds

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        // Initialize your TextViews
        textTemperature = findViewById(R.id.text_temperature)
        textHumidity = findViewById(R.id.text_humidity)

        // Navigation buttons
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
            startActivity(Intent(this, Control::class.java))
            finish()
        }

        findViewById<ImageView>(R.id.option_image).setOnClickListener {
            startActivity(Intent(this, Options::class.java))
        }

        // Start sensor updates
        startUpdatingSensorData()
    }

    private fun startUpdatingSensorData() {
        handler.post(object : Runnable {
            override fun run() {
                fetchSensorData()
                handler.postDelayed(this, updateInterval)
            }
        })
    }

    private fun fetchSensorData() {
        val request = Request.Builder()
            .url("http://192.168.4.1/data") // ESP32 AP mode IP
            .build()

        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                runOnUiThread {
                    textTemperature.text = "--°C"
                    textHumidity.text = "--%"
                }
            }

            override fun onResponse(call: Call, response: Response) {
                val responseData = response.body?.string()
                try {
                    val json = JSONObject(responseData ?: "{}")
                    val temp = json.optDouble("temperature", -1.0)
                    val hum = json.optDouble("humidity", -1.0)

                    runOnUiThread {
                        textTemperature.text = if (temp >= 0) "$temp°C" else "--°C"
                        textHumidity.text = if (hum >= 0) "$hum%" else "--%"
                    }
                } catch (e: Exception) {
                    runOnUiThread {
                        textTemperature.text = "--°C"
                        textHumidity.text = "--%"
                    }
                }
            }
        })
    }
}
