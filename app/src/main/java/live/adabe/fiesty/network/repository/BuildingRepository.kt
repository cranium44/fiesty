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
import timber.log.Timber
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
            return@withContext try {
                buildingAPI.getAllUserBuildings(preferences.getId())
            } catch (t: Throwable) {
                Timber.e(t.message.toString())
                emptyList()
            }
        }
    }

    suspend fun getBuilding(buildingId: Int): BuildingResponse? {
        return withContext(Dispatchers.IO) {
            return@withContext try {
                buildingAPI.getUserBuilding(preferences.getId(), buildingId)
            } catch (t: Throwable) {
                null
            }
        }
    }

    suspend fun updateBuilding(id: Int, buildingRequest: BuildingRequest) =
        buildingAPI.updateBuilding(preferences.getId(), id, buildingRequest = buildingRequest)

    suspend fun deleteBuilding(id: Int): Boolean {
        return withContext(Dispatchers.IO) {
            return@withContext try {
                buildingAPI.deleteBuilding(id).isSuccessful
            } catch (t: Throwable) {
                Timber.e(t.message.toString())
                false
            }
        }
    }

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