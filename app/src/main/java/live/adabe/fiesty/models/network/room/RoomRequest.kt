package live.adabe.fiesty.models.network.room

import com.google.gson.annotations.SerializedName

data class RoomRequest(
    val name: String,
    @SerializedName("numberOfAppliance")
    val numberOfDevices: Int
)
