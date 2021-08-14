package live.adabe.fiesty.ui.home

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.RequiresApi
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
import live.adabe.fiesty.ui.add_new.AddNewDialog
import live.adabe.fiesty.util.*
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*
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
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        viewModel = ViewModelProvider(requireActivity())[HomeViewModel::class.java]
        return binding.root
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initViews()
        observeViewModels()
    }

    private fun observeViewModels() {
        viewModel.run {
            getUserEnergyUse()
            buildings.observe(viewLifecycleOwner, { buildings_ ->
                if (buildings_ != null) {
                    if (buildings_.isNotEmpty()) {
                        binding.noDataText.hide()
                        binding.buildingRecycler.show()
                        buildingAdapter =
                            BuildingAdapter(Converter.getBuildingList(buildings_), listener)
                        binding.buildingRecycler.apply {
                            adapter = buildingAdapter
                            this.adapter?.notifyDataSetChanged()
                        }
                    }
                } else {
                    binding.noDataText.show()
                    binding.buildingRecycler.hideLayout()
                }
            })
            buildingDeleteSuccessLiveData.observe(viewLifecycleOwner, { result ->
                if (result) {
                    Toast.makeText(requireContext(), "Deleted Successfully!", Toast.LENGTH_SHORT)
                        .show()
                    getBuildings()
                }
            })
            userEnergyUseLivedata.observe(viewLifecycleOwner, { userEnergyUse ->
                binding.totalEnergyUseDisplay.text =
                    getString(R.string.energy_use_text, userEnergyUse)
            })
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun initViews() {
        binding.apply {
            addBuilding.setOnClickListener {
//                with(Bundle()) {
//                    putString(StringConstants.MODE, StringConstants.CREATE_MODE)
//                    navigationService.openBuildingCreateScreen(this@with)
//                }
                AddNewDialog().show(requireActivity().supportFragmentManager, "dialog")
            }
            val date: LocalDate = LocalDate.now()
             dateDisplay.text = date.format(DateTimeFormatter.ofPattern("MMMM dd, yyyy"))

//            profileBtn.setOnClickListener { navigationService.openProfileScreen() }

            if (preferences.getImageUri().toString().isNotEmpty()) {
                displayPic.setImageURI(preferences.getImageUri())
            } else {
                displayPic.setImageURI(getDrawableResource(R.drawable.ic_user, requireContext()))
            }

            buildingRecycler.layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
            welcomeText.text = getString(
                R.string.welcome_text,
                preferences.getFirstName(),
                preferences.getLastName()
            )
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