package live.adabe.fiesty.ui.device

import android.app.TimePickerDialog
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import dagger.hilt.android.AndroidEntryPoint
import live.adabe.fiesty.databinding.FragmentDeviceCreateBinding
import live.adabe.fiesty.util.Converter
import live.adabe.fiesty.util.updateTime
import java.time.LocalTime

@AndroidEntryPoint
class DeviceCreateFragment : Fragment() {

    private lateinit var binding: FragmentDeviceCreateBinding

    private lateinit var startTime: LocalTime
    private lateinit var stopTime: LocalTime
    private var roomId:Int? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDeviceCreateBinding.inflate(inflater, container, false)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            initViews()
        }
        return binding.root
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun initViews() {
        binding.apply {
            startTimeLayout.setOnClickListener {
                val timePickerDialog = TimePickerDialog(requireActivity(), { _, hourOfDay, minute ->
                    startTimeTv.text = updateTime(hourOfDay, minute)
                    startTime = Converter.getTime(hourOfDay, minute)
                }, 8, 0, false)
                timePickerDialog.show()
            }

            stopTimeLayout.setOnClickListener {
                val timePickerDialog = TimePickerDialog(requireActivity(), { _, hourOfDay, minute ->
                    stopTimeTv.text = updateTime(hourOfDay, minute)
                    stopTime = Converter.getTime(hourOfDay, minute)
                }, 8, 0, false)
                timePickerDialog.show()
            }
        }
    }

}