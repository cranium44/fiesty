package live.adabe.fiesty.ui.home

import android.os.Bundle
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import live.adabe.fiesty.models.Building
import live.adabe.fiesty.models.Room
import live.adabe.fiesty.models.network.building.BuildingResponse
import live.adabe.fiesty.network.repository.BuildingRepository
import live.adabe.fiesty.network.repository.RoomRepository
import live.adabe.fiesty.util.Converter
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val buildingRepository: BuildingRepository,
    private val roomRepository: RoomRepository
) :
    ViewModel() {
    private var _buildings = MutableLiveData<List<BuildingResponse>>()
    val buildings: LiveData<List<BuildingResponse>> = _buildings
    var screen = MutableLiveData<String>()
    var bundle = MutableLiveData<Bundle>()
    private var _rooms = MutableLiveData<List<Room>>()
    val rooms: LiveData<List<Room>> = _rooms


    init {
        viewModelScope.launch {
            try {
                _buildings.postValue(buildingRepository.getAllUserBuildings())
            } catch (t: Throwable) {
                Timber.e(t.message.toString())
            }

        }
    }

    fun saveBuilding(building: Building) {
        viewModelScope.launch {
            buildingRepository.createBuilding(Converter.convertBuildingToBuildingRequest(building))
        }
    }

    fun setBundle(screenBundle: Bundle?) {
        bundle.postValue(screenBundle)
    }

    fun setScreen(screenName: String) {
        screen.postValue(screenName)
    }

    fun getBuildingRooms(buildingId: Int){
        viewModelScope.launch{
            _rooms.postValue(roomRepository.getRooms(buildingId))
        }
    }

}