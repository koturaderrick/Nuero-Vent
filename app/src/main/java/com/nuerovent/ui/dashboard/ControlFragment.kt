package com.nuerovent.ui.dashboard

import android.os.Bundle
import android.text.InputType
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.nuerovent.adapter.ControlAdapter
import com.nuerovent.adapter.DefaultConditionAdapter
import com.nuerovent.databinding.FragmentControlBinding
import com.nuerovent.model.ControlItem
import com.nuerovent.model.DefaultConditionItem
import okhttp3.*
import java.io.IOException

class ControlFragment : Fragment() {

    private lateinit var binding: FragmentControlBinding
    private lateinit var adapter: ControlAdapter
    private val client = OkHttpClient()

    // Control items for first RecyclerView
    private val controlItems = mutableListOf(
        ControlItem("Inductor Fan", false, "Auto mode"),
        ControlItem("Extractor Fan", false, "Manual mode"),
        ControlItem("Humidifier", false, "Active"),
        ControlItem("Heating Unit", false, "Scheduled"),
        ControlItem("Cooling Unit", false, "Idle")
    )

    // Default condition items for second RecyclerView (make mutable to update)
    private val defaultConditions = mutableListOf(
        DefaultConditionItem("Target Temperature", "22°C"),
        DefaultConditionItem("Target Humidity", "50%"),
        DefaultConditionItem("Target Pressure", "Neutral"),
        DefaultConditionItem("Air Quality Threshold", "Low")
    )

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentControlBinding.inflate(inflater, container, false)

        setupRecyclerViews() // initialize both RecyclerViews here

        return binding.root
    }

    private fun setupRecyclerViews() {
        // Setup first RecyclerView (controls)
        adapter = ControlAdapter(controlItems) { position, isChecked ->
            controlItems[position].isChecked = isChecked

            // If the control is one of the fans, send the toggle command to ESP
            when (controlItems[position].label) {
                "Inductor Fan" -> toggleFan(isChecked)
                "Extractor Fan" -> toggleFan(isChecked)
                // Add more controls here if you wire more relays
            }
        }
        binding.controlRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.controlRecyclerView.adapter = adapter

        // Setup second RecyclerView (default conditions) with popup menu callback
        val defaultAdapter = DefaultConditionAdapter(defaultConditions) { position, action ->
            when (action) {
                "set_temp" -> showInputDialog(position, "Set Temperature", "°C")
                "set_humidity" -> showInputDialog(position, "Set Humidity", "%")
                "set_pressure" -> showInputDialog(position, "Set Pressure", "")
            }
        }
        binding.defaultConditionRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.defaultConditionRecyclerView.adapter = defaultAdapter
    }

    private fun showInputDialog(position: Int, title: String, unit: String) {
        val context = requireContext()
        val input = EditText(context).apply {
            inputType = InputType.TYPE_CLASS_NUMBER or InputType.TYPE_NUMBER_FLAG_DECIMAL
            hint = "Enter value"
        }

        AlertDialog.Builder(context)
            .setTitle(title)
            .setView(input)
            .setPositiveButton("Save") { dialog, _ ->
                val newValue = input.text.toString().trim()
                if (newValue.isNotEmpty()) {
                    defaultConditions[position] =
                        DefaultConditionItem(defaultConditions[position].title, "$newValue$unit")
                    binding.defaultConditionRecyclerView.adapter?.notifyItemChanged(position)
                }
                dialog.dismiss()
            }
            .setNegativeButton("Cancel") { dialog, _ -> dialog.dismiss() }
            .show()
    }

    private fun toggleFan(turnOn: Boolean) {
        val espIp = "192.168.4.1"  //
        val url = "http://$espIp/fan?state=${if (turnOn) "on" else "off"}"

        val request = Request.Builder().url(url).build()
        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                // Optionally handle failure (e.g., show a toast)
                e.printStackTrace()
            }

            override fun onResponse(call: Call, response: Response) {
                response.close()
            }
        })
    }

    companion object {
        @JvmStatic
        fun newInstance() = ControlFragment()
    }
}
