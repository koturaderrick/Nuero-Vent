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

        // X Axis setup: bottom, show every 5 hours
        val xAxis = chart.xAxis
        xAxis.position = XAxis.XAxisPosition.BOTTOM
        xAxis.granularity = 1f
        xAxis.labelCount = 6
        xAxis.setDrawLabels(true)
        xAxis.valueFormatter = TimeAxisFormatter()
        xAxis.setLabelCount(6, true)

        // Y Axis setup: set min and max for visible range
        val leftAxis = chart.axisLeft
        leftAxis.axisMinimum = yMin
        leftAxis.axisMaximum = yMax

        chart.animateY(1000)
        chart.invalidate()
    }

    class TimeAxisFormatter : ValueFormatter() {
        override fun getFormattedValue(value: Float): String {
            // Show label only every 5 hours (0,5,10,15,20,24)
            val hour = value.toInt()
            return when (hour) {
                0, 5, 10, 15, 20, 24 -> String.format("%02d:00", hour)
                else -> ""
            }
        }
    }
}
