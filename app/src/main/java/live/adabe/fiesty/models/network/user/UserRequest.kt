package live.adabe.fiesty.models.network.user

data class UserRequest(
    val firstName: String,
    val lastName: String,
    val email: String,
    val password: String,
    val phoneNumber: String
)