package live.adabe.fiesty.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import live.adabe.fiesty.databinding.BuildingItemBinding
import live.adabe.fiesty.models.Building
import live.adabe.fiesty.ui.home.HomeViewModel

class BuildingAdapter(
    private val buildings: List<Building>,
    private val listener: BuildingItemClickListener,
    private val viewModel: HomeViewModel
) : RecyclerView.Adapter<BuildingAdapter.BuildingViewHolder>() {

    class BuildingViewHolder(
        private val binding: BuildingItemBinding,
        private val listener: BuildingItemClickListener
    ) :
        RecyclerView.ViewHolder(binding.root), View.OnClickListener {
        fun bind(building: Building) {
            binding.apply {
                buildingName.text = building.name
                building.energyRate.toString().also { buildingEnergy.text = "$it kWh" }
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
        return BuildingViewHolder(binding, listener)
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