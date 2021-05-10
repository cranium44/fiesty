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
import live.adabe.fiesty.util.StringConstants
import timber.log.Timber

@AndroidEntryPoint
class BuildingCreateFragment : Fragment() {

    lateinit var binding: FragmentBuildingBinding
    lateinit var viewModel: HomeViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View{
        binding = FragmentBuildingBinding.inflate(inflater, container, false)
        viewModel = ViewModelProvider(requireActivity())[HomeViewModel::class.java]
        binding.saveButton.setOnClickListener {
            viewModel.run {
                arguments?.let {args->
                    if(args.getString(StringConstants.MODE) == StringConstants.EDIT_MODE){
                        updateBuilding(args.getInt(StringConstants.BUILDING_ID), getInput())
                    }else{
                        saveBuilding(getInput())
                    }
                }
                buildingResponse.observe(viewLifecycleOwner,{response ->
                    if (response != null){
                        setScreen(StringConstants.HOME_SCREEN)
                    }
                })
            }
        }
        initViews()
        return binding.root
    }

    override fun onResume() {
        super.onResume()
        initViews()
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

    private fun initViews() {
        arguments?.let{args->
            Timber.d(args.getString(StringConstants.MODE))
            if(args.getString(StringConstants.MODE) == StringConstants.EDIT_MODE){

                binding.run {
                    buildingName.editText?.setText(args.getString(StringConstants.BUILDING_NAME))
                    buildingAddress.editText?.setText(args.getString(StringConstants.BUILDING_ADDRESS))
                    buildingEnergyRate.editText?.setText(args.getInt(StringConstants.BUILDING_RATE).toString())
                }
            }else{
                binding.run {
                    buildingName.editText?.setText("")
                    buildingAddress.editText?.setText("")
                    buildingEnergyRate.editText?.setText("")
                }
            }
        }
    }
}