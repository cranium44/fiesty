package live.adabe.fiesty.ui.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import dagger.hilt.android.AndroidEntryPoint
import live.adabe.fiesty.R
import live.adabe.fiesty.databinding.ProfileFragmentBinding
import live.adabe.fiesty.db.Preferences
import live.adabe.fiesty.ui.home.HomeViewModel
import live.adabe.fiesty.util.getDrawableResource
import timber.log.Timber
import javax.inject.Inject

@AndroidEntryPoint
class ProfileFragment : Fragment() {

    @Inject
    lateinit var preferences: Preferences

    lateinit var viewModel: ProfileViewModel
    lateinit var homeViewModel: HomeViewModel
    private lateinit var binding: ProfileFragmentBinding
    private var isEditable: Boolean = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = ProfileFragmentBinding.inflate(inflater, container, false)
        viewModel = ViewModelProvider(this).get(ProfileViewModel::class.java)
        homeViewModel = ViewModelProvider(requireActivity())[HomeViewModel::class.java]
        initViews()
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

    val getContent = registerForActivityResult(ActivityResultContracts.GetContent()) { uri ->
        Timber.d("Image Selected: $uri")
        preferences.setImageUri(uri)
    }
}