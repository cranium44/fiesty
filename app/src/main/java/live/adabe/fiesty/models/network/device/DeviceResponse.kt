package live.adabe.fiesty.models.network.device

import live.adabe.fiesty.models.network.room.RoomResponse
import java.time.Duration
import java.time.Instant

data class DeviceResponse(
    val id: Int,
    val roomId: Int,
    val name: String,
    val rating: Double,
    val duration: Duration,
    val startTime: Instant,
    val stopTime: Instant,
)
