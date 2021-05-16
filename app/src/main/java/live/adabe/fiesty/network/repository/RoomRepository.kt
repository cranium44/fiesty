package live.adabe.fiesty.network.repository

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import live.adabe.fiesty.models.Room
import live.adabe.fiesty.models.network.room.RoomRequest
import live.adabe.fiesty.models.network.room.RoomResponse
import live.adabe.fiesty.network.api.RoomAPI
import live.adabe.fiesty.util.Converter
import timber.log.Timber
import javax.inject.Inject

class RoomRepository @Inject constructor(private val roomAPI: RoomAPI) {

    suspend fun getRooms(buildingId: Int): List<Room> {
        return withContext(Dispatchers.IO) {
            return@withContext try {
                val response = roomAPI.getAllRooms(buildingId)
                response.map { roomResponse ->
                    Converter.convertRoomResponseToRoom(roomResponse)
                }
            } catch (t: Throwable) {
                Timber.e(t.message.toString())
                emptyList()
            }
        }
    }

    suspend fun createRoom(buildingId: Int, roomRequest: RoomRequest): Room?{
        return withContext(Dispatchers.IO){
            return@withContext try{
                Converter.convertRoomResponseToRoom(roomAPI.createRoom(buildingId, roomRequest))
            }catch (t: Throwable){
                Timber.e(t.message.toString())
                null
            }
        }
    }

    suspend fun deleteRoom(roomId: Int): Boolean{
        return withContext(Dispatchers.IO){
            return@withContext try{
                roomAPI.deleteRoom(roomId).isNotEmpty()
            }catch (t: Throwable){
                Timber.e(t.message.toString())
                false
            }
        }
    }

    suspend fun updateRoom(roomId: Int, buildingId: Int, roomRequest: RoomRequest): Room?{
        return withContext(Dispatchers.IO){
            return@withContext try{
                Converter.convertRoomResponseToRoom(roomAPI.updateRoom(roomId, buildingId, roomRequest))
            }catch (t: Throwable){
                Timber.e(t.message.toString())
                null
            }
        }
    }
}