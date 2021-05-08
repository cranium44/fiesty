package live.adabe.fiesty.network.repository

import androidx.lifecycle.LiveData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import live.adabe.fiesty.db.BuildingDAO
import live.adabe.fiesty.db.Preferences
import live.adabe.fiesty.models.Building
import live.adabe.fiesty.models.network.building.BuildingRequest
import live.adabe.fiesty.models.network.building.BuildingResponse
import live.adabe.fiesty.network.api.BuildingAPI
import javax.inject.Inject

class BuildingRepository @Inject constructor(
    private val buildingAPI: BuildingAPI,
    private val preferences: Preferences,
    private val buildingDAO: BuildingDAO
) {
    suspend fun createBuilding(buildingRequest: BuildingRequest) =
        buildingAPI.createBuilding(preferences.getId(), buildingRequest)

    suspend fun getAllUserBuildings(): List<BuildingResponse> {
        return withContext(Dispatchers.IO) {
            return@withContext buildingAPI.getAllUserBuildings(preferences.getId())
        }
    }

    suspend fun updateBuilding(id: Int, buildingRequest: BuildingRequest) =
        buildingAPI.updateBuilding(preferences.getId(), id, buildingRequest = buildingRequest)

    suspend fun deleteBuilding(id: Int) = buildingAPI.deleteBuilding(id)

    suspend fun saveBuildingToDb(building: Building) {
        withContext(Dispatchers.IO) {
            buildingDAO.addOneBuilding(building)
        }
    }

    suspend fun getBuildingsFromDb(): LiveData<List<Building>> {
        return withContext(Dispatchers.IO) {

            return@withContext buildingDAO.getAllBuildings()
        }
    }

    suspend fun editBuilding(building: Building) {
        withContext(Dispatchers.IO) {
            buildingDAO.updateBuilding(building)
        }
    }

}