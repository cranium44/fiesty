package live.adabe.fiesty.network

import androidx.lifecycle.LiveData
import live.adabe.fiesty.models.network.user.UserRequest
import live.adabe.fiesty.models.network.user.UserResponse
import retrofit2.http.*

interface UserAPI {
    @POST(NetworkConstants.USER_CREATE_ENDPOINT)
    suspend fun createUser(@Body userRequest: UserRequest): LiveData<UserResponse>

    @GET(NetworkConstants.USER_GET_ENDPOINT)
    suspend fun getUser(@Path("id") id: Int): LiveData<UserResponse>

    @PUT(NetworkConstants.USER_UPDATE_ENDPOINT)
    suspend fun updateUser(
        @Path("id") id: Int,
        @Body userRequest: UserRequest
    ): LiveData<UserResponse>
    
    @DELETE(NetworkConstants.USER_DELETE_ENDPOINT)
    suspend fun deleteUser(@Path("id") id: Int)
}