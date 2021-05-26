package live.adabe.fiesty.ui.building

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import live.adabe.fiesty.R
import live.adabe.fiesty.databinding.FragmentBuildingDetailsBinding
import live.adabe.fiesty.models.Room
import live.adabe.fiesty.navigation.NavigationService
import live.adabe.fiesty.ui.adapters.RoomAdapter
import live.adabe.fiesty.ui.home.HomeViewModel
import live.adabe.fiesty.util.StringConstants
import timber.log.Timber
import javax.inject.Inject

@AndroidEntryPoint
class BuildingDetailsFragment : Fragment() {
    @Inject
    lateinit var navigationService: NavigationService

    lateinit var binding: FragmentBuildingDetailsBinding
    lateinit var viewModel: HomeViewModel
    private lateinit var roomAdapter: RoomAdapter
    var id: Int? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentBuildingDetailsBinding.inflate(inflater, container, false)
        viewModel = ViewModelProvider(requireActivity())[HomeViewModel::class.java]
        initViews()
        observeViewModel()
        arguments?.let {
            id = it.getInt(StringConstants.BUILDING_ID)
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        savedInstanceState?.let {
            Timber.d("STATE RES")
            binding.apply {
                buildingNameTv.text = it.getString(StringConstants.BUILDING_NAME)
                buildingAddressTv.text = it.getString(StringConstants.BUILDING_ADDRESS)
                energyRateTv.text = it.getLong(StringConstants.BUILDING_RATE).toString()
            }
        }
    }

    override fun onResume() {
        super.onResume()
        id?.let {
            viewModel.run {
                getBuilding(it)
                buildingGetLiveData.observe(viewLifecycleOwner, { response ->
                    binding.apply {
                        buildingNameTv.text = response?.name
                        buildingAddressTv.text = response?.address
                        energyRateTv.text = response?.energyRate.toString()
                    }
                })
            }
        }
    }

    private fun initViews() {
        binding.apply {
            roomRecycler.layoutManager = LinearLayoutManager(requireContext())
            arguments?.let { args ->
                buildingNameTv.text = args.getString(StringConstants.BUILDING_NAME)
                buildingAddressTv.text = args.getString(StringConstants.BUILDING_ADDRESS)
                energyRateTv.text = args.getLong(StringConstants.BUILDING_RATE).toString()
            }
            addRoomBtn.setOnClickListener {
                arguments?.let { args ->
                    viewModel.apply {
                        with(Bundle()) {
                            putInt(
                                StringConstants.BUILDING_ID,
                                args.getInt(StringConstants.BUILDING_ID)
                            )
                            putString(StringConstants.MODE, StringConstants.CREATE_MODE)
                            navigationService.openRoomCreateScreen(this@with)
                        }
                    }
                }
            }
            editBuildingBtn.setOnClickListener {
                arguments?.let { args ->
                    with(Bundle()) {
                        putInt(
                            StringConstants.BUILDING_ID,
                            args.getInt(StringConstants.BUILDING_ID)
                        )
                        putString(
                            StringConstants.BUILDING_NAME,
                            args.getString(StringConstants.BUILDING_NAME)
                        )
                        putString(
                            StringConstants.BUILDING_ADDRESS,
                            args.getString(StringConstants.BUILDING_ADDRESS)
                        )
                        putString(
                            StringConstants.MODE,
                            StringConstants.EDIT_MODE
                        )
                        putLong(
                            StringConstants.BUILDING_RATE,
                            args.getLong(StringConstants.BUILDING_RATE)
                        )
                        navigationService.openBuildingCreateScreen(this@with)
                    }
                }
            }
        }
    }

    private fun observeViewModel() {
        arguments?.let {
            viewModel.getBuildingRooms(it.getInt(StringConstants.BUILDING_ID))
            viewModel.getBuildingEnergyUse(it.getInt(StringConstants.BUILDING_ID))
        }
        viewModel.run{
            rooms.observe(viewLifecycleOwner, {
                if (it == null || it.isEmpty()) {
                    binding.apply {
                        roomNoData.visibility = View.VISIBLE
                        roomRecycler.visibility = View.INVISIBLE
                    }
                } else {
                    roomAdapter = RoomAdapter(it, listener)
                    binding.apply {
                        roomNoData.visibility = View.GONE
                        roomRecycler.visibility = View.VISIBLE
                    }
                    binding.roomRecycler.apply {
                        adapter = roomAdapter
                        this.adapter?.notifyDataSetChanged()
                    }
                }
            })
            buildingEnergyUseLiveData.observe(viewLifecycleOwner, { energyUse ->
                binding.buildingEnergyUseTv.text = getString(R.string.energy_use_text, energyUse)
            })
        }
    }

    private val listener = object : RoomAdapter.RoomItemClickListener {
        override fun onItemClick(room: Room) {
            with(Bundle()) {
                putInt(StringConstants.ROOM_ID, room.rmId)
                arguments?.let {
                    putInt(
                        StringConstants.BUILDING_ID,
                        it.getInt(StringConstants.BUILDING_ID)
                    )
                    putString(
                        StringConstants.BUILDING_NAME,
                        it.getString(StringConstants.BUILDING_NAME)
                    )
                }
                putString(StringConstants.ROOM_NAME, room.name)
                putInt(StringConstants.ROOM_DEVICE_COUNT, room.numberOfDevices)
                navigationService.openRoomDetailsScreen(this@with)
            }
        }

        override fun onItemDelete(room: Room) {
            viewModel.run {
                deleteRoom(room.rmId)
            }
        }

    }
}