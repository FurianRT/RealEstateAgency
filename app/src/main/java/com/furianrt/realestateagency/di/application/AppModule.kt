package com.furianrt.realestateagency.di.application

import android.app.Application
import android.content.Context
import android.util.Log
import androidx.room.Room
import com.furianrt.realestateagency.data.DataManager
import com.furianrt.realestateagency.data.DataManagerImp
import com.furianrt.realestateagency.data.api.HousesApiService
import com.furianrt.realestateagency.data.room.HouseDatabase
import dagger.Module
import dagger.Provides
import okhttp3.CacheControl
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

@Module
class AppModule(private val app: Application) {

    @Provides
    @AppScope
    fun provideApplication() = app

    @Provides
    @AppScope
    fun provideContext(): Context = app

    @Provides
    @DatabaseInfo
    @AppScope
    fun provideDatabaseName() = "Houses.db"

    @Provides
    @AppScope
    fun provideNoteDatabase(context: Context, @DatabaseInfo databaseName: String) =
            Room.databaseBuilder(context, HouseDatabase::class.java, databaseName)
                    .build()

    @Provides
    @AppScope
    fun provideDataManager(database: HouseDatabase, housesApi: HousesApiService): DataManager =
            DataManagerImp(database, housesApi)

    @Provides
    @LogInfo
    @AppScope
    fun provideLogTag() = "myTag"

    //Так как данные сохраняются в локальную БД, нет необходимости в кэшировании. Отключаем.
    @Provides
    @AppScope
    fun provideNetworkInterceptor(): Interceptor {
        return Interceptor { chain ->
            val original = chain.request()
            val request = original.newBuilder()
                    .cacheControl(CacheControl.FORCE_NETWORK)
                    .build()
            return@Interceptor chain.proceed(request)
        }
    }

    @Provides
    @AppScope
    fun provideHttpLoggingInterceptor(@LogInfo logTag: String): HttpLoggingInterceptor {
        val logging = HttpLoggingInterceptor { message -> Log.e(logTag, message) }
        return logging.setLevel(HttpLoggingInterceptor.Level.BASIC)
    }

    @Provides
    @AppScope
    fun provideOkHttpClient(logInterceptor: HttpLoggingInterceptor, networkInterceptor: Interceptor):
            OkHttpClient = OkHttpClient.Builder()
            .addInterceptor(logInterceptor)
            .addNetworkInterceptor(networkInterceptor)
            .build()

    @Provides
    @AppScope
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit =
            Retrofit.Builder()
                    .client(okHttpClient)
                    .baseUrl("https://api.myjson.com/bins/")
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()

    @Provides
    @AppScope
    fun provideHousesApiService(retrofit: Retrofit): HousesApiService =
            retrofit.create(HousesApiService::class.java)
}