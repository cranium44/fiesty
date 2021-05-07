package live.adabe.fiesty.navigation

import android.os.Bundle
import androidx.fragment.app.Fragment
import live.adabe.fiesty.ui.building.BuildingFragment
import live.adabe.fiesty.ui.home.HomeFragment
import live.adabe.fiesty.ui.login.LoginFragment
import live.adabe.fiesty.ui.profile.ProfileFragment
import live.adabe.fiesty.ui.room.RoomFragment
import live.adabe.fiesty.ui.signup.SignUpFragment
import ru.terrakok.cicerone.android.support.SupportAppScreen

class Screens {
    class HomeScreen : SupportAppScreen() {
        override fun getFragment(): Fragment {
            return HomeFragment()
        }
    }

    class LoginScreen(private val bundle: Bundle?) : SupportAppScreen() {
        override fun getFragment(): Fragment {
            val fragment = LoginFragment()
            fragment.arguments = bundle
            return fragment
        }
    }

    class SignUpScreen() : SupportAppScreen() {
        override fun getFragment(): Fragment {
            return SignUpFragment()
        }
    }

    class ProfileScreen() : SupportAppScreen() {
        override fun getFragment(): Fragment {
            return ProfileFragment()
        }
    }

    class BuildingDetailsScreen(private val bundle: Bundle?) : SupportAppScreen() {
        override fun getFragment(): Fragment {
            return BuildingFragment().apply { arguments = bundle }
        }
    }

    class RoomScreen(private val bundle: Bundle?) : SupportAppScreen() {
        override fun getFragment(): Fragment = RoomFragment().apply { arguments = bundle }
    }
}