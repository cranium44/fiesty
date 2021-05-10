package live.adabe.fiesty.db

import androidx.lifecycle.LiveData
import androidx.room.*
import live.adabe.fiesty.models.Building

@Dao
interface BuildingDAO {
    @Query("SELECT * from building")
    fun getAllBuildings(): LiveData<List<Building>>

    @Query("SELECT * from building WHERE name LIKE :name")
    fun getBuildingByName(name: String): LiveData<List<Building>>

    @Insert
    fun addOneBuilding(building: Building)

    @Insert
    fun addBuildings(vararg buildings: Building)

    @Update
    fun updateBuilding(building: Building)

    @Delete
    fun deleteBuilding(building: Building)
}