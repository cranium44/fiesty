package live.adabe.fiesty.util

import live.adabe.fiesty.models.Building
import live.adabe.fiesty.models.Room
import live.adabe.fiesty.models.network.building.BuildingRequest
import live.adabe.fiesty.models.network.building.BuildingResponse
import live.adabe.fiesty.models.network.room.RoomResponse

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

    fun convertBuildingToBuildingRequest(building: Building): BuildingRequest {
        return BuildingRequest(
            name = building.name,
            address = building.address,
            energyRate = building.energyRate
        )
    }

    fun convertRoomResponseToRoom(roomResponse: RoomResponse): Room {
        return Room(
            rmId = roomResponse.rmId,
            buildingId = roomResponse.buildingId,
            numberOfDevices = roomResponse.numberOfDevices,
            name = roomResponse.name
        )
    }
}