package live.adabe.fiesty.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import live.adabe.fiesty.models.Building

@Dao
interface BuildingDAO {
    @Query("SELECT * from building")
    fun getAllBuildings(): LiveData<List<Building>>

    @Query("SELECT * from building WHERE name LIKE :name")
    fun getBuildingByName(name: String): LiveData<List<Building>>

    @Insert
    fun addOneBuilding()
    
    @Insert
    fun addBuildings(vararg buildings: Building)

    @Delete
    fun deleteBuilding(id: Int)
}