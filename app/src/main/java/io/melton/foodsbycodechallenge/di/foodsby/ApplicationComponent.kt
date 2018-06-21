package io.melton.foodsbycodechallenge.di.foodsby

import dagger.BindsInstance
import dagger.Component
import io.melton.foodsbycodechallenge.FoodsbyCodeChallenge
import io.melton.foodsbycodechallenge.di.android.AndroidActivityModule
import io.melton.foodsbycodechallenge.di.android.AndroidApplicationModule
import io.melton.foodsbycodechallenge.di.foodsby.module.FoodsbyApplicationModule
import io.melton.foodsbycodechallenge.di.foodsby.module.FoodsbyNetworkModule
import io.melton.foodsbycodechallenge.di.viewmodel.ViewModelBindsModule
import io.melton.foodsbycodechallenge.di.viewmodel.ViewModelProvidesModule
import javax.inject.Singleton

/**
 * Created by Alexander Melton on 6/18/2018.
 */
@Singleton
@Component(modules = [
    FoodsbyApplicationModule::class,
    FoodsbyNetworkModule::class,
    ViewModelProvidesModule::class,
    ViewModelBindsModule::class,
    AndroidApplicationModule::class])
interface ApplicationComponent{

    @Component.Builder interface Builder {
        @BindsInstance
        fun application(app: FoodsbyCodeChallenge): Builder
        fun applicationModule(module: AndroidApplicationModule): Builder
        fun build(): ApplicationComponent
    }

    fun inject(target: FoodsbyCodeChallenge)
    fun newActivityComponent(module: AndroidActivityModule): FoodsbyActivityComponent

}