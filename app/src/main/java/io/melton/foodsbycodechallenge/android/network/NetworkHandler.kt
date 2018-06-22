package io.melton.foodsbycodechallenge.android.network

import javax.inject.Singleton
import android.content.Context
import io.melton.foodsbycodechallenge.extensions.networkInfo
import javax.inject.Inject

/**
 * Created by Alexander Melton on 6/19/2018.
 * Injectable class which returns information about the network connection state.
 */

@Singleton
class NetworkHandler
@Inject constructor(private val context: Context) {
    // Get is unnecessary, I don't see why this can't just be returned within your top level module
    val isConnected get() = context.networkInfo?.isConnected
}