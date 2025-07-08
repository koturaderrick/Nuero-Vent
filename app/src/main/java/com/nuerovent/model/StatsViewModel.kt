package com.nuerovent.model

import android.app.Application
import android.os.Handler
import android.os.Looper
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.github.mikephil.charting.data.Entry

class StatsViewModel(application: Application) : AndroidViewModel(application) {

    private val _temperatureEntries = MutableLiveData<MutableList<Entry>>(mutableListOf())
    val temperatureEntries: LiveData<MutableList<Entry>> = _temperatureEntries

    private val _humidityEntries = MutableLiveData<MutableList<Entry>>(mutableListOf())
    val humidityEntries: LiveData<MutableList<Entry>> = _humidityEntries

    private val _pressureEntries = MutableLiveData<MutableList<Entry>>(mutableListOf())
    val pressureEntries: LiveData<MutableList<Entry>> = _pressureEntries

    private val _latestTemperature = MutableLiveData<Float?>()
    val latestTemperature: LiveData<Float?> = _latestTemperature

    private val _latestHumidity = MutableLiveData<Float?>()
    val latestHumidity: LiveData<Float?> = _latestHumidity

    private val _latestPressure = MutableLiveData<Float?>()
    val latestPressure: LiveData<Float?> = _latestPressure

    private val _latestAirQuality = MutableLiveData<Float?>()
    val latestAirQuality: LiveData<Float?> = _latestAirQuality

    private val _auditEntries = MutableLiveData<MutableList<AuditEntry>>(mutableListOf())
    val auditEntries: LiveData<MutableList<AuditEntry>> = _auditEntries

    private var auditTempSum = 0f
    private var auditHumiditySum = 0f
    private var auditPressureSum = 0f
    private var auditAirQualitySum = 0f
    private var auditSampleCount = 0

    private var auditTimerHandler: Handler? = null
    private var auditTimerRunnable: Runnable? = null

    fun addTemperatureEntry(time: Float, value: Float) {
        val list = _temperatureEntries.value ?: mutableListOf()
        list.add(Entry(time, value))
        _temperatureEntries.postValue(list)
        _latestTemperature.postValue(value)
    }

    fun addHumidityEntry(time: Float, value: Float) {
        val list = _humidityEntries.value ?: mutableListOf()
        list.add(Entry(time, value))
        _humidityEntries.postValue(list)
        _latestHumidity.postValue(value)
    }

    fun addPressureEntry(time: Float, value: Float) {
        val list = _pressureEntries.value ?: mutableListOf()
        list.add(Entry(time, value))
        _pressureEntries.postValue(list)
        _latestPressure.postValue(value)
    }

    fun updateAirQuality(value: Float) {
        _latestAirQuality.postValue(value)
    }

    fun addAuditSample(temp: Float, humidity: Float, pressure: Float, airQuality: Float) {
        auditTempSum += temp
        auditHumiditySum += humidity
        auditPressureSum += pressure
        auditAirQualitySum += airQuality
        auditSampleCount++
    }

    fun finalizeAudit() {
        if (auditSampleCount == 0) return

        val avgTemp = auditTempSum / auditSampleCount
        val avgHumidity = auditHumiditySum / auditSampleCount
        val avgPressure = auditPressureSum / auditSampleCount
        val avgAirQuality = auditAirQualitySum / auditSampleCount

        val auditEntry = AuditEntry(
            timestamp = System.currentTimeMillis(),
            avgTemperature = avgTemp,
            avgHumidity = avgHumidity,
            avgPressure = avgPressure,
            avgAirQuality = avgAirQuality
        )

        val updatedList = _auditEntries.value ?: mutableListOf()
        updatedList.add(auditEntry)
        _auditEntries.postValue(updatedList)

        auditTempSum = 0f
        auditHumiditySum = 0f
        auditPressureSum = 0f
        auditAirQualitySum = 0f
        auditSampleCount = 0
    }

    fun startAuditTimer() {
        if (auditTimerHandler != null) return

        auditTimerHandler = Handler(Looper.getMainLooper())
        auditTimerRunnable = object : Runnable {
            override fun run() {
                finalizeAudit()
                auditTimerHandler?.postDelayed(this, 3 * 1000) // 3 seconds
            }
        }
        auditTimerHandler?.postDelayed(auditTimerRunnable!!, 3 * 1000) // initial 3 seconds delay
    }


    fun clearAllData() {
        _temperatureEntries.postValue(mutableListOf())
        _humidityEntries.postValue(mutableListOf())
        _pressureEntries.postValue(mutableListOf())
        _latestTemperature.postValue(null)
        _latestHumidity.postValue(null)
        _latestPressure.postValue(null)
        _latestAirQuality.postValue(null)
        _auditEntries.postValue(mutableListOf())
    }
}
