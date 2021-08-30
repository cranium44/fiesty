package live.adabe.fiesty.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import live.adabe.fiesty.databinding.BuildingChildItemBinding
import live.adabe.fiesty.models.Room

class RoomAdapter(
    private val rooms: List<Room>,
    private val listener: RoomItemClickListener
) : RecyclerView.Adapter<RoomAdapter.RoomViewHolder>() {

    class RoomViewHolder(
        private val binding: BuildingChildItemBinding,
        private val listener: RoomItemClickListener
    ) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(room: Room) {
            binding.apply {
                childName.text = room.name
                childEnergy.text = "${room.numberOfDevices.toString()} kW/hr"
                root.setOnClickListener {
                    listener.onItemClick(room)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RoomViewHolder {
        val binding =
            BuildingChildItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return RoomViewHolder(binding, listener)
    }

    override fun onBindViewHolder(holder: RoomViewHolder, position: Int) {
        holder.bind(rooms[position])
    }

    override fun getItemCount(): Int {
        return rooms.size
    }

    interface RoomItemClickListener {
        fun onItemClick(room: Room)
        fun onItemDelete(room: Room)
    }
}