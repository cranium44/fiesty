package live.adabe.fiesty.db

import androidx.room.*
import androidx.room.OnConflictStrategy.REPLACE
import live.adabe.fiesty.models.Room


@Dao
interface RoomDAO {
    @Query("SELECT * FROM room WHERE buildingId = :buildId")
    suspend fun getAllRoomsByBuilding(buildId: Int): List<Room>

    @Query("SELECT * FROM room")
    suspend fun getAllRooms(): List<Room>

    @Query("SELECT * FROM room WHERE rmId = :rmId")
    suspend fun getRoomById(rmId: Int): Room

    @Insert(onConflict = REPLACE)
    suspend fun addRoom(room: Room)

    @Insert(onConflict = REPLACE)
    suspend fun addRooms(vararg room: Room)

    @Update
    suspend fun updateRoom(room: Room)

    @Delete
    suspend fun deleteRoom(room: Room)
}