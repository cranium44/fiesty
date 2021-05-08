package live.adabe.fiesty.util

import live.adabe.fiesty.models.Building
import live.adabe.fiesty.models.network.building.BuildingResponse

object Converter {

    fun getBuildingList(data: List<BuildingResponse>): List<Building> {
        return data.map {
            convertBuildingResponseToBuilding(it)
        }
    }

    private fun convertBuildingResponseToBuilding(buildingResponse: BuildingResponse): Building{
        return Building(
            buildId = buildingResponse.id,
            name = buildingResponse.name,
            address = buildingResponse.address,
            energyRate = buildingResponse.energyRate
        )
    }
}