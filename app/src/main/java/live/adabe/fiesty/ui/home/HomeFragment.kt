package live.adabe.fiesty.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import live.adabe.fiesty.databinding.FragmentHomeBinding
import live.adabe.fiesty.navigation.NavigationService
import live.adabe.fiesty.ui.adapters.BuildingAdapter
import live.adabe.fiesty.util.Converter
import timber.log.Timber
import javax.inject.Inject

@AndroidEntryPoint
class HomeFragment : Fragment() {

    @Inject
    lateinit var navigationService: NavigationService

    private lateinit var binding: FragmentHomeBinding
    private lateinit var buildingAdapter: BuildingAdapter
    lateinit var viewModel: HomeViewModel


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        viewModel = ViewModelProvider(requireActivity())[HomeViewModel::class.java]
        binding.apply {
            buildingRecycler.layoutManager = LinearLayoutManager(requireContext())
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.addBuilding.setOnClickListener {
            navigationService.openBuildingScreen(bundle = null)
            Timber.d("button clicked")
        }
        viewModel.run {
            buildings.observe(viewLifecycleOwner, { buildings_ ->
                Timber.d(buildings_.size.toString())
                if (buildings_ != null) {
                    buildingAdapter =
                        BuildingAdapter(Converter.getBuildingList(buildings_), navigationService)
                    binding.buildingRecycler.apply {
                        adapter = buildingAdapter
                        this.adapter?.notifyDataSetChanged()
                    }
                }
            })
        }
    }
}