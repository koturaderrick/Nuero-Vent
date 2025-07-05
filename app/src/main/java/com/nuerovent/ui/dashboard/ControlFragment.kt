package com.nuerovent.ui.dashboard

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.nuerovent.databinding.FragmentControlBinding


class ControlFragment : Fragment() {
    private lateinit var binding: FragmentControlBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentControlBinding.inflate(layoutInflater)
        return binding.root
    }

    companion object {

        @JvmStatic
        fun newInstance() =
            ControlFragment().apply {}
    }
}