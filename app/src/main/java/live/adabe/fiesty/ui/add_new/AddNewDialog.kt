package live.adabe.fiesty.ui.add_new

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import dagger.hilt.android.AndroidEntryPoint
import live.adabe.fiesty.R
import live.adabe.fiesty.databinding.DialogAddNewBinding
import live.adabe.fiesty.navigation.NavigationService
import live.adabe.fiesty.util.StringConstants
import javax.inject.Inject


@AndroidEntryPoint
class AddNewDialog : DialogFragment() {

    @Inject
    lateinit var navigationService: NavigationService
    private lateinit var binding: DialogAddNewBinding

    init {
        isCancelable = true
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        super.onCreateView(inflater, container, savedInstanceState)
        binding = DialogAddNewBinding.inflate(inflater, container, false)
        dialog?.window?.setBackgroundDrawableResource(R.drawable.dialog_bg)
        initView()
        return binding.root
    }

    private fun initView() {
        binding.apply {
            dialogAddBuilding.setOnClickListener {
                with(Bundle()) {
                    putString(StringConstants.MODE, StringConstants.CREATE_MODE)
                    navigationService.openBuildingCreateScreen(this)
                    dialog?.cancel()
                }
            }

            dialogAddRoom.setOnClickListener {
                with(Bundle()) {
                    putString(StringConstants.MODE, StringConstants.CREATE_MODE)
                    navigationService.openRoomCreateScreen(this)
                    dialog?.cancel()
                }
            }

            dialogAddDevice.setOnClickListener {
                with(Bundle()) {
                    putString(StringConstants.MODE, StringConstants.CREATE_MODE)
                    navigationService.openDeviceCreateScreen(this)
                    dialog?.cancel()
                }
            }
        }
    }

    override fun onStart() {
        super.onStart()
        val width = (resources.displayMetrics.widthPixels * 0.85).toInt()
        val height = (resources.displayMetrics.heightPixels * 0.40).toInt()
        dialog!!.window?.setLayout(width, ViewGroup.LayoutParams.WRAP_CONTENT)
    }
}