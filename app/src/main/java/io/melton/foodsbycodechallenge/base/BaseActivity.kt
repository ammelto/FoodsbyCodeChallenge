package io.melton.foodsbycodechallenge.base

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import io.melton.foodsbycodechallenge.di.contract.DaggerActivityAware
import io.melton.foodsbycodechallenge.extensions.componentCompat

/**
 * Created by Alexander Melton on 6/18/2018.
 */
abstract class BaseActivity : AppCompatActivity(), DaggerActivityAware {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        @Suppress("DEPRECATION") componentCompat.inject(this)
        injectSelf(componentCompat)
    }
}