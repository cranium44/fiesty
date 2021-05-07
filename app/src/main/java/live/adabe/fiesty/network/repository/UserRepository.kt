package live.adabe.fiesty.network.repository

import android.util.Log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import live.adabe.fiesty.db.Preferences
import live.adabe.fiesty.models.network.user.UserRequest
import live.adabe.fiesty.models.network.user.UserResponse
import live.adabe.fiesty.network.api.UserAPI

class UserRepository(private val userAPI: UserAPI, private val preferences: Preferences) {

    companion object {
        const val TAG = "USER_REPOSITORY"
    }

    suspend fun createUser(userRequest: UserRequest) {
        withContext(Dispatchers.IO) {
            try {
                val response = userAPI.createUser(userRequest)
                response.value?.let { response_ ->
                    saveUserInfo(response_)
                }
            } catch (t: Throwable) {
                Log.e(TAG, t.message.toString())
            }
        }
    }

    private fun saveUserInfo(response_: UserResponse) {
        with(preferences) {
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
                }else{}
            }catch (t: Throwable){
                Log.e(TAG, t.message.toString())
            }
        }
    }

    suspend fun updateUser(userRequest: UserRequest){
        withContext(Dispatchers.IO){
            try {
                val id = preferences.getId()
                if (id != 0){
                    val response = userAPI.updateUser(id, userRequest)
                    response.value?.let { saveUserInfo(it) }
                }else{}
            }catch (t: Throwable){
                Log.e(TAG, t.message.toString())
            }
        }
    }


}