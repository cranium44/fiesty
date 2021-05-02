package live.adabe.fiesty.ui.building

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import live.adabe.fiesty.databinding.FragmentBuildingBinding

class BuildingFragment : Fragment() {

    lateinit var binding: FragmentBuildingBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View{
        binding = FragmentBuildingBinding.inflate(inflater, container, false)
        return binding.root
    }

}