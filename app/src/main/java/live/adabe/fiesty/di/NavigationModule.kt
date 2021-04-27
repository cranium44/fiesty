package live.adabe.fiesty.di

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import live.adabe.fiesty.navigation.NavigationService
import live.adabe.fiesty.navigation.NavigationServiceImpl
import ru.terrakok.cicerone.Cicerone
import ru.terrakok.cicerone.Router

@InstallIn(SingletonComponent::class)
@Module
class NavigationModule {
    @Provides
    fun provideCicerone(): Cicerone<Router>{
        return Cicerone.create()
    }

    @Provides
    fun provideNavigationService(cicerone: Cicerone<Router>): NavigationService {
        return NavigationServiceImpl(cicerone)
    }
}