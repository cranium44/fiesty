package live.adabe.fiesty.db

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import javax.inject.Inject

class Preferences @Inject constructor(private val application: Application) {
    private var sharedPreferences: SharedPreferences

    companion object{
        private const val SHARED_PREF_NAME = "sharedPref"
        private const val FIRST_NAME = "first_name"
        private const val LAST_NAME = "last_name"
        private const val EMAIL = "email"
        private const val ID = "id"
        private const val LOGGED_IN_KEY = "logged_in"
    }

    init {
        sharedPreferences = application.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE)
    }

    fun setIsLoggedIn(boolean: Boolean){
        with(sharedPreferences.edit()){
            putBoolean(LOGGED_IN_KEY, boolean)
            apply()
        }
    }

    fun setFirstName(firstName: String){
        with(sharedPreferences.edit()){
            putString(FIRST_NAME, firstName)
            apply()
        }
    }

    fun setLastName(lastName: String){
        with(sharedPreferences.edit()){
            putString(LAST_NAME, lastName)
            apply()
        }
    }

    fun setEmail(email: String){
        with(sharedPreferences.edit()){
            putString(EMAIL, email)
            apply()
        }
    }

    fun setId(id: Int){
        with(sharedPreferences.edit()){
            putInt(ID, id)
            apply()
        }
    }

    fun isLoggedIn() = sharedPreferences.getBoolean(LOGGED_IN_KEY, false)

    fun getId() = sharedPreferences.getInt(ID, 0)

    fun getFirstName() = sharedPreferences.getString(FIRST_NAME, "")

    fun getLastName() = sharedPreferences.getString(LAST_NAME, "")

    fun getEmail() = sharedPreferences.getString(EMAIL, "")

    fun clearAll() {
        with(sharedPreferences.edit()){
            clear()
            commit()
        }
    }

}