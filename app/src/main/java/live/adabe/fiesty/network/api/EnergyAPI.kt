package live.adabe.fiesty.network.api

import live.adabe.fiesty.network.NetworkConstants
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface EnergyAPI {
    @GET(NetworkConstants.ENERGY_BUILDING_ENDPOINT)
    suspend fun getBuildingEnergyOutput(@Path("buildingId") buildingId: Int): Response<Double>

    @GET(NetworkConstants.ENERGY_ROOM_ENDPOINT)
    suspend fun getRoomEnergyOutput(@Path("roomId") roomId: Int): Response<Double>

    @GET(NetworkConstants.ENERGY_USER_ENDPOINT)
    suspend fun getUserEnergyOutput(@Path("userId") userId: Int): Response<Double>
}