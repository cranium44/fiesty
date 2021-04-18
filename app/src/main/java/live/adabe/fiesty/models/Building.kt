package live.adabe.fiesty.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Building(
        @PrimaryKey
        val buildId: Int,
        val name: String,
        val address: String,
        val type: BuildingType,
)