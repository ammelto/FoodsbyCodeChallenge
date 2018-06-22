package io.melton.foodsbycodechallenge

import android.app.Application
import io.melton.foodsbycodechallenge.di.android.AndroidApplicationModule
import io.melton.foodsbycodechallenge.di.foodsby.ApplicationComponent
import io.melton.foodsbycodechallenge.di.foodsby.DaggerApplicationComponent
import io.reactivex.disposables.CompositeDisposable
import timber.log.Timber

/**
 * Created by Alexander Melton on 6/15/2018.
 *
 * Activity level class
 */
class FoodsbyCodeChallenge: Application(){

    /**
     * Get the single-instance of the shared application component.
     *
     * @return An application component to be used to inject base-level classes.
     */
    var applicationComponent: ApplicationComponent? = null
        private set

    // delete this
    private var compositeDisposable: CompositeDisposable? = null

    override fun onCreate() {
        super.onCreate()
        startServer()

        this.initializeInjection()
        Timber.plant(Timber.DebugTree())
        compositeDisposable = CompositeDisposable()
    }

    fun initializeInjection(){
        val builder = DaggerApplicationComponent.builder()
        builder.application(this).applicationModule(AndroidApplicationModule(this))

        this.applicationComponent = builder.build()
        this.applicationComponent?.inject(this)
    }

    // Delete this

    /**
     * This only exists to create a mock server for the json file.
     * This way the application can build and run as if it's a production level application with retrofit/okhttp instead of just loading the JSON with only gson
     */
    fun startServer(){


    }
}