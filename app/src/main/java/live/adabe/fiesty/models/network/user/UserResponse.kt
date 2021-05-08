package live.adabe.fiesty.models.network.user

import com.google.gson.annotations.SerializedName

data class UserResponse(
    val id: Int,
    val firstName: String,
    @SerializedName("lastname")
    val lastName: String,
    val email: String,
    val password: String,)
