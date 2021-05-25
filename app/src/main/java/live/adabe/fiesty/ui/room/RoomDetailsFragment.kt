package live.adabe.fiesty.ui.room

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import live.adabe.fiesty.databinding.FragmentRoomDetailsBinding
import live.adabe.fiesty.models.Device
import live.adabe.fiesty.navigation.NavigationService
import live.adabe.fiesty.ui.adapters.DeviceAdapter
import live.adabe.fiesty.ui.device.DeviceViewModel
import live.adabe.fiesty.ui.home.HomeViewModel
import live.adabe.fiesty.util.StringConstants
import javax.inject.Inject

@AndroidEntryPoint
class RoomDetailsFragment : Fragment() {

    @Inject
    lateinit var navigationService: NavigationService

    private lateinit var binding: FragmentRoomDetailsBinding
    private lateinit var deviceAdapter: DeviceAdapter
    lateinit var viewModel: HomeViewModel
    lateinit var deviceViewModel: DeviceViewModel


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentRoomDetailsBinding.inflate(inflater, container, false)
        viewModel = ViewModelProvider(requireActivity())[HomeViewModel::class.java]
        deviceViewModel = ViewModelProvider(requireActivity())[DeviceViewModel::class.java]
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            initViews()
        }
        observeData()
        return binding.root
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun initViews() {
        binding.run {
            arguments?.let { args ->
                roomNameTv.text = args.getString(StringConstants.ROOM_NAME) ?: ""
                deviceViewModel.getDevices(args.getInt(StringConstants.ROOM_ID))
                viewModel.getRoomEnergyUse(args.getInt(StringConstants.ROOM_ID), args.getInt(StringConstants.BUILDING_ID))
            }
            deviceRv.layoutManager = LinearLayoutManager(requireContext())
            addDeviceBtn.setOnClickListener {
                with(Bundle()){
                    arguments?.let { args->
                        putInt( StringConstants.ROOM_ID,
                            args.getInt(StringConstants.ROOM_ID)
                        )
                        putString(StringConstants.MODE, StringConstants.CREATE_MODE)
                        navigationService.openDeviceCreateScreen(this)
                    }
                }
            }
        }
    }

    private fun observeData() {
        deviceViewModel.run {
            deviceListLiveData.observe(viewLifecycleOwner, { devices ->
                if (devices.isNotEmpty()) {
                    binding.deviceRv.visibility = View.VISIBLE
                    binding.noDeviceData.visibility = View.INVISIBLE
                    deviceAdapter = DeviceAdapter(devices, listener)
                    binding.deviceRv.run {
                        adapter = deviceAdapter
                        adapter?.notifyDataSetChanged()
                    }
                }else{
                    binding.deviceRv.visibility = View.INVISIBLE
                    binding.noDeviceData.visibility = View.VISIBLE
                }
            })
        }
        viewModel.roomEnergyUseLiveData.observe(viewLifecycleOwner, { energyUse ->
            binding.roomEnergyUseTv.text = energyUse.toString()
        })
    }

    private val listener = object : DeviceAdapter.DeviceItemClickListener {
        override fun onItemClick(device: Device) {
            with(Bundle()) {
                putInt(StringConstants.DEVICE_ID, device.deviceId)
                arguments?.getInt(StringConstants.ROOM_ID)?.let {
                    putInt(StringConstants.ROOM_ID,
                        it
                    )
                }
                navigationService.openDeviceDetailsScreen(this@with)
            }
        }

        override fun onItemDelete(device: Device) {
            deviceViewModel.deleteDevice(device.deviceId)
        }

    }

}