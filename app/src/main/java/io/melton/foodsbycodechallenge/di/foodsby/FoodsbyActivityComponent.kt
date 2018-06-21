package io.melton.foodsbycodechallenge.di.foodsby

import dagger.Subcomponent
import io.melton.foodsbycodechallenge.base.BaseActivity
import io.melton.foodsbycodechallenge.base.BaseFragment
import io.melton.foodsbycodechallenge.di.android.AndroidActivityModule
import io.melton.foodsbycodechallenge.di.annotation.ActivityScope
import io.melton.foodsbycodechallenge.di.foodsby.module.FoodsbyActivityModule
import io.melton.foodsbycodechallenge.view.orders.OrdersActivity

/**
 * Created by Alexander Melton on 6/18/2018.
 *
 * Dagger component scoped to an Activity. Pulls in subcomponents and defines all injects for anything
 * injected with dependencies tied to the activity scope
 *
 * We remove things like the network component from here to enforce separation of the network layer from the view layer, etc.
 */
@ActivityScope
@Subcomponent(modules = [
    FoodsbyActivityModule::class,
    AndroidActivityModule::class
])
interface FoodsbyActivityComponent {

    @Deprecated("This method is necessary to inject the base activity but\n" +
            "                  can't be used to inject child activities. This is deprecated\n" +
            "                  to indicate that you need to add a new method to this class\n" +
            "                  to inject your activity!")
    fun inject(target: BaseActivity)


    @Deprecated("This method is necessary to inject the base fragment but\n" +
            "                  can't be used to inject child fragments. This is deprecated\n" +
            "                  to indicate that you need to add a new method to this class\n" +
            "                  to inject your fragment!")
    fun inject(target: BaseFragment)

    fun inject(target: OrdersActivity)

}
