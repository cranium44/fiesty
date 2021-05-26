package live.adabe.fiesty.ui.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import dagger.hilt.android.AndroidEntryPoint
import live.adabe.fiesty.R
import live.adabe.fiesty.databinding.ProfileFragmentBinding
import live.adabe.fiesty.db.Preferences
import live.adabe.fiesty.models.network.user.UserRequest
import live.adabe.fiesty.navigation.NavigationService
import live.adabe.fiesty.ui.home.HomeViewModel
import live.adabe.fiesty.util.getDrawableResource
import timber.log.Timber
import javax.inject.Inject

@AndroidEntryPoint
class ProfileFragment : Fragment() {

    @Inject
    lateinit var preferences: Preferences

    @Inject
    lateinit var navigationService: NavigationService

    lateinit var viewModel: ProfileViewModel
    lateinit var homeViewModel: HomeViewModel
    private lateinit var binding: ProfileFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = ProfileFragmentBinding.inflate(inflater, container, false)
        viewModel = ViewModelProvider(this).get(ProfileViewModel::class.java)
        homeViewModel = ViewModelProvider(requireActivity())[HomeViewModel::class.java]
        initViews()
        observeData()
        return binding.root
    }

    private fun initViews() {
        binding.apply {
            selectImageBtn.setOnClickListener {
                getContent.launch("image/*")
            }
            logoutBtn.setOnClickListener {
                viewModel.logout()
            }

            editProfileBtn.setOnClickListener {
                it as Button
                if (viewModel.isEditableLiveData.value == true) {
                    viewModel.run {
                        updateUser(getInput())
                        setIsEditable(false)
                    }
                    it.text = getString(R.string.edit)
                } else {
                    viewModel.setIsEditable(true)
                    it.text = getString(R.string.save)
                }
            }

            firstName.editText?.setText(preferences.getFirstName())
            lastName.editText?.setText(preferences.getLastName())
            email.editText?.setText(preferences.getEmail())
            phone.editText?.setText(preferences.getPhoneNumber())
            if (preferences.getImageUri().toString().isNotEmpty()) {
                profilePic.setImageURI(preferences.getImageUri())
            } else {
                profilePic.setImageURI(getDrawableResource(R.drawable.ic_user, requireContext()))
            }
        }
    }

    private fun observeData() {
        viewModel.run {
            logoutSuccessLiveData.observe(viewLifecycleOwner, { isSuccessful ->
                if (isSuccessful) navigationService.openLoginScreen()
            })

            updateUserResponseLiveData.observe(viewLifecycleOwner, { response ->
                response?.let { user ->
                    binding.run {
                        firstName.editText?.setText(user.firstName)
                        lastName.editText?.setText(user.lastName)
                        email.editText?.setText(user.email)
                        phone.editText?.setText(user.password)
                    }
                }
            })

            isEditableLiveData.observe(viewLifecycleOwner, { isEditable ->
                binding.run {
                    firstName.editText?.isEnabled = isEditable
                    lastName.editText?.isEnabled = isEditable
                    email.editText?.isEnabled = isEditable
                    phone.editText?.isEnabled = isEditable
                }
            })
        }
    }

    private fun getInput(): UserRequest {
        binding.run {
            return UserRequest(
                firstName = firstName.editText?.text.toString(),
                lastName = lastName.editText?.text.toString(),
                email = email.editText?.text.toString(),
                password = preferences.getPassword() ?: "",
                phoneNumber = phone.editText?.text.toString()
            )
        }
    }

    val getContent = registerForActivityResult(ActivityResultContracts.GetContent()) { uri ->
        Timber.d("Image Selected: $uri")
        preferences.setImageUri(uri)
    }
}