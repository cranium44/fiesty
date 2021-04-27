package live.adabe.fiesty

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import dagger.hilt.android.AndroidEntryPoint
import live.adabe.fiesty.databinding.ActivityMainBinding
import live.adabe.fiesty.navigation.NavigationService
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var navigationService: NavigationService

    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        binding = ActivityMainBinding.inflate(layoutInflater)
        navigationService.openSignUpScreen()
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
}