package live.adabe.fiesty.ui.adapters

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import live.adabe.fiesty.databinding.BuildingItemBinding
import live.adabe.fiesty.models.Building
import live.adabe.fiesty.navigation.NavigationService
import live.adabe.fiesty.util.StringConstants
import live.adabe.fiesty.util.listen
import timber.log.Timber

class BuildingAdapter(
    private val buildings: List<Building>,
    private val navigationService: NavigationService
) : RecyclerView.Adapter<BuildingAdapter.BuildingViewHolder>() {

    class BuildingViewHolder(private val binding: BuildingItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(building: Building) {
            binding.apply {
                buildingName.text = building.name
                buildingAddress.text = building.address
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BuildingViewHolder {
        val binding =
            BuildingItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return BuildingViewHolder(binding).listen { position, _ ->
            val building = buildings[position]
            with(Bundle()){
                putString(StringConstants.BUILDING_NAME, building.name)
                putInt(StringConstants.BUILDING_ID, building.buildId)
                putString(StringConstants.BUILDING_ADDRESS, building.address)
                putLong(StringConstants.BUILDING_RATE, building.energyRate)
                navigationService.openBuildingScreen(this)
                Timber.d("item clicked")
            }
        }
    }

    override fun onBindViewHolder(holder: BuildingViewHolder, position: Int) {
        holder.bind(buildings[position])
    }

    override fun getItemCount(): Int {
        return buildings.size
    }
}