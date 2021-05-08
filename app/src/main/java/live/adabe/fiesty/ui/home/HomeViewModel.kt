package live.adabe.fiesty.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import live.adabe.fiesty.models.Building
import live.adabe.fiesty.models.network.building.BuildingResponse
import live.adabe.fiesty.network.repository.BuildingRepository
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val buildingRepository: BuildingRepository) :
    ViewModel() {
    private var _buildings = MutableLiveData<List<BuildingResponse>>()
    val buildings: LiveData<List<BuildingResponse>> = _buildings


    init {
        viewModelScope.launch {
            _buildings.postValue(buildingRepository.getAllUserBuildings())
        }
    }

    fun saveBuilding(building: Building) {
        viewModelScope.launch{
            buildingRepository.saveBuildingToDb(building)
        }
    }

}