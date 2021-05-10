package live.adabe.fiesty.models.network.room

data class RoomResponse(
    val rmId: Int,
    val buildingId: Int,
    val name: String,
    val numberOfDevices: Int
)