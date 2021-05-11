package live.adabe.fiesty.navigation

import android.os.Bundle
import androidx.fragment.app.Fragment
import live.adabe.fiesty.ui.building.BuildingCreateFragment
import live.adabe.fiesty.ui.building.BuildingDetailsFragment
import live.adabe.fiesty.ui.home.HomeFragment
import live.adabe.fiesty.ui.login.LoginFragment
import live.adabe.fiesty.ui.profile.ProfileFragment
import live.adabe.fiesty.ui.room.RoomDetailsFragment
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

    class BuildingCreateScreen(private val bundle: Bundle?) : SupportAppScreen() {
        override fun getFragment(): Fragment {
            val fragment = BuildingCreateFragment()
            fragment.arguments = bundle
            return fragment
        }
    }

    class RoomCreateScreen(private val bundle: Bundle?) : SupportAppScreen() {
        override fun getFragment(): Fragment = RoomFragment().apply { arguments = bundle }
    }

    class BuildingDetailsScreen(private val bundle: Bundle?) : SupportAppScreen() {
        override fun getFragment(): Fragment {
            val fragment = BuildingDetailsFragment()
            fragment.arguments = bundle
            return fragment
        }
    }

    class RoomDetailsScreen(private val bundle: Bundle?) : SupportAppScreen() {
        override fun getFragment(): Fragment {
            val fragment = RoomDetailsFragment()
            fragment.arguments = bundle
            return fragment
        }
    }

}