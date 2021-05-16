package live.adabe.fiesty.ui.room

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import live.adabe.fiesty.R
import live.adabe.fiesty.databinding.FragmentRoomDetailsBinding
import live.adabe.fiesty.models.Device
import live.adabe.fiesty.ui.adapters.BuildingAdapter
import live.adabe.fiesty.ui.adapters.DeviceAdapter
import live.adabe.fiesty.ui.home.HomeViewModel
import live.adabe.fiesty.util.StringConstants

@AndroidEntryPoint
class RoomDetailsFragment : Fragment() {

    private lateinit var binding: FragmentRoomDetailsBinding
    private lateinit var deviceAdapter: DeviceAdapter
    lateinit var viewModel: HomeViewModel


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentRoomDetailsBinding.inflate(inflater, container, false)
        viewModel = ViewModelProvider(requireActivity())[HomeViewModel::class.java]
        initViews()
        return binding.root
    }

    private fun initViews() {
        binding.run {
            arguments?.let { args ->
                roomNameTv.text = args.getString(StringConstants.ROOM_NAME)
                roomBuildingNameTv.text = args.getString(StringConstants.BUILDING_NAME)
            }
            deviceRv.layoutManager = LinearLayoutManager(requireContext())
        }
    }

    private val listener = object : DeviceAdapter.DeviceItemClickListener{
        override fun onItemClick(device: Device) {

        }

        override fun onItemDelete(device: Device) {

        }

    }

}