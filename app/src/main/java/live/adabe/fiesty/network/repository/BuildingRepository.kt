package live.adabe.fiesty.network.repository

import androidx.lifecycle.LiveData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import live.adabe.fiesty.db.BuildingDAO
import live.adabe.fiesty.db.Preferences
import live.adabe.fiesty.models.Building
import live.adabe.fiesty.models.network.building.BuildingRequest
import live.adabe.fiesty.models.network.building.BuildingResponse
import live.adabe.fiesty.network.api.BuildingAPI
import live.adabe.fiesty.network.api.EnergyAPI
import timber.log.Timber
import javax.inject.Inject

class BuildingRepository @Inject constructor(
    private val buildingAPI: BuildingAPI,
    private val preferences: Preferences,
    private val buildingDAO: BuildingDAO,
    private val energyAPI: EnergyAPI
) {

    init {
        CoroutineScope(Dispatchers.IO).launch {
            updateBuildingsInDb()
        }
    }
    suspend fun createBuilding(buildingRequest: BuildingRequest): BuildingResponse? {
        return try{
            val response = buildingAPI.createBuilding(preferences.getId(), buildingRequest)
            buildingDAO.addOneBuilding(
                Building(
                    buildId = response.id,
                    name = response.name,
                    address = response.address,
                    energyRate = response.energyRate
                )
            )
            response
        }catch(t: Throwable){
            null
        }
    }


    suspend fun getAllUserBuildings(): List<Building> {
        return withContext(Dispatchers.IO) {
            try {
                buildingDAO.getAllBuildings()
            } catch (t: Throwable) {
                Timber.e(t.message.toString())
                emptyList()
            }
        }
    }

    private suspend fun updateBuildingsInDb() {
        withContext(Dispatchers.IO) {
            try {
                val response = buildingAPI.getAllUserBuildings(preferences.getId())
                val converted = response.map {
                    Building(
                        buildId = it.id,
                        name = it.name,
                        address = it.address,
                        energyRate = it.energyRate
                    )
                }
                buildingDAO.addBuildings(*converted.toTypedArray())
            } catch (t: Throwable) {
                Timber.e(t.message.toString())
            }
        }
    }

    suspend fun getBuilding(buildingId: Int): Building? {
        return withContext(Dispatchers.IO) {
            try {
                buildingDAO.getBuildingById(buildingId)
            } catch (t: Throwable) {
                null
            }
        }
    }

    suspend fun getBuildingEnergyUse(buildingId: Int): Double {
        return withContext(Dispatchers.IO) {
            try {
                energyAPI.getBuildingEnergyOutput(buildingId)
            } catch (t: Throwable) {
                0.0
            }
        }
    }

    suspend fun updateBuilding(id: Int, buildingRequest: BuildingRequest) =
        buildingAPI.updateBuilding(preferences.getId(), id, buildingRequest = buildingRequest)

    suspend fun deleteBuilding(id: Int): Boolean {
        return withContext(Dispatchers.IO) {
            try {
                buildingAPI.deleteBuilding(id).isSuccessful
            } catch (t: Throwable) {
                Timber.e(t.message.toString())
                false
            }
        }
    }


    suspend fun editBuilding(building: Building) {
        withContext(Dispatchers.IO) {
            buildingDAO.updateBuilding(building)
        }
    }

}