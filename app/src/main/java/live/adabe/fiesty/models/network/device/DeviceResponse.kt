package live.adabe.fiesty.models.network.device

import java.time.Duration
import java.time.Instant

data class DeviceResponse(
    var applianceName: String,
    var deviceRating: Double,
    var duration: Any?,
    var energyUse: Double,
    var id: Int,
    var startTime: String,
    var stopTime: String
)
