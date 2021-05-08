package live.adabe.fiesty.network.repository

import android.util.Log
import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import live.adabe.fiesty.db.Preferences
import live.adabe.fiesty.models.network.user.UserRequest
import live.adabe.fiesty.models.network.user.UserResponse
import live.adabe.fiesty.network.api.UserAPI
import timber.log.Timber
import javax.inject.Inject

class UserRepository @Inject constructor(
    private val userAPI: UserAPI,
    private val preferences: Preferences
) {

    companion object {
        const val TAG = "USER_REPOSITORY"
    }

    suspend fun createUser(userRequest: UserRequest, success: MutableLiveData<Boolean>) {
        withContext(Dispatchers.IO) {
            try {
                val response = userAPI.createUser(userRequest)
                saveUserInfo(response)
                success.postValue(true)
                Timber.d(response.id.toString())

            } catch (t: Throwable) {
                Timber.e(t.message.toString())
                success.postValue(false)
            }
        }
    }

    private fun saveUserInfo(response_: UserResponse) {
        preferences.run {
            setEmail(response_.email)
            setFirstName(response_.firstName)
            setLastName(response_.lastName)
            setId(response_.id)
            setIsLoggedIn(true)
        }
    }

    suspend fun getUser() {
        withContext(Dispatchers.IO) {
            try {
                val id = preferences.getId()
                if (id != 0) {
                    userAPI.getUser(id)
                } else {
                }
            } catch (t: Throwable) {
                Timber.e(t.message.toString())
            }
        }
    }

    suspend fun updateUser(userRequest: UserRequest) {
        withContext(Dispatchers.IO) {
            try {
                val id = preferences.getId()
                if (id != 0) {
                    val response = userAPI.updateUser(id, userRequest)
                    saveUserInfo(response)
                } else {
                }
            } catch (t: Throwable) {
                Timber.e(t.message.toString())
            }
        }
    }

    fun getUserID(): Int = preferences.getId()

    fun getUserFirstName(): String? = preferences.getFirstName()

}