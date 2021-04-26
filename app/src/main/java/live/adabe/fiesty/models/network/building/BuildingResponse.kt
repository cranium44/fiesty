package live.adabe.fiesty.models.network.building

data class BuildingResponse(
    val id: Int,
    val name: String,
    val address: String,
    val energyRate: Long,
)
