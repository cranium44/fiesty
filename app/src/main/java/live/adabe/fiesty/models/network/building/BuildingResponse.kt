package live.adabe.fiesty.models.network.building

import live.adabe.fiesty.models.network.room.RoomResponse
import live.adabe.fiesty.models.network.user.UserResponse

data class BuildingResponse(
    val id: Int,
    val name: String,
    val address: String,
    val energyRate: Long,
)
