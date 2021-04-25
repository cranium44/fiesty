package live.adabe.fiesty.models.network

data class UserResponse(
    val id: Int,
    val firstName: String,
    val lastName: String,
    val email: String,
    val password: String,
    val energyRate: Long
)
