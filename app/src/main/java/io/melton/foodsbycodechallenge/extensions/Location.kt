package io.melton.foodsbycodechallenge.extensions

import android.content.Context
import android.location.Address
import android.location.Geocoder
import android.location.Location
import java.util.*

/**
 * Created by Alexander Melton on 6/20/2018.
 */

/**
 * Gets the address from the geocoder
 */
fun Location.address(context: Context): Address{
    return Geocoder(context , Locale.getDefault())
            .getFromLocation(latitude, longitude, 1)
            .first()
}

/**
 * Converts the location to a usable POKO
 */
fun Location.toFoodsbyAddress(context: Context): io.melton.foodsbycodechallenge.data.model.Address{
    val address = address(context)
    val topAddress = address.getAddressLine(0)
    val bottomAddress = if(address.maxAddressLineIndex > 0) address.getAddressLine(1) else address.subAdminArea
    return io.melton.foodsbycodechallenge.data.model.Address(topAddress, bottomAddress)
}
