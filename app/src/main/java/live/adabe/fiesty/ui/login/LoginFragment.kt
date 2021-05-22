package live.adabe.fiesty.ui.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import dagger.hilt.android.AndroidEntryPoint
import live.adabe.fiesty.databinding.LoginFragmentBinding
import live.adabe.fiesty.models.network.user.LoginRequest
import live.adabe.fiesty.navigation.NavigationService
import javax.inject.Inject

@AndroidEntryPoint
class LoginFragment : Fragment() {

    @Inject
    lateinit var navigationService: NavigationService

    private lateinit var binding: LoginFragmentBinding

    lateinit var viewModel: LoginViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = LoginFragmentBinding.inflate(inflater, container, false)
        viewModel = ViewModelProvider(requireActivity())[LoginViewModel::class.java]
        initViews()
        viewModel.loginResponseLiveData.observe(viewLifecycleOwner, { response ->
            response?.let {
                navigationService.openHomeScreen()
            }?.run {
                Toast.makeText(
                    requireContext(),
                    "Oops! Something went wrong. Try again",
                    Toast.LENGTH_SHORT
                ).show()
            }
        })
        return binding.root
    }

    private fun getInput(): LoginRequest {
        return binding.run {
            LoginRequest(
                email = emailInput.editText?.text.toString(),
                password = passwordInput.editText?.text.toString()
            )
        }
    }

    private fun initViews() {
        binding.run {
            goToSignup.setOnClickListener {
                navigationService.openSignUpScreen()
            }

            loginButton.setOnClickListener {
                viewModel.loginUser(getInput())
            }
        }
    }

}