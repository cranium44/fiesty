package live.adabe.fiesty.network.repository

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import live.adabe.fiesty.models.network.device.DeviceRequest
import live.adabe.fiesty.models.network.device.DeviceResponse
import live.adabe.fiesty.network.api.DeviceAPI
import timber.log.Timber
import javax.inject.Inject

class DeviceRepository @Inject constructor(private val deviceAPI: DeviceAPI) {
    suspend fun createDevice(roomId: Int, deviceRequest: DeviceRequest): DeviceResponse? {
        return withContext(Dispatchers.IO) {
            return@withContext try {
                deviceAPI.createDevice(roomId, deviceRequest)
            } catch (t: Throwable) {
                Timber.e(t.message.toString())
                null
            }
        }
    }

    suspend fun getDevices(roomId: Int): List<DeviceResponse> {
        return withContext(Dispatchers.IO) {
            return@withContext try {
                deviceAPI.getAllDevices(roomId)
            } catch (t: Throwable) {
                Timber.e(t.message.toString())
                emptyList()
            }
        }
    }

    suspend fun getDevice(deviceId: Int): DeviceResponse? {
        return withContext(Dispatchers.IO) {
            return@withContext try {
                deviceAPI.getDevice(deviceId)
            } catch (t: Throwable) {
                Timber.e(t.message.toString())
                null
            }
        }
    }

    suspend fun updateDevice(
        roomId: Int,
        deviceId: Int,
        deviceRequest: DeviceRequest
    ): DeviceResponse? {
        return withContext(Dispatchers.IO) {
            return@withContext try {
                deviceAPI.updateDevice(roomId, deviceId, deviceRequest)
            } catch (t: Throwable) {
                Timber.e(t.message.toString())
                null
            }
        }
    }

    suspend fun deleteDevice(deviceId: Int): Boolean {
        return withContext(Dispatchers.IO) {
            return@withContext try {
                deviceAPI.deleteDevice(deviceId).isNotEmpty()
            } catch (t: Throwable) {
                Timber.e(t.message.toString())
                false
            }
        }
    }
}