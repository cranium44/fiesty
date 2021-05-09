package live.adabe.fiesty.network.repository

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import live.adabe.fiesty.db.BuildingDAO
import javax.inject.Inject

class DatabaseRepository @Inject constructor(
    private val buildingRepository: BuildingRepository,
    private val buildingDAO: BuildingDAO
) {
    suspend fun updateBuildingDatabase() {
        withContext(Dispatchers.IO){

        }
    }
}