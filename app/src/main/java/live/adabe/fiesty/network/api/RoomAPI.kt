package live.adabe.fiesty.network.api

import live.adabe.fiesty.models.network.room.RoomRequest
import live.adabe.fiesty.models.network.room.RoomResponse
import live.adabe.fiesty.network.NetworkConstants
import retrofit2.Response
import retrofit2.http.*

interface RoomAPI {
    @POST(NetworkConstants.ROOM_CREATE_ENDPOINT)
    suspend fun createRoom(
        @Path("buildingId") buildingId: Int,
        @Body roomRequest: RoomRequest
    ): RoomResponse

    @GET(NetworkConstants.ROOM_GET_ENDPOINT)
    suspend fun getRoom(@Path("roomId") roomId: Int): RoomResponse

    @GET(NetworkConstants.ROOM_GETALL_ENDPOINT)
    suspend fun getAllRooms(@Path("buildingId") buildingId: Int): List<RoomResponse>

    @PUT(NetworkConstants.ROOM_UPDATE_ENDPOINT)
    suspend fun updateRoom(
        @Path("roomId") roomId: Int,
        @Path("buildingId") buildingId: Int,
        @Body roomRequest: RoomRequest
    ): RoomResponse

    @DELETE(NetworkConstants.ROOM_DELETE_ENDPOINT)
    suspend fun deleteRoom(@Path("roomId") roomId: Int): Response<Any>
}