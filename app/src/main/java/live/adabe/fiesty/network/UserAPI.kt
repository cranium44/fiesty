package live.adabe.fiesty.network

import androidx.lifecycle.LiveData
import live.adabe.fiesty.models.network.UserRequest
import retrofit2.http.Body
import retrofit2.http.POST

interface UserAPI {
    @POST(NetworkConstants.USER_CREATE_ENDPOINT)
    suspend fun createUser(@Body userRequest: UserRequest): LiveData<UserRequest>
}