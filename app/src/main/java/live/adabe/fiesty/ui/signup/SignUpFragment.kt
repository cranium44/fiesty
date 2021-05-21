package live.adabe.fiesty.ui.signup

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import dagger.hilt.android.AndroidEntryPoint
import live.adabe.fiesty.databinding.SignUpFragmentBinding
import live.adabe.fiesty.models.network.user.UserRequest
import live.adabe.fiesty.navigation.NavigationService
import live.adabe.fiesty.ui.home.HomeViewModel
import live.adabe.fiesty.util.StringConstants
import timber.log.Timber
import javax.inject.Inject

@AndroidEntryPoint
class SignUpFragment : Fragment() {

    @Inject
    lateinit var navigationService: NavigationService
    private lateinit var binding: SignUpFragmentBinding
    lateinit var viewModel: SignUpViewModel
    lateinit var homeViewModel: HomeViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = SignUpFragmentBinding.inflate(inflater, container, false)
        viewModel = ViewModelProvider(requireActivity())[SignUpViewModel::class.java]
        homeViewModel = ViewModelProvider(requireActivity())[HomeViewModel::class.java]
        observeViewModel()
        binding.button.setOnClickListener {
            it as Button
            it.text = "Loading"
            viewModel.createUser(getInput())
        }
        return binding.root
    }

    private fun observeViewModel() {
        viewModel.run {
            responseLiveData.observe(requireActivity(), { userResponse ->
                if (userResponse != null) {
                    Timber.d("response successful")
                    homeViewModel.run {
                        setBundle(null)
                        setScreen(StringConstants.HOME_SCREEN)
                    }
                } else {
                    Toast.makeText(
                        requireContext(),
                        "Something went wrong, try again.",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            })
        }
    }

    private fun getInput(): UserRequest {
        binding.run {
            return UserRequest(
                firstName = firstNameInput.editText?.text.toString(),
                lastName = lastNameInput.editText?.text.toString(),
                email = emailInput.editText?.text.toString(),
                password = passwordInput.editText?.text.toString(),
                phoneNumber = phoneIput.editText?.text.toString(),
            )
        }
    }

}