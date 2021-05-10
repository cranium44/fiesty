package live.adabe.fiesty.ui.signup

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
class SignUpViewModel @Inject constructor(private val userRepository: UserRepository) : ViewModel() {

    val responseLiveData = MutableLiveData<UserResponse?>()
    fun createUser(userRequest: UserRequest){
        viewModelScope.launch{
            responseLiveData.postValue(userRepository.createUser(userRequest))
        }
    }
}