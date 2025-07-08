package com.nuerovent.model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.github.mikephil.charting.data.Entry

class StatsViewModel : ViewModel() {

    // Use immutable LiveData to expose, mutable internally
    private val _temperatureEntries = MutableLiveData<MutableList<Entry>>(mutableListOf())
    val temperatureEntries: LiveData<MutableList<Entry>> = _temperatureEntries

    private val _humidityEntries = MutableLiveData<MutableList<Entry>>(mutableListOf())
    val humidityEntries: LiveData<MutableList<Entry>> = _humidityEntries

    private val _pressureEntries = MutableLiveData<MutableList<Entry>>(mutableListOf())
    val pressureEntries: LiveData<MutableList<Entry>> = _pressureEntries

    fun addTemperatureEntry(time: Float, value: Float) {
        val list = _temperatureEntries.value ?: mutableListOf()
        list.add(Entry(time, value))
        _temperatureEntries.postValue(list)
    }

    fun addHumidityEntry(time: Float, value: Float) {
        val list = _humidityEntries.value ?: mutableListOf()
        list.add(Entry(time, value))
        _humidityEntries.postValue(list)
    }

    fun addPressureEntry(time: Float, value: Float) {
        val list = _pressureEntries.value ?: mutableListOf()
        list.add(Entry(time, value))
        _pressureEntries.postValue(list)
    }

    fun clearAllData() {
        _temperatureEntries.postValue(mutableListOf())
        _humidityEntries.postValue(mutableListOf())
        _pressureEntries.postValue(mutableListOf())
    }
}
