package com.nuerovent.ui.dashboard

import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.formatter.ValueFormatter
import com.nuerovent.databinding.FragmentStatsBinding

class StatsFragment : Fragment() {

    private lateinit var binding: FragmentStatsBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentStatsBinding.inflate(layoutInflater)
        initViews()
        return binding.root
    }

    private fun initViews() {
        val tempEntries = listOf(
            Entry(0f, 22f),
            Entry(5f, 24f),
            Entry(10f, 18f),
            Entry(15f, 23f),
            Entry(20f, 25f),
            Entry(24f, 22f)
        )
        val humidityEntries = listOf(
            Entry(0f, 60f),
            Entry(5f, 58f),
            Entry(10f, 62f),
            Entry(15f, 80f),
            Entry(20f, 63f),
            Entry(24f, 60f)
        )
        val pressureEntries = listOf(
            Entry(0f, 1012f),
            Entry(5f, 1013f),
            Entry(10f, 1011f),
            Entry(15f, 1014f),
            Entry(20f, 1010f),
            Entry(24f, 1012f)
        )

        setupChart(binding.lineChartTemp, tempEntries, "Temperature (°C)", Color.RED, 15f, 30f)
        setupChart(binding.lineChartHumidity, humidityEntries, "Humidity (%)", Color.BLUE, 40f, 80f)
        setupChart(
            binding.lineChartPressure,
            pressureEntries,
            "Pressure (hPa)",
            Color.MAGENTA,
            1005f,
            1020f
        )

    }

    private fun setupChart(
        chart: LineChart,
        entries: List<Entry>,
        label: String,
        color: Int,
        yMin: Float,
        yMax: Float
    ) {
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
        @SuppressLint("DefaultLocale")
        override fun getFormattedValue(value: Float): String {
            // Show label only every 5 hours (0,5,10,15,20,24)
            val hour = value.toInt()
            return when (hour) {
                0, 5, 10, 15, 20, 24 -> String.format("%02d:00", hour)
                else -> ""
            }
        }
    }

    companion object {

        @JvmStatic
        fun newInstance() =
            StatsFragment().apply {}
    }
}