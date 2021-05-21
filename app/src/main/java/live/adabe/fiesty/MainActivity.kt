package live.adabe.fiesty

import android.os.Bundle
import android.os.PersistableBundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import dagger.hilt.android.AndroidEntryPoint
import live.adabe.fiesty.databinding.ActivityMainBinding
import live.adabe.fiesty.db.Preferences
import live.adabe.fiesty.navigation.NavigationService
import live.adabe.fiesty.ui.home.HomeViewModel
import live.adabe.fiesty.util.StringConstants
import timber.log.Timber
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var navigationService: NavigationService

    @Inject
    lateinit var preferences: Preferences

    lateinit var binding: ActivityMainBinding
    lateinit var viewModel: HomeViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        binding = ActivityMainBinding.inflate(layoutInflater)
        viewModel = ViewModelProvider(this)[HomeViewModel::class.java]
        Timber.d(preferences.getId().toString())
        viewModel.screen.observe(this, { screenName ->
            viewModel.bundle.observe(this, { bundle ->
                navigateToScreen(screenName, bundle)
            })

        })
        if (preferences.getId() == 0) {
            navigationService.openSignUpScreen()
        } else {
            navigationService.openHomeScreen()
        }
        setContentView(binding.root)
    }

    override fun onPause() {
        navigationService.detachFromActivity()
        super.onPause()
    }

    override fun onResumeFragments() {
        super.onResumeFragments()
        navigationService.attachToActivity(this)
    }

    private fun navigateToScreen(screenName: String, bundle: Bundle?) {
        when (screenName) {
            StringConstants.HOME_SCREEN -> navigationService.openHomeScreen()
            StringConstants.LOGIN_SCREEN -> navigationService.openLoginScreen()
            StringConstants.PROFILE_SCREEN -> navigationService.openProfileScreen()
            StringConstants.SIGNUP_SCREEN -> navigationService.openSignUpScreen()
            StringConstants.BUILDING_CREATE_SCREEN -> navigationService.openBuildingCreateScreen(
                bundle
            )
            StringConstants.BUILDING_DETAILS_SCREEN -> navigationService.openBuildingDetailsScreen(
                bundle
            )
            StringConstants.ROOM_CREATE_SCREEN -> navigationService.openRoomCreateScreen(bundle)
            StringConstants.ROOM_DETAILS_SCREEN -> navigationService.openRoomDetailsScreen(bundle)
            StringConstants.DEVICE_CREATE_SCREEN -> navigationService.openDeviceCreateScreen(bundle)
            StringConstants.DEVICE_DETAILS_SCREEN -> navigationService.openDeviceDetailsScreen(
                bundle
            )
        }
    }
}