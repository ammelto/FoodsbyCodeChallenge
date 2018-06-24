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
@Inject constructor(context: Context) {
    val isConnected = context.networkInfo?.isConnected
}