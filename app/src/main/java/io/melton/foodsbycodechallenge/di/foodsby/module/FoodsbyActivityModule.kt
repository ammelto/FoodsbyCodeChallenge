package io.melton.foodsbycodechallenge.di.foodsby.module

import dagger.Module
import dagger.Provides
import io.melton.foodsbycodechallenge.BuildConfig
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import javax.inject.Singleton

/**
 * Created by Alexander Melton on 6/18/2018.
 *
 * Activity specific dependencies. These are scoped only to the activity they're injected into
 *
 * Nothing here for this simple app but this would be full of stuff if the app was production
 */
@Module
class FoodsbyActivityModule{

}