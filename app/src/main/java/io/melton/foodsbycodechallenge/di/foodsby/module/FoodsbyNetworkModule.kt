package io.melton.foodsbycodechallenge.di.foodsby.module

import android.app.Application
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationServices
import dagger.Module
import dagger.Provides
import io.melton.foodsbycodechallenge.BuildConfig
import io.melton.foodsbycodechallenge.R
import io.melton.foodsbycodechallenge.data.repository.dropoff.DropoffsRepository
import okhttp3.*
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton
import com.google.gson.JsonParser
import com.google.gson.JsonElement
import com.google.gson.JsonObject
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.experimental.CoroutineCallAdapterFactory
import io.melton.foodsbycodechallenge.data.repository.dropoff.DropoffsApi
import io.melton.foodsbycodechallenge.data.repository.location.LocationRepository
import java.io.IOException
import java.io.InputStreamReader


/**
 * Created by Alexander Melton on 6/18/2018.
 *
 * Application level network module. Any I/O dependency resides here
 */
@Module
class FoodsbyNetworkModule{
    @Provides
    @Singleton
    fun provideRetrofit(application: Application): Retrofit {
        return Retrofit.Builder()
                .baseUrl("http://10.0.2.2/")
                .client(createClient(application))
                .addCallAdapterFactory(CoroutineCallAdapterFactory())
                .addConverterFactory(GsonConverterFactory.create())
                .build()
    }

    @Provides
    @Singleton
    fun providesDropoffsApi(retrofit: Retrofit): DropoffsApi{
        return retrofit.create(DropoffsApi::class.java)
    }

    @Provides
    @Singleton
    fun providesLocationClient(application: Application): FusedLocationProviderClient{
        return LocationServices.getFusedLocationProviderClient(application)
    }

    /**
     * Essentially just loads the JSON file anytime we make an http request.
     * Since we don't have a real API, this works as an alternative
     */
    private fun createClient(application: Application): OkHttpClient {
        val okHttpClientBuilder: OkHttpClient.Builder = OkHttpClient.Builder()
        val loggingInterceptor = HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BASIC)
        okHttpClientBuilder.addInterceptor(loggingInterceptor)

        okHttpClientBuilder.addInterceptor {
            val json: JsonObject
            try {
                val element = JsonParser().parse(
                        InputStreamReader(application.resources.openRawResource(R.raw.dropoffs))
                )
                json = element.asJsonObject
            } catch (e: IOException) {
                throw RuntimeException(e.localizedMessage)
            }
            val response: String = json.toString()

            Response.Builder()
                    .code(200)
                    .message(response)
                    .request(it.request())
                    .protocol(Protocol.HTTP_1_1)
                    .body(ResponseBody.create(MediaType.parse("application/json"), response))
                    .addHeader("content-type", "application/json")
                    .build()
        }

        return okHttpClientBuilder.build()
    }

    @Provides @Singleton fun provideDropoffsRepository(dataSource: DropoffsRepository.Network): DropoffsRepository = dataSource
    @Provides @Singleton fun provideLocationRepository(dataSource: LocationRepository.Network): LocationRepository = dataSource

}