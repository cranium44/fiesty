package live.adabe.fiesty.di

import dagger.Module
import dagger.Provides

@Module
abstract class DatabaseModule {
    @Provides
    fun provideAppDatabase(){

    }
}