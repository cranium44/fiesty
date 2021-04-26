package live.adabe.fiesty.network

import androidx.lifecycle.LiveData
import live.adabe.fiesty.models.network.building.BuildingRequest
import live.adabe.fiesty.models.network.building.BuildingResponse
import retrofit2.http.*

interface BuildingAPI {
    @POST(NetworkConstants.BUILDING_CREATE_ENDPOINT)
    suspend fun createBuilding(
        @Path("userId") userId: Int,
        @Body buildingRequest: BuildingRequest
    ): LiveData<BuildingResponse>

    @GET(NetworkConstants.BUILDING_GET_ENDPOINT)
    suspend fun getUserBuilding(
        @Path("userId") userId: Int,
        @Path("id") id: Int
    ): LiveData<BuildingResponse>

    @GET(NetworkConstants.BUILDING_GETALL_ENDPOINT)
    suspend fun getAllUserBuildings(@Path("userId") userId: Int): LiveData<List<BuildingResponse>>

    @PUT(NetworkConstants.BUILDING_UPDATE_ENDPOINT)
    suspend fun updateBuilding(
        @Path("userId") userId: Int,
        @Path("id") id: Int,
        @Body buildingRequest: BuildingRequest
    ): LiveData<BuildingResponse>

    @DELETE(NetworkConstants.BUILDING_DELETE_ENDPOINT)
    suspend fun deleteBuilding(@Path("userId") userId: Int)

}