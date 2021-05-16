package live.adabe.fiesty.ui.room

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import dagger.hilt.android.AndroidEntryPoint
import live.adabe.fiesty.R
import live.adabe.fiesty.databinding.FragmentRoomDetailsBinding
import live.adabe.fiesty.util.StringConstants

@AndroidEntryPoint
class RoomDetailsFragment : Fragment() {

    private lateinit var binding: FragmentRoomDetailsBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentRoomDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    private fun initViews() {
        binding.run {
            arguments?.let { args ->
                roomNameTv.text = args.getString(StringConstants.ROOM_NAME)
                roomBuildingNameTv.text = args.getString(StringConstants.BUILDING_NAME)
            }

        }
    }

}