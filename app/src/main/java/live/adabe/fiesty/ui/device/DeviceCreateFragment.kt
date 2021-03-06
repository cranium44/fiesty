package live.adabe.fiesty.ui.device

import android.app.TimePickerDialog
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import dagger.hilt.android.AndroidEntryPoint
import live.adabe.fiesty.databinding.FragmentDeviceCreateBinding
import live.adabe.fiesty.models.network.device.DeviceRequest
import live.adabe.fiesty.navigation.NavigationService
import live.adabe.fiesty.ui.home.HomeViewModel
import live.adabe.fiesty.util.StringConstants
import live.adabe.fiesty.util.updateTime
import java.time.*
import java.util.*
import javax.inject.Inject

@AndroidEntryPoint
class DeviceCreateFragment : Fragment() {

    @Inject
    lateinit var navigationService: NavigationService

    private lateinit var binding: FragmentDeviceCreateBinding

    lateinit var deviceViewModel: DeviceViewModel
    lateinit var homeViewModel: HomeViewModel

    private lateinit var startTime: LocalDateTime
    private lateinit var stopTime: LocalDateTime
    private var roomId: Int? = null
    private var mode = StringConstants.CREATE_MODE

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDeviceCreateBinding.inflate(inflater, container, false)
        deviceViewModel = ViewModelProvider(requireActivity())[DeviceViewModel::class.java]
        homeViewModel = ViewModelProvider(requireActivity())[HomeViewModel::class.java]
        arguments?.let {
            roomId = it.getInt(StringConstants.ROOM_ID)
        }
        observeData()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            initViews()
        }
        return binding.root
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun initViews() {
        binding.apply {
            startTimeLayout.setOnClickListener {
                val timePickerDialog = TimePickerDialog(requireActivity(), { _, hourOfDay, minute ->
                    startTimeTv.text = updateTime(hourOfDay, minute)
                    startTime =
                        LocalDateTime.now().withHour(hourOfDay).withMinute(minute).withSecond(0)
                }, 8, 0, false)
                timePickerDialog.show()
            }

            stopTimeLayout.setOnClickListener {
                val timePickerDialog = TimePickerDialog(requireActivity(), { _, hourOfDay, minute ->
                    stopTimeTv.text = updateTime(hourOfDay, minute)
                    stopTime =
                        LocalDateTime.now().withHour(hourOfDay).withMinute(minute).withSecond(0)
                }, 8, 0, false)
                timePickerDialog.show()
            }

            saveDeviceBtn.setOnClickListener {
                roomId?.let { id ->
                    when (mode) {
                        StringConstants.CREATE_MODE -> {
                            val deviceRequest = DeviceRequest(
                                name = binding.deviceNameInput.editText?.text.toString(),
                                rating = binding.deviceRatingInput.editText?.text.toString()
                                    .toDouble(),
                                startTime = getInstantFromLocalDateTime(startTime).toString(),
                                stopTime = getInstantFromLocalDateTime(stopTime).toString(),
                                duration = Duration.between(startTime, stopTime)
                            )
                            deviceViewModel.createDevice(deviceRequest = deviceRequest, roomId = id)
                        }
                        StringConstants.EDIT_MODE -> {
                            val deviceRequest = DeviceRequest(
                                name = binding.deviceNameInput.editText?.text.toString(),
                                rating = binding.deviceRatingInput.editText?.text.toString()
                                    .toDouble(),
                                startTime = getInstantFromLocalDateTime(startTime).toString(),
                                stopTime = getInstantFromLocalDateTime(stopTime).toString(),
                                duration = Duration.between(startTime, stopTime)
                            )
                            deviceViewModel.updateDevice(
                                deviceRequest = deviceRequest,
                                roomId = id,
                                deviceId = requireArguments().getInt(StringConstants.DEVICE_ID)
                            )
                        }
                    }
                }
            }

        }
    }

    private fun observeData() {
        deviceViewModel.run {
            devicesLiveData.observe(viewLifecycleOwner, {
                it?.let {
                    val bundle = Bundle().apply {
                        roomId?.let { it1 -> putInt(StringConstants.ROOM_ID, it1) }
                    }
                    devicesLiveData.postValue(null)
                    navigationService.openRoomDetailsScreen(bundle)
                }
            })
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun getInstantFromLocalDateTime(localDateTime: LocalDateTime): Instant {
        return localDateTime.toInstant(ZoneOffset.UTC)
    }


}