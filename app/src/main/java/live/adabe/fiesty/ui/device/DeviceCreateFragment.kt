package live.adabe.fiesty.ui.device

import android.app.TimePickerDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import live.adabe.fiesty.databinding.FragmentDeviceCreateBinding


class DeviceCreateFragment : Fragment() {

    private lateinit var binding: FragmentDeviceCreateBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDeviceCreateBinding.inflate(inflater, container, false)

        return binding.root
    }

    fun initViews() {
        binding.apply {
            startTimeLayout.setOnClickListener {
                val timePickerDialog = TimePickerDialog(requireActivity(), { _, hourOfDay, minute ->
                    startTimeTv.text = "$hourOfDay : $minute"
                }, 12, 0, false)
                timePickerDialog.show()
            }

            stopTimeLayout.setOnClickListener {
                val timePickerDialog = TimePickerDialog(requireActivity(), { _, hourOfDay, minute ->
                    stopTimeTv.text = "$hourOfDay : $minute"
                }, 12, 0, false)
                timePickerDialog.show()
            }
        }
    }

}