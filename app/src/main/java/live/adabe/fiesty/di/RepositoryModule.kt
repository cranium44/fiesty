package live.adabe.fiesty.di

import android.app.Application
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import live.adabe.fiesty.db.Preferences
import live.adabe.fiesty.network.api.UserAPI
import live.adabe.fiesty.network.repository.UserRepository
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class RepositoryModule {
    @Provides
    @Singleton
    fun providePrefences(application: Application): Preferences{
        return Preferences(application)
    }

    @Provides
    fun provideUserRepository(preferences: Preferences, userAPI: UserAPI): UserRepository{
        return UserRepository(userAPI, preferences)
    }
}