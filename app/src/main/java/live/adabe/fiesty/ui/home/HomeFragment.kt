package live.adabe.fiesty.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import live.adabe.fiesty.R
import live.adabe.fiesty.databinding.FragmentHomeBinding
import live.adabe.fiesty.db.Preferences
import live.adabe.fiesty.models.Building
import live.adabe.fiesty.navigation.NavigationService
import live.adabe.fiesty.ui.adapters.BuildingAdapter
import live.adabe.fiesty.util.Converter
import live.adabe.fiesty.util.StringConstants
import timber.log.Timber
import javax.inject.Inject

@AndroidEntryPoint
class HomeFragment : Fragment() {

    @Inject
    lateinit var navigationService: NavigationService

    @Inject
    lateinit var preferences: Preferences

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
            welcomeText.text = getString(R.string.welcome_text, preferences.getFirstName(), preferences.getLastName())
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.addBuilding.setOnClickListener {
            navigationService.openBuildingCreateScreen(null)
        }
        viewModel.run {
            buildings.observe(viewLifecycleOwner, { buildings_ ->
                if (buildings_ != null) {
                    if (buildings_.isNotEmpty()) {
                        binding.noDataText.visibility = View.GONE
                        binding.buildingRecycler.visibility = View.VISIBLE
                        buildingAdapter =
                            BuildingAdapter(Converter.getBuildingList(buildings_), listener)
                        binding.buildingRecycler.apply {
                            adapter = buildingAdapter
                            this.adapter?.notifyDataSetChanged()
                        }
                    }
                } else {
                    binding.noDataText.visibility = View.VISIBLE
                    binding.buildingRecycler.visibility = View.INVISIBLE
                }
            })
            buildingDeleteSuccessLiveData.observe(viewLifecycleOwner, { result ->
                if (result) {
                    Toast.makeText(requireContext(), "Deleted Successfully!", Toast.LENGTH_SHORT)
                        .show()
                    getBuildings()
                }
            })
        }
    }

    override fun onResume() {
        super.onResume()
        viewModel.run {
            getBuildings()
        }
    }

    private val listener = object : BuildingAdapter.BuildingItemClickListener {
        override fun onClick(building: Building) {
            with(Bundle()) {
                putString(StringConstants.BUILDING_NAME, building.name)
                putInt(StringConstants.BUILDING_ID, building.buildId)
                putString(StringConstants.BUILDING_ADDRESS, building.address)
                putLong(StringConstants.BUILDING_RATE, building.energyRate)
                navigationService.openBuildingDetailsScreen(this@with)
            }
        }

        override fun onDelete(building: Building) {
            viewModel.run {
                deleteBuilding(building.buildId)
            }
        }
    }
}