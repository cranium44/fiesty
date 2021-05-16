package live.adabe.fiesty.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import live.adabe.fiesty.databinding.RoomItemBinding
import live.adabe.fiesty.models.Room
import javax.inject.Inject

class RoomAdapter (
    private val rooms: List<Room>,
    private val listener: RoomItemClickListener
) : RecyclerView.Adapter<RoomAdapter.RoomViewHolder>() {

    class RoomViewHolder(
        private val binding: RoomItemBinding,
        private val listener: RoomItemClickListener
    ) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(room: Room) {
            binding.apply {
                roomNameRv.text = room.name
                deviceCountRv.text = room.numberOfDevices.toString()
                root.setOnClickListener {
                    listener.onItemClick(room)
                }
                deleteRoomButton.setOnClickListener {
                    listener.onItemDelete(room)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RoomViewHolder {
        val binding = RoomItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
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