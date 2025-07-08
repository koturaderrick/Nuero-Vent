package com.nuerovent.ui.audit

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.widget.TableRow
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.nuerovent.databinding.ActivityAuditBinding
import com.nuerovent.model.StatsViewModel
import java.text.SimpleDateFormat
import java.util.*

class AuditActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAuditBinding
    private lateinit var statsViewModel: StatsViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAuditBinding.inflate(layoutInflater)
        setContentView(binding.root)

        statsViewModel = ViewModelProvider(
            this,
            ViewModelProvider.AndroidViewModelFactory.getInstance(application)
        )[StatsViewModel::class.java]

        binding.back.setOnClickListener {
            finish()
        }

        statsViewModel.auditEntries.observe(this) { entries ->
            binding.auditTable.removeAllViews()

            val headerRow = LayoutInflater.from(this).inflate(
                com.nuerovent.R.layout.item_audit_row,
                binding.auditTable,
                false
            ) as TableRow

            headerRow.findViewById<TextView>(com.nuerovent.R.id.timeText).text = "Time"
            headerRow.findViewById<TextView>(com.nuerovent.R.id.tempText).text = "Temp"
            headerRow.findViewById<TextView>(com.nuerovent.R.id.humText).text = "Humidity"
            headerRow.findViewById<TextView>(com.nuerovent.R.id.presText).text = "Pressure"
            headerRow.findViewById<TextView>(com.nuerovent.R.id.airText).text = "Air Quality"

            binding.auditTable.addView(headerRow)

            Log.d("AuditActivity", "Audit entries count: ${entries.size}")

            for (entry in entries) {
                val row = LayoutInflater.from(this).inflate(
                    com.nuerovent.R.layout.item_audit_row,
                    binding.auditTable,
                    false
                ) as TableRow

                val date = SimpleDateFormat("HH:mm:ss", Locale.getDefault()).format(Date(entry.timestamp))
                row.findViewById<TextView>(com.nuerovent.R.id.timeText).text = date
                row.findViewById<TextView>(com.nuerovent.R.id.tempText).text = "${entry.avgTemperature}°C"
                row.findViewById<TextView>(com.nuerovent.R.id.humText).text = "${entry.avgHumidity}%"
                row.findViewById<TextView>(com.nuerovent.R.id.presText).text = "${entry.avgPressure} hPa"
                row.findViewById<TextView>(com.nuerovent.R.id.airText).text = "${entry.avgAirQuality} AQI"

                binding.auditTable.addView(row)
            }
        }

        statsViewModel.finalizeAudit()
    }
}
