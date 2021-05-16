package live.adabe.fiesty.ui.device

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import live.adabe.fiesty.models.Device
import live.adabe.fiesty.network.repository.DeviceRepository
import javax.inject.Inject

@HiltViewModel
class DeviceViewModel @Inject constructor(private val deviceRepository: DeviceRepository) :
    ViewModel() {

    val devicesLiveData = MutableLiveData<Device>()
}