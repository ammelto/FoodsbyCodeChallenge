package io.melton.foodsbycodechallenge.android.lifecycle

import android.arch.lifecycle.Lifecycle
import android.arch.lifecycle.LifecycleObserver
import android.arch.lifecycle.OnLifecycleEvent
import java.util.concurrent.atomic.AtomicBoolean

/**
 * Created by Alexander Melton on 6/18/2018.
 *
 * Class designed to observe the [Lifecycle] returned from [ProcessLifecycleOwner] and allows
 * us to ask the [ProcessVisibility] interface if the app is in the foreground.
 *
 * ⚠⚠⚠ This should not be used for another type of [Lifecycle] ⚠⚠⚠
 *
 * This [LifecycleObserver] makes the assumption that we're observing the [Lifecycle] for the
 * entire process and not just a single Activity. This fundamentally changes the way some of the
 * callbacks in this class will behave.
 */
class ProcessLifecycleObserver : LifecycleObserver, ProcessVisibility {
    override val isVisible = AtomicBoolean(false)

    /**
     * Is only called once per application run.
     */
    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    fun onCreate() {
        isVisible.set(true)
    }

    /**
     * Is called as an Activity goes through onResume, and no other activity had been started recently
     */
    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    fun onResume() {
        isVisible.set(true)
    }

    /**
     * Will only be called once all Activities have called onStop, also includes a slight delay.
     *
     * This should be sufficient to check if the app is in the background, also onDestroy will never
     * be called for a Process [Lifecycle]
     */
    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    fun onStop() {
        isVisible.set(false)
    }
}