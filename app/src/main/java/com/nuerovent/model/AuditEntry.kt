package com.nuerovent.model

data class AuditEntry(
    val timestamp: Long,
    val avgTemperature: Float,
    val avgHumidity: Float,
    val avgPressure: Float,
    val avgAirQuality: Float
)
