package live.adabe.fiesty.ui.device

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import dagger.hilt.android.AndroidEntryPoint
import live.adabe.fiesty.R
import live.adabe.fiesty.databinding.FragmentDeviceDetailsBinding
import live.adabe.fiesty.navigation.NavigationService
import live.adabe.fiesty.ui.home.HomeViewModel
import live.adabe.fiesty.util.StringConstants
import live.adabe.fiesty.util.updateTime
import java.time.Instant
import java.time.ZoneOffset
import javax.inject.Inject

@AndroidEntryPoint
class DeviceDetailsFragment : Fragment() {

    @Inject
    lateinit var navigationService: NavigationService

    private lateinit var binding: FragmentDeviceDetailsBinding
    lateinit var deviceViewModel: DeviceViewModel
    lateinit var homeViewModel: HomeViewModel

    private val deviceId by lazy {
        arguments?.getInt(StringConstants.DEVICE_ID)
    }

    private val roomId by lazy {
        arguments?.getInt(StringConstants.ROOM_ID)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDeviceDetailsBinding.inflate(inflater, container, false)
        deviceViewModel = ViewModelProvider(requireActivity())[DeviceViewModel::class.java]
        initViews()
        return binding.root
    }

    private fun initViews() {
        binding.run {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                deviceId?.let { deviceViewModel.getSingleDevice(it) }
            }
            deviceViewModel.deviceGetLiveData.observe(viewLifecycleOwner, { device ->
                device?.let {
                    deviceNameDisplay.text = device.name
                    deviceRatingDisplay.text = device.rating.toString()
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                        startTimeDisplay.text = getTimeFromDate(device.startTime)
                        stopTimeDisplay.text = getTimeFromDate(device.stopTime)
                    }
                    energyUseDisplay.text = getString(R.string.energy_use_text, device.energyUse)
                }
            })
            editDeviceBtn.setOnClickListener {
                val bundle = Bundle().apply {
                    roomId?.let { it1 -> putInt(StringConstants.ROOM_ID, it1) }
                    deviceId?.let { id -> putInt(StringConstants.DEVICE_ID, id) }
                }
                navigationService.openDeviceCreateScreen(bundle)
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun getTimeFromDate(dateString: String): String {
        val instant = Instant.parse(dateString)
        return updateTime(
            instant.atZone(ZoneOffset.UTC).hour,
            instant.atZone(ZoneOffset.UTC).minute
        )
    }

}