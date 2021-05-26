package live.adabe.fiesty.ui.profile

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import live.adabe.fiesty.models.network.user.UserRequest
import live.adabe.fiesty.network.repository.UserRepository
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(private val userRepository: UserRepository): ViewModel() {
    val logoutSuccessLiveData = MutableLiveData(false)

    
    fun logout(){
        logoutSuccessLiveData.postValue(userRepository.logout())
    }
    
    fun updateUser(userRequest: UserRequest)
}