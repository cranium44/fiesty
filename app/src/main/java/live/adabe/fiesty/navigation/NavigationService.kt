package live.adabe.fiesty.navigation

import android.content.Context
import android.os.Bundle
import ru.terrakok.cicerone.Screen

interface NavigationService {
    fun openHomeScreen()

    fun openSignUpScreen(bundle: Bundle? = null)

    fun attachToActivity(context: Context)

    fun detachFromActivity()

    fun createChain(vararg screens: Screen)

    fun openProfileScreen()

    fun openBuildingCreateScreen(bundle: Bundle?)

    fun openBuildingDetailsScreen(bundle: Bundle?)

    fun openRoomScreen(bundle: Bundle?)

}