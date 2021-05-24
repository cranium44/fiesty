package live.adabe.fiesty.ui.device

import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModelProvider
import dagger.hilt.android.AndroidEntryPoint
import live.adabe.fiesty.databinding.FragmentDeviceDetailsBinding
import live.adabe.fiesty.ui.adapters.DeviceAdapter
import live.adabe.fiesty.ui.home.HomeViewModel
import live.adabe.fiesty.util.StringConstants
import live.adabe.fiesty.util.updateTime
import java.time.Instant
import java.time.ZoneOffset

@AndroidEntryPoint
class DeviceDetailsFragment : Fragment() {

    private lateinit var binding: FragmentDeviceDetailsBinding
    private lateinit var deviceAdapter: DeviceAdapter
    lateinit var deviceViewModel: DeviceViewModel
    lateinit var homeViewModel: HomeViewModel

    private val deviceId by lazy {
        arguments?.getInt(StringConstants.DEVICE_ID)
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
            deviceViewModel.deviceGetLiveData.observe(viewLifecycleOwner, {device->
                device?.let {
                    deviceNameDisplay.text = device.name
                    deviceRatingDisplay.text = device.rating.toString()
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                        startTimeDisplay.text = getTimeFromDate(device.startTime)
                        stopTimeDisplay.text = getTimeFromDate(device.stopTime)
                    }
                    energyUseDisplay.text = device.energyUse.toString()
                }
            })
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun getTimeFromDate(dateString : String): String{
        val instant = Instant.parse(dateString)
        return  updateTime(instant.atZone(ZoneOffset.UTC).hour, instant.atZone(ZoneOffset.UTC).minute)
    }

}