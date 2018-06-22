package io.melton.foodsbycodechallenge.extensions

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo

/**
 * Created by Alexander Melton on 6/19/2018.
 */
// My rule of thumb is if it doesn't fit on one line, it gets brackets. just a personal obinion tho
val Context.networkInfo: NetworkInfo? get() =
    (this.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager).activeNetworkInfo