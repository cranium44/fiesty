package live.adabe.fiesty.models.network.room

import com.google.gson.annotations.SerializedName

data class RoomResponse(
    @SerializedName("id")
    val rmId: Int,
    val buildingId: Int,
    val name: String,
    @SerializedName("numberOfAppliance")
    val numberOfDevices: Int
)