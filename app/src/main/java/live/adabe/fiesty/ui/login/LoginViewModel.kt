package live.adabe.fiesty.ui.login

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import live.adabe.fiesty.models.network.user.LoginRequest
import live.adabe.fiesty.models.network.user.UserResponse
import live.adabe.fiesty.network.repository.UserRepository
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(private val userRepository: UserRepository) : ViewModel() {

    val loginResponseLiveData = MutableLiveData<UserResponse?>()
    fun loginUser(loginRequest: LoginRequest) {
        viewModelScope.launch {
            loginResponseLiveData.postValue(userRepository.loginUser(loginRequest))
        }
    }
}