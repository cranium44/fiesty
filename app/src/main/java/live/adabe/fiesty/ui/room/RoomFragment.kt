package live.adabe.fiesty.ui.room

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import dagger.hilt.android.AndroidEntryPoint
import live.adabe.fiesty.databinding.FragmentRoomBinding
import live.adabe.fiesty.models.network.room.RoomRequest
import live.adabe.fiesty.ui.home.HomeViewModel
import live.adabe.fiesty.util.StringConstants

@AndroidEntryPoint
class RoomFragment : Fragment() {

    private lateinit var binding: FragmentRoomBinding
    lateinit var viewModel: HomeViewModel
    private lateinit var mode: String

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentRoomBinding.inflate(inflater, container, false)
        viewModel = ViewModelProvider(requireActivity())[HomeViewModel::class.java]
        arguments?.let {
            mode = it.getString(StringConstants.MODE).toString()
        }
        initViews()
        observeViewModel()
        return binding.root
    }

    private fun initViews() {
        binding.run {
            arguments?.let { args ->
                when(mode){
                    StringConstants.EDIT_MODE->{
                        roomNameInput.editText?.setText(args.getString(StringConstants.ROOM_NAME))
                        numberOfAppliancesInput.editText?.setText(args.getInt(StringConstants.ROOM_DEVICE_COUNT))
                    }
                    StringConstants.CREATE_MODE ->{
                        roomNameInput.editText?.setText("")
                        numberOfAppliancesInput.editText?.setText("")
                    }
                    else -> {
                        roomNameInput.editText?.setText("")
                        numberOfAppliancesInput.editText?.setText("")
                    }
                }

            }?.run {
                roomNameInput.editText?.setText("")
                numberOfAppliancesInput.editText?.setText("")
            }
            saveRoomButton.setOnClickListener {
                arguments?.let { args ->
                    when (mode) {
                        StringConstants.CREATE_MODE -> {
                            viewModel.createRoom(
                                args.getInt(StringConstants.BUILDING_ID),
                                getInput()
                            )
                        }
                        StringConstants.EDIT_MODE -> {
                            viewModel.updateRoom(
                                args.getInt(StringConstants.ROOM_ID),
                                args.getInt(StringConstants.BUILDING_ID),
                                getInput()
                            )
                        }
                    }
                }
            }
        }
    }

    private fun getInput(): RoomRequest {
        return binding.run {
            RoomRequest(
                name = roomNameInput.editText?.text.toString(),
                numberOfDevices = numberOfAppliancesInput.editText?.text.toString().toInt()
            )
        }
    }

    private fun observeViewModel() {
        viewModel.run {
            updateRoomResponse.observe(viewLifecycleOwner, { room ->
                room?.let {
                    with(Bundle()) {
                        putInt(StringConstants.ROOM_ID, it.rmId)
                        putInt(StringConstants.BUILDING_ID, it.buildingId)
                        putString(StringConstants.ROOM_NAME, it.name)
                        putInt(StringConstants.ROOM_DEVICE_COUNT, it.numberOfDevices)
                        setBundle(this@with)
                        setScreen(StringConstants.ROOM_DETAILS_SCREEN)
                    }
                }
            })
            createRoomResponse.observe(viewLifecycleOwner, {
                it?.let {
                    setScreen(StringConstants.BUILDING_DETAILS_SCREEN)
                }
            })
        }
    }

}