package live.adabe.fiesty.network.repository

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import live.adabe.fiesty.db.BuildingDAO
import live.adabe.fiesty.db.RoomDAO
import live.adabe.fiesty.models.Room
import live.adabe.fiesty.models.network.room.RoomRequest
import live.adabe.fiesty.network.api.EnergyAPI
import live.adabe.fiesty.network.api.RoomAPI
import live.adabe.fiesty.util.Converter
import timber.log.Timber
import javax.inject.Inject

class RoomRepository @Inject constructor(
    private val roomAPI: RoomAPI,
    private val energyAPI: EnergyAPI,
    private val roomDAO: RoomDAO,
    private val buildingDAO: BuildingDAO
) {

    init {
        CoroutineScope(Dispatchers.Default).launch {
            updateRoomsInDb()
        }
    }

    private suspend fun updateRoomsInDb() {
        withContext(Dispatchers.IO) {
            try {
                val buildings = buildingDAO.getAllBuildings()
                buildings.forEach { building ->
                    val rooms = roomAPI.getAllRooms(building.buildId).map {
                        Room(
                            rmId = it.rmId,
                            buildingId = building.buildId,
                            name = it.name,
                            numberOfDevices = it.numberOfDevices
                        )
                    }
                    roomDAO.addRooms(*rooms.toTypedArray())
                }
            } catch (t: Throwable) {
                Timber.d(t.message)
            }
        }
    }

    suspend fun getRooms(buildingId: Int): List<Room> {
        return withContext(Dispatchers.IO) {
            try {
                roomDAO.getAllRoomsByBuilding(buildingId)
            } catch (t: Throwable) {
                Timber.e(t.message.toString())
                emptyList()
            }
        }
    }

    suspend fun getRoomEnergyUse(roomId: Int, buildingId: Int): Double {
        return withContext(Dispatchers.IO) {
            try {
                energyAPI.getRoomEnergyOutput(roomId, buildingId)
            } catch (t: Throwable) {
                0.0
            }
        }
    }

    suspend fun getRoom(roomId: Int): Room? {
        return withContext(Dispatchers.IO) {
            return@withContext try {
                Converter.convertRoomResponseToRoom(roomAPI.getRoom(roomId))
            } catch (t: Throwable) {
                Timber.e(t.message.toString())
                null
            }
        }
    }

    suspend fun createRoom(buildingId: Int, roomRequest: RoomRequest): Room? {
        return withContext(Dispatchers.IO) {
            return@withContext try {
                val response =
                    Converter.convertRoomResponseToRoom(roomAPI.createRoom(buildingId, roomRequest))
                roomDAO.addRoom(response)
                response
            } catch (t: Throwable) {
                Timber.e(t.message.toString())
                null
            }
        }
    }

    suspend fun deleteRoom(roomId: Int): Boolean {
        return withContext(Dispatchers.IO) {
            return@withContext try {
                val response = roomAPI.deleteRoom(roomId).isSuccessful
                roomDAO.deleteRoom(roomDAO.getRoomById(roomId))
                response
            } catch (t: Throwable) {
                Timber.e(t.message.toString())
                false
            }
        }
    }

    suspend fun updateRoom(roomId: Int, buildingId: Int, roomRequest: RoomRequest): Room? {
        return withContext(Dispatchers.IO) {
            try {
                val response = Converter.convertRoomResponseToRoom(
                    roomAPI.updateRoom(
                        roomId,
                        buildingId,
                        roomRequest
                    )
                )
                roomDAO.updateRoom(response)
                response
            } catch (t: Throwable) {
                Timber.e(t.message.toString())
                null
            }
        }
    }
}