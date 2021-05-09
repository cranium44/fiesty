package live.adabe.fiesty.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import live.adabe.fiesty.databinding.BuildingItemBinding
import live.adabe.fiesty.models.Building

class BuildingAdapter(
    private val buildings: List<Building>,
    private val listener: BuildingItemClickListener
) : RecyclerView.Adapter<BuildingAdapter.BuildingViewHolder>() {

    class BuildingViewHolder(
        private val binding: BuildingItemBinding,
        private val listener: BuildingItemClickListener
    ) :
        RecyclerView.ViewHolder(binding.root), View.OnClickListener {
        fun bind(building: Building) {
            binding.apply {
                buildingName.text = building.name
                buildingAddress.text = building.address
                root.setOnClickListener {
                    listener.onClick(building)
                }
            }
        }

        override fun onClick(v: View?) {

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BuildingViewHolder {
        val binding =
            BuildingItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return BuildingViewHolder(binding, listener)//.listen { position, _ ->
//            val building = buildings[position]
//            with(Bundle()){
//                putString(StringConstants.BUILDING_NAME, building.name)
//                putInt(StringConstants.BUILDING_ID, building.buildId)
//                putString(StringConstants.BUILDING_ADDRESS, building.address)
//                putLong(StringConstants.BUILDING_RATE, building.energyRate)
//                navigationService.openBuildingScreen(this)
//                Timber.d("item clicked")
//            }
        //      }
    }

    override fun onBindViewHolder(holder: BuildingViewHolder, position: Int) {
        holder.bind(buildings[position])
    }

    override fun getItemCount(): Int {
        return buildings.size
    }

    interface BuildingItemClickListener {
        fun onClick(building: Building)
    }
}