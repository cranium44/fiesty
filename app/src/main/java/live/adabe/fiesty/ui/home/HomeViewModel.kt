package live.adabe.fiesty.ui.home

import androidx.lifecycle.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import live.adabe.fiesty.models.Building
import live.adabe.fiesty.models.Room
import live.adabe.fiesty.models.network.building.BuildingResponse
import live.adabe.fiesty.models.network.room.RoomRequest
import live.adabe.fiesty.network.repository.BuildingRepository
import live.adabe.fiesty.network.repository.RoomRepository
import live.adabe.fiesty.network.repository.UserRepository
import live.adabe.fiesty.util.Converter
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val buildingRepository: BuildingRepository,
    private val roomRepository: RoomRepository,
    private val userRepository: UserRepository
) : ViewModel() {

    val buildings: LiveData<List<Building>> = liveData {
        val data = buildingRepository.getAllUserBuildings()
        emit(data)
    }

    val userEnergyUseLivedata = liveData{
        emit(userRepository.getUserEnergyUse())
    }
    val roomEnergyUseLiveData = MutableLiveData<Double>()
    val buildingEnergyUseLiveData = MutableLiveData<Double>()

    private var _rooms = MutableLiveData<List<Room>>()
    val rooms: LiveData<List<Room>> = _rooms

    var buildingResponse = MutableLiveData<BuildingResponse?>()
    var buildingDeleteSuccessLiveData = MutableLiveData<Boolean>()
    var buildingGetLiveData = MutableLiveData<Building>()

    var createRoomResponse = MutableLiveData<Room?>()
    var updateRoomResponse = MutableLiveData<Room?>()
    var deleteRoomLiveData = MutableLiveData<Boolean>()
    val getRoomLiveData = MutableLiveData<Room?>()

    fun saveBuilding(building: Building) {
        viewModelScope.launch {
            buildingResponse.postValue(
                buildingRepository.createBuilding(
                    Converter.convertBuildingToBuildingRequest(
                        building
                    )
                )
            )
        }
    }

    fun getBuildingRooms(buildingId: Int) {
        viewModelScope.launch {
            _rooms.postValue(roomRepository.getRooms(buildingId))
        }
    }


    fun getBuilding(buildingId: Int) {
        viewModelScope.launch {
            buildingGetLiveData.postValue(buildingRepository.getBuilding(buildingId))
        }
    }

    fun updateBuilding(buildingId: Int, building: Building) {
        viewModelScope.launch {
            buildingResponse.postValue(
                buildingRepository.updateBuilding(
                    buildingId,
                    Converter.convertBuildingToBuildingRequest(building)
                )
            )
        }
    }

    fun deleteBuilding(buildingId: Int) {
        viewModelScope.launch {
            buildingDeleteSuccessLiveData.postValue(buildingRepository.deleteBuilding(buildingId))
        }
    }

    fun createRoom(buildingId: Int, roomRequest: RoomRequest) {
        viewModelScope.launch {
            createRoomResponse.postValue(roomRepository.createRoom(buildingId, roomRequest))
        }
    }

    fun getRoom(roomId: Int) {
        viewModelScope.launch {
            getRoomLiveData.postValue(roomRepository.getRoom(roomId))
        }
    }

    fun deleteRoom(roomId: Int) {
        viewModelScope.launch {
            deleteRoomLiveData.postValue(roomRepository.deleteRoom(roomId))
        }
    }

    fun updateRoom(roomId: Int, buildingId: Int, roomRequest: RoomRequest) {
        viewModelScope.launch {
            updateRoomResponse.postValue(roomRepository.updateRoom(roomId, buildingId, roomRequest))
        }
    }


    fun getBuildingEnergyUse(buildingId: Int) {
        viewModelScope.launch {
            buildingEnergyUseLiveData.postValue(buildingRepository.getBuildingEnergyUse(buildingId))
        }
    }

    fun getRoomEnergyUse(roomId: Int, buildingId: Int) {
        viewModelScope.launch {
            roomEnergyUseLiveData.postValue(roomRepository.getRoomEnergyUse(roomId, buildingId))
        }
    }
}