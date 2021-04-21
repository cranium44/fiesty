package live.adabe.fiesty.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.Duration
import java.time.Instant


@Entity
data class Device(
    @PrimaryKey
    val deviceId: Int,
    val roomId: Int,
    val name: String,
    val duration: Duration,
    val startTime: Instant,
    val stopTime: Instant,
    val energyUse: Double,
)
