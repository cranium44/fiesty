package live.adabe.fiesty.ui.building

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import live.adabe.fiesty.databinding.FragmentBuildingDetailsBinding
import live.adabe.fiesty.navigation.NavigationService
import live.adabe.fiesty.ui.adapters.RoomAdapter
import live.adabe.fiesty.ui.home.HomeViewModel
import live.adabe.fiesty.util.StringConstants
import javax.inject.Inject

@AndroidEntryPoint
class BuildingDetailsFragment : Fragment() {
    @Inject
    lateinit var navigationService: NavigationService

    lateinit var binding: FragmentBuildingDetailsBinding
    lateinit var viewModel: HomeViewModel
    lateinit var roomAdapter: RoomAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentBuildingDetailsBinding.inflate(inflater, container, false)
        viewModel = ViewModelProvider(requireActivity())[HomeViewModel::class.java]
        initViews()
        observeViewModel()
        return binding.root
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
                            setBundle(this@with)
                            setScreen(StringConstants.ROOM_SCREEN)
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
                        viewModel.run {
                            setBundle(this@with)
                            setScreen(StringConstants.BUILDING_CREATE_SCREEN)
                        }
                    }
                }
            }
        }
    }

    private fun observeViewModel() {
        arguments?.let { viewModel.getBuildingRooms(it.getInt(StringConstants.BUILDING_ID)) }
        viewModel.rooms.observe(viewLifecycleOwner, {
            if (it == null || it.isEmpty()) {
                binding.apply {
                    roomNoData.visibility = View.VISIBLE
                    roomRecycler.visibility = View.INVISIBLE
                }
            } else {
                roomAdapter = RoomAdapter(it, navigationService)
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
    }
}