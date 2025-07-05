package com.nuerovent.ui.dashboard

import AlertAdapter
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.nuerovent.databinding.FragmentAlertsBinding

class AlertsFragment : Fragment() {

    private lateinit var binding: FragmentAlertsBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAlertsBinding.inflate(layoutInflater)
        binding.initViews()
        return binding.root
    }

    private fun FragmentAlertsBinding.initViews() {
        with(recyclerView) {
            layoutManager = LinearLayoutManager(requireContext())
            recyclerView.adapter = AlertAdapter(
                listOf<String>(
                    "",
                    "",
                    ""
                )
            )/*addItemDecoration(Constants.VerticalSpacingItemDecoration(45))*/
        }

    }

    companion object {

        @JvmStatic
        fun newInstance() = AlertsFragment().apply {}
    }
}