package live.adabe.fiesty.di

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import live.adabe.fiesty.network.*
import live.adabe.fiesty.network.api.*
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {


    @Provides
    @Singleton
    fun provideLoggingInterceptor(): HttpLoggingInterceptor {
        return with(HttpLoggingInterceptor()) {
            setLevel(HttpLoggingInterceptor.Level.BODY)
        }
    }

    @Provides
    @Singleton
    fun provideGson(): Gson{
        return GsonBuilder().setLenient().create()
    }

    @Provides
    fun provideOkHttpClient(
        httpLoggingInterceptor: HttpLoggingInterceptor
    ): OkHttpClient {
        return OkHttpClient.Builder().callTimeout(10, TimeUnit.MINUTES)
            .addNetworkInterceptor(httpLoggingInterceptor).build()
    }

    @Provides
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder().baseUrl(NetworkConstants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create()).client(okHttpClient)
            .build()
    }

    @Provides
    fun provideUserApi(retrofit: Retrofit): UserAPI {
        return retrofit.create(UserAPI::class.java)
    }

    @Provides
    fun provideBuildingAPI(retrofit: Retrofit): BuildingAPI {
        return retrofit.create(BuildingAPI::class.java)
    }

    @Provides
    fun provideRoomAPI(retrofit: Retrofit): RoomAPI {
        return retrofit.create(RoomAPI::class.java)
    }

    @Provides
    fun provideDeviceAPI(retrofit: Retrofit): DeviceAPI = retrofit.create(DeviceAPI::class.java)

    @Provides
    fun provideEnergyAPI(retrofit: Retrofit): EnergyAPI = retrofit.create(EnergyAPI::class.java)
}