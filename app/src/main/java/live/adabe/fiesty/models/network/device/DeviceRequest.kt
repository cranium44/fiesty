package live.adabe.fiesty.models.network.device

import java.time.Duration
import java.time.Instant

data class DeviceRequest(
    val name: String,
    val rating: Double,
    val duration: Duration,
    val startTime: Instant,
    val endTime: Instant
)
