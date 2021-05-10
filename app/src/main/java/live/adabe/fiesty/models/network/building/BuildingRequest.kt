package live.adabe.fiesty.models.network.building

data class BuildingRequest(
    val name: String,
    val address: String,
    val energyRate: Long,
)
