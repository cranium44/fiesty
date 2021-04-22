package live.adabe.fiesty.di

import android.app.Application
import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import live.adabe.fiesty.db.BuildingDAO
import live.adabe.fiesty.db.Database
import live.adabe.fiesty.db.Preferences
import live.adabe.fiesty.db.RoomDAO
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class DatabaseModule {
    @Provides
    fun providePreferences(application: Application): Preferences {
        return Preferences(application)
    }

    @Provides
    fun provideDb(@ApplicationContext applicationContext: Context): Database {
        return Room.databaseBuilder(applicationContext, Database::class.java, "fiesty_db")
            .fallbackToDestructiveMigration().build()
    }

    @Provides
    fun provideBuildingDAO(db: Database): BuildingDAO {
        return db.buildingDao()
    }

    @Provides
    @Singleton
    fun provideRoomDAO(db: Database): RoomDAO {
        return db.roomDAO()
    }
}