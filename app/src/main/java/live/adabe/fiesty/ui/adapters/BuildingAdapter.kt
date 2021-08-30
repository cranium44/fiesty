package live.adabe.fiesty.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import live.adabe.fiesty.databinding.BuildingItemBinding
import live.adabe.fiesty.models.Building
import live.adabe.fiesty.models.Room
import live.adabe.fiesty.network.repository.RoomRepository
import javax.inject.Inject

class BuildingAdapter @Inject constructor(
    private val buildings: List<Building>,
    private val listener: BuildingItemClickListener,
    private val rooms: List<Room>
) : RecyclerView.Adapter<BuildingAdapter.BuildingViewHolder>() {

    @Inject
    lateinit var roomRepository: RoomRepository

    class BuildingViewHolder(
        private val binding: BuildingItemBinding,
        private val listener: BuildingItemClickListener,
        private val rooms: List<Room>
    ) :
        RecyclerView.ViewHolder(binding.root), View.OnClickListener {
        fun bind(building: Building) {
            binding.apply {
                buildingName.text = building.name
                building.energyRate.toString().also { buildingEnergy.text = "$it kWh" }
                root.setOnClickListener {
                    listener.onClick(building)
                }
                CoroutineScope(Dispatchers.IO).launch {
                    buildingRoomRv.layoutManager = LinearLayoutManager(binding.root.context)
                    buildingRoomRv.adapter =
                        RoomAdapter(rooms = rooms.filter {
                            it.buildingId == building.buildId
                        }, object : RoomAdapter.RoomItemClickListener {
                            override fun onItemClick(room: Room) {

                            }

                            override fun onItemDelete(room: Room) {

                            }

                        })
                }
            }
        }

        override fun onClick(v: View?) {

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BuildingViewHolder {
        val binding =
            BuildingItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return BuildingViewHolder(binding, listener, rooms)
    }

    override fun onBindViewHolder(holder: BuildingViewHolder, position: Int) {
        holder.bind(buildings[position])
    }

    override fun getItemCount(): Int {
        return buildings.size
    }

    interface BuildingItemClickListener {
        fun onClick(building: Building)
        fun onDelete(building: Building)
    }
}