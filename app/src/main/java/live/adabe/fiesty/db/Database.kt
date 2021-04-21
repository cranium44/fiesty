package live.adabe.fiesty.db

import androidx.room.Database
import androidx.room.RoomDatabase
import live.adabe.fiesty.models.Building
import live.adabe.fiesty.models.Device
import live.adabe.fiesty.models.Room

@Database(entities = [Building::class, Room::class, Device::class], version = 1)
abstract class Database: RoomDatabase() {
    abstract fun buildingDao(): BuildingDAO
}