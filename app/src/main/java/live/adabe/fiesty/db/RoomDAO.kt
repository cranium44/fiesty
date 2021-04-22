package live.adabe.fiesty.db

import androidx.lifecycle.LiveData
import androidx.room.*
import androidx.room.OnConflictStrategy.REPLACE
import live.adabe.fiesty.models.Room


@Dao
interface RoomDAO {
    @Query("SELECT * FROM room")
    fun getAllRooms(): LiveData<List<Room>>

    @Insert
    fun addRoom(room: Room)

    @Update
    fun updateRoom(room: Room)

    @Delete
    fun deleteRoom(room: Room)
}