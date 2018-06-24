package io.melton.foodsbycodechallenge.di.android

import android.app.Application
import android.arch.lifecycle.Lifecycle
import android.arch.lifecycle.ProcessLifecycleOwner
import android.content.ContentResolver
import android.content.Context
import android.content.SharedPreferences
import android.content.pm.ApplicationInfo
import android.content.pm.PackageManager
import android.content.res.AssetManager
import android.content.res.Resources

import javax.inject.Singleton

import dagger.Module
import dagger.Provides
import io.melton.foodsbycodechallenge.android.lifecycle.ProcessLifecycleObserver
import io.melton.foodsbycodechallenge.android.lifecycle.ProcessVisibility
import io.melton.foodsbycodechallenge.di.annotation.ProcessLifecycle

/**
 * Created by Alexander Melton on 6/18/2018.
 *
 * Dependencies from the Android Application / Context for use in injections.
 *
 * This module is for native Android services, not application services!
 *
 */
@Module
class AndroidApplicationModule
/**
 * @param application The active Android application.
 */
(private val application: Application) {

    /**
     * Android Application instance.
     *
     * Avoid depending on this service in favor of more granular services.
     *
     * @return The root Android application instance that is bound to the module.
     */
    @Provides
    @Singleton
    fun application(): Application {
        return this.application
    }

    /**
     * Generic system context.
     *
     * Avoid depending on this service in favor of more granular services.
     *
     * @return The context of the single, global Application object of the current process.
     */
    @Provides
    @Singleton
    fun applicationContext(): Context {
        return this.application.applicationContext
    }

    /**
     * Information you can retrieve about a particular application.
     *
     * @return The full application info for this context's package.
     */
    @Provides
    @Singleton
    fun applicationInfo(): ApplicationInfo {
        return this.application.applicationInfo
    }

    /**
     * Provides access to an application's raw asset files.
     *
     * @return An AssetManager instance for your application's package.
     */
    @Provides
    @Singleton
    fun assets(): AssetManager {
        return this.application.assets
    }

    /**
     * This class provides applications access to the content model.
     *
     * @return A ContentResolver instance for your application's package.
     */
    @Provides
    @Singleton
    fun contentResolver(): ContentResolver {
        return this.application.contentResolver
    }

    /**
     * Loads classes and resources from a repository.
     *
     * @return A class loader you can use to retrieve classes in this package.
     */
    @Provides
    @Singleton
    fun classLoader(): ClassLoader {
        return this.application.classLoader
    }

    /**
     * Retrieves various kinds of information related to the application
     * packages that are currently installed on the device.
     *
     * @return A PackageManager instance to find global package information.
     */
    @Provides
    @Singleton
    fun packageManager(): PackageManager {
        return this.application.packageManager
    }

    /**
     * Keeps track of all non-code assets associated with an application.
     *
     * @return A Resources instance for your application's package.
     */
    @Provides
    @Singleton
    fun resources(): Resources {
        return this.application.resources
    }

    /**
     * Application Shared Preferences.
     *
     * This provides a private-mode level shared preferences instance, since
     * all other modes are deprecated.
     * The provided instance does not allow concurrent writing.
     *
     * @return A SharedPreferences through which you can retrieve and modify its values.
     */
    @Provides
    @Singleton
    fun sharedPreferences(): SharedPreferences {
        return this.application.getSharedPreferences(application.packageName, Context.MODE_PRIVATE)
    }

    @Provides
    @Singleton
    @ProcessLifecycle
    internal fun processLifecycle(): Lifecycle {
        return ProcessLifecycleOwner.get().lifecycle
    }

    @Provides
    @Singleton
    internal fun processLifecycleObserver(@ProcessLifecycle lifecycle: Lifecycle): ProcessLifecycleObserver {
        val processLifecycleObserver = ProcessLifecycleObserver()
        lifecycle.addObserver(processLifecycleObserver)
        return processLifecycleObserver
    }

    @Provides
    @Singleton
    internal fun processVisibility(lifecycle: ProcessLifecycleObserver): ProcessVisibility {
        return lifecycle
    }
}
