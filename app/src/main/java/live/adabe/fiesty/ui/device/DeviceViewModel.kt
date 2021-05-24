package live.adabe.fiesty.ui.device

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import live.adabe.fiesty.models.Device
import live.adabe.fiesty.models.network.device.DeviceRequest
import live.adabe.fiesty.network.repository.DeviceRepository
import live.adabe.fiesty.util.Converter
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class DeviceViewModel @Inject constructor(private val deviceRepository: DeviceRepository) :
    ViewModel() {

    val devicesLiveData = MutableLiveData<Device?>()
    val deviceListLiveData = MutableLiveData<List<Device>>()
    val deviceGetLiveData = MutableLiveData<Device?>()
    val deviceUpdateResponseLiveData = MutableLiveData<Device?>()
    val isDeviceDeleteSuccessful = MutableLiveData<Boolean>()

    @RequiresApi(Build.VERSION_CODES.O)
    fun createDevice(roomId: Int, deviceRequest: DeviceRequest) {
        viewModelScope.launch {
            devicesLiveData.postValue(deviceRepository.createDevice(roomId, deviceRequest)?.let {
                Converter.convertDeviceResponseToDevice(
                    it
                )
            })
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun getDevices(roomId: Int) {
        viewModelScope.launch {

            deviceListLiveData.postValue(
                deviceRepository.getDevices(roomId)
                    .map { response -> Converter.convertDeviceResponseToDevice(response) })
            Timber.d(
                deviceListLiveData.value?.get(0)?.name
            )
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun getSingleDevice(deviceId: Int) {
        viewModelScope.launch {
            deviceGetLiveData.postValue(
                deviceRepository.getDevice(deviceId)
                    ?.let { Converter.convertDeviceResponseToDevice(it) })
        }
    }


    @RequiresApi(Build.VERSION_CODES.O)
    fun updateDevice(
        roomId: Int,
        deviceId: Int,
        deviceRequest: DeviceRequest
    ) {
        viewModelScope.launch {
            deviceUpdateResponseLiveData.postValue(
                deviceRepository.updateDevice(roomId, deviceId, deviceRequest)?.let {
                    Converter.convertDeviceResponseToDevice(it)
                }
            )
        }
    }

    fun deleteDevice(deviceId: Int) {
        viewModelScope.launch {
            isDeviceDeleteSuccessful.postValue(deviceRepository.deleteDevice(deviceId))
        }
    }
}