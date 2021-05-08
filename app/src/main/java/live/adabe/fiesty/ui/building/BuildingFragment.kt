package live.adabe.fiesty.ui.building

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import dagger.hilt.android.AndroidEntryPoint
import live.adabe.fiesty.databinding.FragmentBuildingBinding
import live.adabe.fiesty.models.Building
import live.adabe.fiesty.ui.home.HomeViewModel

@AndroidEntryPoint
class BuildingFragment : Fragment() {

    lateinit var binding: FragmentBuildingBinding
    lateinit var viewModel: HomeViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View{
        binding = FragmentBuildingBinding.inflate(inflater, container, false)
        viewModel = ViewModelProvider(requireActivity())[HomeViewModel::class.java]
        binding.saveButton.setOnClickListener {
            viewModel.saveBuilding(getInput())
        }
        return binding.root
    }

    private fun getInput(): Building{
        binding.run{
            return Building(
                buildId = 0,
                name = buildingName.editText?.text.toString(),
                address = buildingAddress.editText?.text.toString(),
                energyRate = buildingEnergyRate.editText?.text.toString().toLong()
            )
        }
    }

}