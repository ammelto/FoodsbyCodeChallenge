package io.melton.foodsbycodechallenge.di.android

import android.app.Activity
import android.content.res.Resources
import android.support.v4.app.FragmentActivity
import android.support.v4.app.FragmentManager
import android.support.v7.app.AppCompatActivity
import android.view.LayoutInflater
import android.view.MenuInflater
import android.view.WindowManager

import dagger.Module
import dagger.Provides
import io.melton.foodsbycodechallenge.di.annotation.ActivityScope

/**
 * Dependencies from the Android Activity for use in injections.
 *
 * This module is for native Android services, not application services!
 *
 * @author Renee Vandervelde
 */
@Module
class AndroidActivityModule
/**
 * @param activity The Activity that all of the provided resources
 * will be based off of.
 */
(
        /**
         * Activity context to fetch services from.
         */
        private val activity: AppCompatActivity) {

    /**
     * Current Activity instance.
     *
     * Avoid depending on this service in favor of more granular services.
     *
     * @return The currently activity instance that this module is bound to.
     */
    @Provides
    @ActivityScope
    fun activityContext(): Activity {
        return this.activity
    }

    /**
     * Current Activity instance (support level).
     *
     * Avoid depending on this service in favor of more granular services.
     *
     * @return The currently activity instance that this module is bound to.
     */
    @Provides
    @ActivityScope
    fun supportActivityContext(): FragmentActivity {
        return this.activity
    }

    /**
     * Instantiates a layout XML file into its corresponding View objects.
     *
     * @return LayoutInflater instance that this Window retrieved from its Context.
     */
    @Provides
    @ActivityScope
    fun inflater(): LayoutInflater {
        return this.activity.layoutInflater
    }

    /**
     * Interface for interacting with Fragment objects inside of an Activity.
     *
     * @return The FragmentManager for interacting with fragments associated with this activity.
     */
    @Provides
    @ActivityScope
    fun fragmentManager(): FragmentManager {
        return this.activity.supportFragmentManager
    }

    /**
     * Used to instantiate menu XML files into Menu objects.
     *
     * @return a MenuInflater with the current activity context.
     */
    @Provides
    @ActivityScope
    fun menuInflater(): MenuInflater {
        return this.activity.menuInflater
    }

    /**
     * The interface that apps use to talk to the window manager.
     *
     * @return The window manager for showing custom windows.
     */
    @Provides
    @ActivityScope
    fun windowManager(): WindowManager {
        return this.activity.windowManager
    }

    /**
     *
     *
     * @return
     */
    @Provides
    @ActivityScope
    fun theme(): Resources.Theme {
        return this.activity.theme
    }

}
