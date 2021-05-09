package live.adabe.fiesty.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import live.adabe.fiesty.databinding.RoomItemBinding
import live.adabe.fiesty.models.Room
import live.adabe.fiesty.navigation.NavigationService
import live.adabe.fiesty.util.listen
import javax.inject.Inject

class RoomAdapter @Inject constructor(
    private val rooms: List<Room>,
    private val navigationService: NavigationService
) : RecyclerView.Adapter<RoomAdapter.RoomViewHolder>() {

    class RoomViewHolder(private val binding: RoomItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(room: Room) {
            binding.apply {
                roomNameRv.text = room.name
                deviceCountRv.text = room.numberOfDevices.toString()
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RoomViewHolder {
        val binding = RoomItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return RoomViewHolder(binding).listen { position, type ->
            navigationService.openRoomScreen(null)
        }
    }

    override fun onBindViewHolder(holder: RoomViewHolder, position: Int) {
        holder.bind(rooms[position])
    }

    override fun getItemCount(): Int {
        return rooms.size
    }
}