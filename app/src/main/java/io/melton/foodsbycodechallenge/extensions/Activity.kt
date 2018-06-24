package io.melton.foodsbycodechallenge.extensions

import android.app.Activity
import android.arch.lifecycle.LifecycleRegistry
import android.support.v7.app.AppCompatActivity
import io.melton.foodsbycodechallenge.FoodsbyCodeChallenge
import io.melton.foodsbycodechallenge.di.android.AndroidActivityModule
import io.melton.foodsbycodechallenge.di.foodsby.FoodsbyActivityComponent

/**
 * Created by Alexander Melton on 6/21/2018.
 */

val Activity.foodsbyCodeChallengeApp get() = application as FoodsbyCodeChallenge
val Activity.componentCompat: FoodsbyActivityComponent get() = this.foodsbyCodeChallengeApp.applicationComponent!!.newActivityComponent(AndroidActivityModule(this as AppCompatActivity))
