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
        return binding.root
    }

    private fun initViews() {
        binding.roomRecycler.layoutManager = LinearLayoutManager(requireContext())
    }

    fun observeViewModel(){
        arguments?.let { viewModel.getBuildingRooms(it.getInt(StringConstants.BUILDING_ID)) }
        viewModel.rooms.observe(viewLifecycleOwner,{
            if (it == null || it.isEmpty() ){
                binding.apply {
                    roomNoData.visibility = View.VISIBLE
                    roomRecycler.visibility = View.INVISIBLE
                }
            }else{
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