package io.melton.foodsbycodechallenge.base

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import com.evernote.android.state.StateSaver
import io.melton.foodsbycodechallenge.FoodsbyCodeChallenge
import io.melton.foodsbycodechallenge.di.android.AndroidActivityModule
import io.melton.foodsbycodechallenge.di.contract.DaggerActivityAware
import io.reactivex.disposables.CompositeDisposable

/**
 * Created by Alexander Melton on 6/18/2018.
 */
abstract class BaseFragment: Fragment(), DaggerActivityAware {

    protected lateinit var disposables: CompositeDisposable

    override fun onStart() {
        super.onStart()
        disposables = CompositeDisposable()

    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        this.initializeInjections()
        StateSaver.restoreInstanceState(this, savedInstanceState)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)

        StateSaver.saveInstanceState(this, outState)
    }

    /**
     * Inject this class and invoke the child injector.
     */
    private fun initializeInjections() {
        val application = this.activity!!.application as FoodsbyCodeChallenge
        val applicationComponent = application.applicationComponent

        val androidActivityModule = AndroidActivityModule(this.activity as AppCompatActivity)
        val activityComponent = applicationComponent!!.newActivityComponent(androidActivityModule)

        @Suppress("DEPRECATION") activityComponent.inject(this)
        this.injectSelf(activityComponent)
    }


}