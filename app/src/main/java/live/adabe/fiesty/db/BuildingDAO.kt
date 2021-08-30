package live.adabe.fiesty.db

import androidx.lifecycle.LiveData
import androidx.room.*
import live.adabe.fiesty.models.Building

@Dao
interface BuildingDAO {
    @Query("SELECT * from building")
    suspend fun getAllBuildings(): List<Building>

    @Query("SELECT * from building WHERE name LIKE :name")
    suspend fun getBuildingByName(name: String): List<Building>

    @Query("SELECT * from building WHERE buildId = :id")
    suspend fun getBuildingById(id: Int): Building

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addOneBuilding(building: Building)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addBuildings(vararg buildings: Building)

    @Update
    suspend fun updateBuilding(building: Building)

    @Delete
    suspend fun deleteBuilding(building: Building)
}