package com.nuerovent.ui.dashboard

import ItemAdapter
import android.graphics.Rect
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.nuerovent.R
import com.nuerovent.databinding.FragmentHomeBinding
import com.nuerovent.model.HomeItem
import okhttp3.Call
import okhttp3.Callback
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import org.json.JSONObject
import java.io.IOException

class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding

    private val client = OkHttpClient()
    private lateinit var itemViewAdapter: ItemAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(layoutInflater)

        binding.initViews()
        return binding.root
    }

    private fun FragmentHomeBinding.initViews() {
        itemViewAdapter = ItemAdapter()

        with(recyclerView) {
            layoutManager = GridLayoutManager(requireContext(), 2)
            isNestedScrollingEnabled = false
            adapter = itemViewAdapter

            addItemDecoration(GridSpacingItemDecoration(2))
        }

        itemViewAdapter.setDataList(
            listOf(
                HomeItem(getString(R.string.temperature), ""),
                HomeItem(getString(R.string.pressure), ""),
                HomeItem(getString(R.string.temperature), "15.6"),
                HomeItem(getString(R.string.temperature), "20.0"),
            )
        )
        startUpdatingSensorData()
    }

    private fun startUpdatingSensorData() {
        Handler(Looper.getMainLooper()).post(object : Runnable {
            override fun run() {
                fetchSensorData()
                Handler(Looper.getMainLooper()).postDelayed(this, 5000)
            }
        })
    }

    private fun fetchSensorData() {
        val request = Request.Builder().url("http://192.168.4.1/data").build()

        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                Handler(Looper.getMainLooper()).post {
                    context?.let {
                        itemViewAdapter.setDataList(
                            listOf(
                                HomeItem(getString(R.string.temperature), "--°C"),
                                HomeItem(getString(R.string.pressure), "__°C"),
                                HomeItem(getString(R.string.humidity), "--%"),
                                HomeItem(getString(R.string.temperature), "--°C"),
                            )
                        )
                    }

                }
            }

            override fun onResponse(call: Call, response: Response) {
                val responseData = response.body?.string()
                Handler(Looper.getMainLooper()).post {
                    try {
                        val json = JSONObject(responseData ?: "{}")
                        val temp = json.optDouble("temperature", -1.0)
                        val hum = json.optDouble("humidity", -1.0)

                        context?.let {
                            itemViewAdapter.setDataList(
                                listOf(
                                    HomeItem(
                                        getString(R.string.temperature),
                                        if (temp >= 0) "$temp°C" else "--°C"
                                    ),
                                    HomeItem(getString(R.string.pressure), "__°C"),
                                    HomeItem(
                                        getString(R.string.humidity),
                                        if (hum >= 0) "$hum%" else "--%"
                                    ),
                                    HomeItem(getString(R.string.temperature), "20.0"),
                                )
                            )
                        }
                    } catch (e: Exception) {
                        context?.let {
                            itemViewAdapter.setDataList(
                                listOf(
                                    HomeItem(getString(R.string.temperature), "--°C"),
                                    HomeItem(getString(R.string.pressure), "__°C"),
                                    HomeItem(getString(R.string.humidity), "--%"),
                                    HomeItem(getString(R.string.temperature), "--°C"),
                                )
                            )
                        }
                    }
                }
            }
        })
    }

    class GridSpacingItemDecoration(
        private val spanCount: Int
    ) : RecyclerView.ItemDecoration() {

        val spacing = 40
        override fun getItemOffsets(
            outRect: Rect, view: View,
            parent: RecyclerView, state: RecyclerView.State
        ) {
            val position = parent.getChildAdapterPosition(view)
            val column = position % spanCount

            outRect.left = column * spacing / spanCount
            outRect.right = spacing - (column + 1) * spacing / spanCount
            if (position >= spanCount) {
                outRect.top = spacing // item top
            }

        }
    }

    companion object {

        @JvmStatic
        fun newInstance() =
            HomeFragment().apply {
                arguments = Bundle().apply {
                }
            }
    }
}