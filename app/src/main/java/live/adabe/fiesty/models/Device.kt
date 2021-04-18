package live.adabe.fiesty.models

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity
data class Device(
    @PrimaryKey
    val deviceId: Int,
    val roomId: Int,
    val name: String,
)
