package io.melton.foodsbycodechallenge.base

import android.arch.lifecycle.LifecycleRegistry
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.os.PersistableBundle
import android.support.v7.app.AppCompatActivity
import com.evernote.android.state.StateSaver
import io.melton.foodsbycodechallenge.FoodsbyCodeChallenge
import io.melton.foodsbycodechallenge.di.android.AndroidActivityModule
import io.melton.foodsbycodechallenge.di.contract.DaggerActivityAware
import io.melton.foodsbycodechallenge.di.foodsby.FoodsbyActivityComponent
import io.reactivex.disposables.CompositeDisposable

/**
 * Created by Alexander Melton on 6/18/2018.
 */
abstract class BaseActivity : AppCompatActivity(), DaggerActivityAware {
    val foodsbyCodeChallengeApp get() = application as FoodsbyCodeChallenge
    val component: FoodsbyActivityComponent by lazy { foodsbyCodeChallengeApp.applicationComponent!!.newActivityComponent(AndroidActivityModule(this)) }
    val lifecycleRegistry: LifecycleRegistry by lazy { LifecycleRegistry(this) }

    private var viewModel: BaseActivityViewModel? = null

    private lateinit var disposables: CompositeDisposable

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(BaseActivityViewModel::class.java)

        @Suppress("DEPRECATION") component.inject(this)
        injectSelf(component)
        StateSaver.restoreInstanceState(this, savedInstanceState)
    }

    override fun onStart() {
        super.onStart()
        disposables = CompositeDisposable()
    }

    override fun onSaveInstanceState(outState: Bundle?) {
        super.onSaveInstanceState(outState)

        if (outState != null) StateSaver.saveInstanceState(this, outState)
    }
}