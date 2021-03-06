package live.adabe.fiesty.navigation

import android.content.Context
import android.os.Bundle
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import live.adabe.fiesty.MainActivity
import live.adabe.fiesty.R
import ru.terrakok.cicerone.Cicerone
import ru.terrakok.cicerone.Router
import ru.terrakok.cicerone.Screen
import ru.terrakok.cicerone.android.support.SupportAppNavigator

class NavigationServiceImpl(cicerone: Cicerone<Router>) : NavigationService {

    private val router = cicerone.router
    private val navigationHolder = cicerone.navigatorHolder


    override fun openHomeScreen() {
        newRootScreen(Screens.HomeScreen())
    }

    override fun openSignUpScreen() {
        newRootScreen(Screens.SignUpScreen())
    }

    override fun openLoginScreen() {
        newRootScreen(Screens.LoginScreen())
    }

    override fun openProfileScreen() {
        navigateTo(Screens.ProfileScreen())
    }

    override fun openBuildingCreateScreen(bundle: Bundle?) {
        navigateTo(Screens.BuildingCreateScreen(bundle))
    }

    override fun openBuildingDetailsScreen(bundle: Bundle?) {
        navigateTo(Screens.BuildingDetailsScreen(bundle))
    }

    override fun openRoomCreateScreen(bundle: Bundle?) {
        navigateTo(Screens.RoomCreateScreen(bundle))
    }

    override fun openRoomDetailsScreen(bundle: Bundle?) {
        navigateTo(Screens.RoomDetailsScreen(bundle))
    }

    override fun openDeviceCreateScreen(bundle: Bundle?) {
        navigateTo(Screens.DeviceCreateScreen(bundle))
    }

    override fun openDeviceDetailsScreen(bundle: Bundle?) {
        navigateTo(Screens.DeviceDetailScreen(bundle))
    }

    override fun attachToActivity(context: Context) {
        context as MainActivity
        navigationHolder.setNavigator(SupportAppNavigator(context, R.id.nav_host_fragment))
    }

    override fun detachFromActivity() {
        navigationHolder.removeNavigator()
    }

    override fun createChain(vararg screens: Screen) {
        router.run { newChain(*screens) }
    }

    private fun navigateTo(screen: Screen) {
        CoroutineScope(Dispatchers.Main).launch {
            router.navigateTo(screen)
        }
    }

    private fun replaceScreen(screen: Screen) {
        CoroutineScope(Dispatchers.Main).launch {
            router.replaceScreen(screen)
        }
    }

    private fun newRootScreen(screen: Screen) {
        CoroutineScope(Dispatchers.Main).launch {
            router.newRootScreen(screen)
        }
    }
}