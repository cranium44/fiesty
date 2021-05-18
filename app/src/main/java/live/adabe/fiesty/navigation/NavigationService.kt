package live.adabe.fiesty.navigation

import android.content.Context
import android.os.Bundle
import ru.terrakok.cicerone.Screen

interface NavigationService {
    fun openHomeScreen()

    fun openSignUpScreen()

    fun openLoginScreen()

    fun openProfileScreen()

    fun openBuildingCreateScreen(bundle: Bundle?)

    fun openBuildingDetailsScreen(bundle: Bundle?)

    fun openRoomCreateScreen(bundle: Bundle?)

    fun openRoomDetailsScreen(bundle: Bundle?)

    fun openDeviceCreateScreen(bundle: Bundle?)

    fun openDeviceDetailsScreen(bundle: Bundle?)

    fun attachToActivity(context: Context)

    fun detachFromActivity()

    fun createChain(vararg screens: Screen)

}