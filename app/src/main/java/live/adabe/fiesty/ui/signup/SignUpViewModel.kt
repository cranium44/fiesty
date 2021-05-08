package live.adabe.fiesty.ui.signup

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import live.adabe.fiesty.models.network.user.UserRequest
import live.adabe.fiesty.network.repository.UserRepository
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(private val userRepository: UserRepository) : ViewModel() {

    val successLiveData = MutableLiveData<Boolean>(false)
    fun createUser(userRequest: UserRequest){
        viewModelScope.launch{
            userRepository.createUser(userRequest, successLiveData)
        }
    }
}