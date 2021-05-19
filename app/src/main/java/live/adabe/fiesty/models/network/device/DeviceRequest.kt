package live.adabe.fiesty.models.network.device

import java.time.Duration
import java.time.Instant
import java.time.LocalTime

data class DeviceRequest(
    val name: String,
    val rating: Double,
    val duration: Duration,
    val startTime: LocalTime,
    val stopTime: LocalTime
)
