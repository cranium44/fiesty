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
import live.adabe.fiesty.ui.home.HomeViewModel
import live.adabe.fiesty.util.Converter
import live.adabe.fiesty.util.StringConstants
import live.adabe.fiesty.util.updateTime
import java.time.Duration
import java.time.LocalTime

@AndroidEntryPoint
class DeviceCreateFragment : Fragment() {

    private lateinit var binding: FragmentDeviceCreateBinding

    lateinit var deviceViewModel: DeviceViewModel
    lateinit var homeViewModel: HomeViewModel

    private lateinit var startTime: LocalTime
    private lateinit var stopTime: LocalTime
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
        obseveData()
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
                    startTime = Converter.getTime(hourOfDay, minute)
                }, 8, 0, false)
                timePickerDialog.show()
            }

            stopTimeLayout.setOnClickListener {
                val timePickerDialog = TimePickerDialog(requireActivity(), { _, hourOfDay, minute ->
                    stopTimeTv.text = updateTime(hourOfDay, minute)
                    stopTime = Converter.getTime(hourOfDay, minute)
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
                                startTime = startTime,
                                stopTime = stopTime,
                                duration = Duration.between(startTime, stopTime)
                            )
                            deviceViewModel.createDevice(deviceRequest = deviceRequest, roomId = id)
                        }
                        StringConstants.EDIT_MODE -> {
                            val deviceRequest = DeviceRequest(
                                name = binding.deviceNameInput.editText?.text.toString(),
                                rating = binding.deviceRatingInput.editText?.text.toString()
                                    .toDouble(),
                                startTime = startTime,
                                stopTime = stopTime,
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

    private fun obseveData() {
        deviceViewModel.run {
            devicesLiveData.observe(viewLifecycleOwner, {
                it?.let {
                    homeViewModel.run {
                        setBundle(null)
                        setScreen(StringConstants.ROOM_DETAILS_SCREEN)
                    }
                }
            })
        }
    }


}