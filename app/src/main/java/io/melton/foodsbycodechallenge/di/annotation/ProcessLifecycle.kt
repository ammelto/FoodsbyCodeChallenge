package io.melton.foodsbycodechallenge.di.annotation

import java.lang.annotation.Retention

import javax.inject.Qualifier

import java.lang.annotation.RetentionPolicy.RUNTIME

/**
 * Created by Alexander Melton on 6/18/2018.
 */

// This is only used on one dagger provider, so you don't need it
@Qualifier
@Retention(RUNTIME)
annotation class ProcessLifecycle
