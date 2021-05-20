package live.adabe.fiesty.ui.device

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import dagger.hilt.android.AndroidEntryPoint
import live.adabe.fiesty.R
import live.adabe.fiesty.databinding.FragmentDeviceDetailsBinding
import live.adabe.fiesty.models.Device
import live.adabe.fiesty.ui.adapters.DeviceAdapter
import live.adabe.fiesty.ui.home.HomeViewModel

@AndroidEntryPoint
class DeviceDetailsFragment : Fragment() {

    private lateinit var binding: FragmentDeviceDetailsBinding
    private lateinit var deviceAdapter: DeviceAdapter
    lateinit var deviceViewModel: DeviceViewModel
    lateinit var homeViewModel: HomeViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDeviceDetailsBinding.inflate(inflater, container, false)
        deviceViewModel = ViewModelProvider(requireActivity())[DeviceViewModel::class.java]
        homeViewModel = ViewModelProvider(requireActivity())[HomeViewModel::class.java]
        return binding.root
    }

}