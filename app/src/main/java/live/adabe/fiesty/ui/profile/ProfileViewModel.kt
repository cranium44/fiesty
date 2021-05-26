package live.adabe.fiesty.ui.profile

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import live.adabe.fiesty.models.network.user.UserRequest
import live.adabe.fiesty.models.network.user.UserResponse
import live.adabe.fiesty.network.repository.UserRepository
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(private val userRepository: UserRepository) :
    ViewModel() {
    val logoutSuccessLiveData = MutableLiveData(false)
    val updateUserResponseLiveData = MutableLiveData<UserResponse?>()
    val isEditableLiveData = MutableLiveData(false)

    fun logout() {
        logoutSuccessLiveData.postValue(userRepository.logout())
    }

    fun updateUser(userRequest: UserRequest) {
        viewModelScope.launch {
            updateUserResponseLiveData.postValue(userRepository.updateUser(userRequest))
        }
    }

    fun setIsEditable(boolean: Boolean) {
        isEditableLiveData.postValue(boolean)
    }
}