package com.nuerovent

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet

class Stats : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_stats)

        // Temperature
        val tempChart = findViewById<LineChart>(R.id.lineChartTemp)
        val tempEntries = listOf(
            Entry(0f, 22f),
            Entry(1f, 24f),
            Entry(2f, 21f),
            Entry(3f, 23f),
            Entry(4f, 25f)
        )
        setupChart(tempChart, tempEntries, "Temperature (°C)")

        // Humidity
        val humidityChart = findViewById<LineChart>(R.id.lineChartHumidity)
        val humidityEntries = listOf(
            Entry(0f, 60f),
            Entry(1f, 58f),
            Entry(2f, 62f),
            Entry(3f, 65f),
            Entry(4f, 63f)
        )
        setupChart(humidityChart, humidityEntries, "Humidity (%)")

        // Pressure
        val pressureChart = findViewById<LineChart>(R.id.lineChartPressure)
        val pressureEntries = listOf(
            Entry(0f, 1012f),
            Entry(1f, 1013f),
            Entry(2f, 1011f),
            Entry(3f, 1014f),
            Entry(4f, 1010f)
        )
        setupChart(pressureChart, pressureEntries, "Pressure (hPa)")

        // Navigation setup
        findViewById<ImageView>(R.id.home).setOnClickListener {
            startActivity(Intent(this, Home::class.java))
            finish()
        }

        findViewById<ImageView>(R.id.alerts_icon).setOnClickListener {
            startActivity(Intent(this, Alerts::class.java))
            finish()
        }

        findViewById<ImageView>(R.id.stats_icon).setOnClickListener {
            // already here
        }

        findViewById<ImageView>(R.id.option_image).setOnClickListener {
            startActivity(Intent(this, Options::class.java))
        }

        findViewById<ImageView>(R.id.control_icon).setOnClickListener {
            startActivity(Intent(this, Control::class.java))
            finish()
        }
    }

    private fun setupChart(chart: LineChart, entries: List<Entry>, label: String, color: Int, yMin: Float, yMax: Float) {
        val dataSet = LineDataSet(entries, label).apply {
            this.color = color
            valueTextColor = Color.BLACK
            lineWidth = 2f
            circleRadius = 4f
            setCircleColor(color)
            mode = LineDataSet.Mode.CUBIC_BEZIER // smooth curve
            setDrawFilled(true)
            fillColor = color
            fillAlpha = 50
        }

        chart.data = LineData(dataSet)
        chart.setNoDataText("No data available")
        chart.description.isEnabled = false
        chart.axisRight.isEnabled = false
        chart.xAxis.granularity = 1f
        chart.animateY(1000)
        chart.invalidate()
    }
}
