package live.adabe.fiesty.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import live.adabe.fiesty.navigation.NavigationService
import live.adabe.fiesty.navigation.NavigationServiceImpl
import ru.terrakok.cicerone.Cicerone
import ru.terrakok.cicerone.Router
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class NavigationModule {
    @Provides
    @Singleton
    fun provideCicerone(): Cicerone<Router> {
        return Cicerone.create()
    }

    @Provides
    @Singleton
    fun provideNavigationService(cicerone: Cicerone<Router>): NavigationService {
        return NavigationServiceImpl(cicerone)
    }
}