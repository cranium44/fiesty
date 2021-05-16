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

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentRoomBinding.inflate(inflater, container, false)
        viewModel = ViewModelProvider(requireActivity())[HomeViewModel::class.java]
        initViews()
        return binding.root
    }

    private fun initViews() {
        binding.run {
            arguments?.let { args ->
                roomNameInput.editText?.setText(args.getString(StringConstants.ROOM_NAME))
                numberOfAppliancesInput.editText?.setText(args.getString(StringConstants.ROOM_DEVICE_COUNT))
            }?.run {
                roomNameInput.editText?.setText("")
                numberOfAppliancesInput.editText?.setText("")
            }
            saveRoomButton.setOnClickListener {
                arguments?.let { args ->
                    viewModel.createRoom(
                        args.getInt(StringConstants.BUILDING_ID),
                        getInput()
                    )
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
            roomResponse.observe(viewLifecycleOwner, { room ->
                room?.let {

                }
            })
        }
    }

}