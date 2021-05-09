package live.adabe.fiesty

import android.os.Bundle
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
            navigateToScreen(screenName)
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

    private fun navigateToScreen(screenName: String) {
        when (screenName) {
            StringConstants.HOME_SCREEN -> navigationService.openHomeScreen()
            StringConstants.BUILDING_DETAILS_SCREEN -> navigationService.openBuildingScreen(null)
            StringConstants.PROFILE_SCREEN -> navigationService.openProfileScreen()
            StringConstants.SIGNUP_SCREEN -> navigationService.openSignUpScreen()
            StringConstants.ROOM_SCREEN -> navigationService.openRoomScreen(null)
        }
    }
}