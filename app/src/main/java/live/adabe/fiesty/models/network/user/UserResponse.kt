package live.adabe.fiesty.models.network.user

data class UserResponse(
    val id: Int,
    val firstName: String,
    val lastName: String,
    val email: String,
    val password: String,
    val energyRate: Long
)
