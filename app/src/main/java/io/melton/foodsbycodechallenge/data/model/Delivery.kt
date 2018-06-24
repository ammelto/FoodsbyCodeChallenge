package io.melton.foodsbycodechallenge.data.model

import android.support.annotation.StringRes
import android.view.View
import io.melton.foodsbycodechallenge.R
import java.io.Serializable

/**
 * Created by Alexander Melton on 6/19/2018.
 */
data class Delivery(
        val deliveryId: Number,
        val storeId: Number,
        val restaurantName: String,
        val logoUrl: String,
        val cutoff: String,
        val dropoff: String,
        val soldout: Boolean,
        val sellingOut: Boolean,
        val isPastCutoff: Boolean,
        val isOrderPlaced: Boolean
): Serializable{
     val statusText: Int? @StringRes get() =
        when{
            isPastCutoff -> R.string.past_cutoff
            soldout -> R.string.sold_out
            sellingOut -> R.string.selling_out
            isOrderPlaced -> R.string.order_placed
            else -> null
        }
}