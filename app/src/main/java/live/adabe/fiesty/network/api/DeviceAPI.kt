package live.adabe.fiesty.network.api

import live.adabe.fiesty.models.network.device.DeviceRequest
import live.adabe.fiesty.models.network.device.DeviceResponse
import live.adabe.fiesty.network.NetworkConstants
import retrofit2.Response
import retrofit2.http.*

interface DeviceAPI {
    @POST(NetworkConstants.DEVICE_CREATE_ENDPOINT)
    suspend fun createDevice(
        @Path("roomId") roomId: Int,
        @Body deviceRequest: DeviceRequest
    ): DeviceResponse

    @GET(NetworkConstants.DEVICE_GET_ENDPOINT)
    suspend fun getDevice(@Path("id") id: Int): DeviceResponse

    @GET(NetworkConstants.DEVICE_GETALL_ENDPOINT)
    suspend fun getAllDevices(@Path("roomId") roomId: Int): List<DeviceResponse>

    @PUT(NetworkConstants.DEVICE_UPDATE_ENDPOINT)
    suspend fun updateDevice(
        @Path("roomId") roomId: Int,
        @Path("id") id: Int,
        @Body deviceRequest: DeviceRequest
    ): DeviceResponse

    @DELETE(NetworkConstants.DEVICE_DELETE_ENDPOINT)
    suspend fun deleteDevice(@Path("id") id: Int): Response<Any>
}