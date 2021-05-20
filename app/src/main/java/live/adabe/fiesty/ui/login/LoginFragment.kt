package live.adabe.fiesty.ui.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import dagger.hilt.android.AndroidEntryPoint
import live.adabe.fiesty.databinding.LoginFragmentBinding
import live.adabe.fiesty.models.network.user.LoginRequest
import live.adabe.fiesty.ui.home.HomeViewModel
import live.adabe.fiesty.util.StringConstants
import timber.log.Timber

@AndroidEntryPoint
class LoginFragment : Fragment() {

    private lateinit var binding: LoginFragmentBinding

    lateinit var viewModel: LoginViewModel
    lateinit var homeViewModel: HomeViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = LoginFragmentBinding.inflate(inflater, container, false)
        viewModel = ViewModelProvider(requireActivity())[LoginViewModel::class.java]
        homeViewModel = ViewModelProvider(requireActivity())[HomeViewModel::class.java]
        initViews()
        viewModel.loginResponseLiveData.observe(viewLifecycleOwner,{response ->
            response?.let{
                homeViewModel.run{
                    setBundle(null)
                    setScreen(StringConstants.HOME_SCREEN)
                }
            }?:kotlin.run {
                Timber.d("Login Failed")
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
                homeViewModel.run {
                    setBundle(null)
                    setScreen(StringConstants.SIGNUP_SCREEN)
                }
            }

            loginButton.setOnClickListener {
                viewModel.loginUser(getInput())
            }
        }
    }

}