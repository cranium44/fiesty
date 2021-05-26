package live.adabe.fiesty.network.repository

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import live.adabe.fiesty.db.Preferences
import live.adabe.fiesty.models.network.user.LoginRequest
import live.adabe.fiesty.models.network.user.UserRequest
import live.adabe.fiesty.models.network.user.UserResponse
import live.adabe.fiesty.network.api.EnergyAPI
import live.adabe.fiesty.network.api.UserAPI
import timber.log.Timber
import javax.inject.Inject

class UserRepository @Inject constructor(
    private val userAPI: UserAPI,
    private val energyAPI: EnergyAPI,
    private val preferences: Preferences
) {

    suspend fun createUser(userRequest: UserRequest): UserResponse? {
        return withContext(Dispatchers.IO) {
            return@withContext try {
                val response = userAPI.createUser(userRequest)
                saveUserInfo(response)
                response
            } catch (t: Throwable) {
                Timber.e(t.message.toString())
                null
            }
        }
    }

    suspend fun loginUser(loginRequest: LoginRequest): UserResponse? {
        return withContext(Dispatchers.IO) {
            return@withContext try {
                val response = userAPI.loginUser(loginRequest)
                saveUserInfo(response)
                response
            } catch (t: Throwable) {
                Timber.e(t.message.toString())
                null
            }
        }
    }

    private fun saveUserInfo(response_: UserResponse) {
        preferences.run {
            setEmail(response_.email)
            setFirstName(response_.firstName)
            setLastName(response_.lastName)
            setPhoneNumber(response_.phoneNumber)
            setId(response_.id)
            setIsLoggedIn(true)
        }
    }

    suspend fun getUserEnergyUse(): Double {
        return withContext(Dispatchers.IO) {
            return@withContext try {
                val response = energyAPI.getUserEnergyOutput(preferences.getId())
                if (response.isSuccessful && response.body() != null) {
                    response.body()
                } else {
                    0.0
                }
            } catch (t: Throwable) {
                0.0
            }!!
        }
    }

    fun logout(): Boolean {
        preferences.clearAll()
        preferences.setIsLoggedIn(false)
        return true
    }

    suspend fun updateUser(userRequest: UserRequest): UserResponse? {
        return withContext(Dispatchers.IO) {
            return@withContext try {
                val response = userAPI.updateUser(preferences.getId(), userRequest)
                saveUserInfo(response)
                response
            } catch (t: Throwable) {
                Timber.e(t.message.toString())
                null
            }
        }
    }
}