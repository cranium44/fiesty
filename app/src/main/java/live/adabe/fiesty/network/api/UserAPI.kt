package live.adabe.fiesty.network.api

import live.adabe.fiesty.models.network.user.LoginRequest
import live.adabe.fiesty.models.network.user.UserRequest
import live.adabe.fiesty.models.network.user.UserResponse
import live.adabe.fiesty.network.NetworkConstants
import retrofit2.Response
import retrofit2.http.*

interface UserAPI {
    @POST(NetworkConstants.USER_CREATE_ENDPOINT)
    suspend fun createUser(@Body userRequest: UserRequest): UserResponse

    @POST(NetworkConstants.USER_LOGIN_ENDPOIT)
    suspend fun loginUser(@Body loginRequest: LoginRequest): UserResponse

    @GET(NetworkConstants.USER_GET_ENDPOINT)
    suspend fun getUser(@Path("id") id: Int): UserResponse

    @PUT(NetworkConstants.USER_UPDATE_ENDPOINT)
    suspend fun updateUser(
        @Path("id") id: Int,
        @Body userRequest: UserRequest
    ): UserResponse

    @DELETE(NetworkConstants.USER_DELETE_ENDPOINT)
    suspend fun deleteUser(@Path("id") id: Int): Response<Any>
}