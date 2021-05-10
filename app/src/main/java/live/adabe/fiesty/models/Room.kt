package live.adabe.fiesty.models

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity
data class Room(
    @PrimaryKey
    val rmId: Int,
    val buildingId: Int,
    val name: String,
    val numberOfDevices: Int
)
