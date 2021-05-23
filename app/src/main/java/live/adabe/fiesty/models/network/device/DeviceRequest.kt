package live.adabe.fiesty.models.network.device

import java.time.Duration
import java.time.Instant
import java.time.LocalTime

data class DeviceRequest(
    val name: String,
    val rating: Double,
    @Transient val duration: Duration,
    val startTime: String,
    val stopTime: String
)
