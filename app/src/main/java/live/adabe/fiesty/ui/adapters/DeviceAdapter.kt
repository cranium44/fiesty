package live.adabe.fiesty.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import live.adabe.fiesty.databinding.DeviceItemBinding
import live.adabe.fiesty.models.Device

class DeviceAdapter(
    private val devices: List<Device>,
    private val listener: DeviceItemClickListener
) : RecyclerView.Adapter<DeviceAdapter.DeviceViewHolder>() {


    class DeviceViewHolder(
        private val binding: DeviceItemBinding,
        private val listener: DeviceItemClickListener
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(device: Device) {
            binding.apply {
                deviceNameRv.text = device.name
                deviceItemRating.text = device.rating.toString()

                deviceItemDelete.setOnClickListener {
                    listener.onItemDelete(device)
                }

                root.setOnClickListener {
                    listener.onItemClick(device)
                }
            }
        }
    }

    interface DeviceItemClickListener {
        fun onItemClick(device: Device)
        fun onItemDelete(device: Device)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DeviceViewHolder {
        val binding = DeviceItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return DeviceViewHolder(binding, listener)
    }

    override fun onBindViewHolder(holder: DeviceViewHolder, position: Int) {
        holder.bind(devices[position])
    }

    override fun getItemCount(): Int = devices.size
}