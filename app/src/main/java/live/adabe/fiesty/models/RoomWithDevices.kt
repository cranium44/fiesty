package live.adabe.fiesty.models

import androidx.room.Embedded
import androidx.room.Relation

data class RoomWithDevices(
    @Embedded val room: Room,
    @Relation(
    parentColumn = "rmId",
        entityColumn = "roomId"
    )
    val devices: List<Device>
)
