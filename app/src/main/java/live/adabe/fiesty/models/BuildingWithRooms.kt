package live.adabe.fiesty.models

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.Relation


data class BuildingWithRooms(
    @Embedded val building: Building,
    @Relation(
        parentColumn = "buildId",
        entityColumn = "buildingId"
    )
    val rooms: List<Room>
)
