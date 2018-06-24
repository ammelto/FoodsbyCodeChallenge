package io.melton.foodsbycodechallenge.view.orders

import android.databinding.BindingAdapter
import android.support.annotation.StringRes
import android.support.v4.widget.SwipeRefreshLayout
import android.view.View
import com.airbnb.lottie.LottieAnimationView
import com.airbnb.lottie.LottieDrawable
import io.melton.foodsbycodechallenge.data.model.Address
import io.melton.foodsbycodechallenge.data.model.Delivery
import io.melton.foodsbycodechallenge.data.model.Dropoff
import io.melton.foodsbycodechallenge.domain.exceptions.Failure
import java.util.*

/**
 * Created by Alexander Melton on 6/20/2018.
 *
 * Viewstate model that represents the view. One of the cool things we can do with this is serialize this on a crash or error
 * and then hotload the state. We could also push multiple states to a queue and then load the last few states to see exactly
 * what the user was doing.
 *
 * This is basically the raw code behind the layout. In every since of the word, this is the View. This is what the layout is actually bound to
 * All changes to the viewmodel update this and then the activity glues this to the layout
 *
 */
data class OrdersView(
        var address: Address = Address("", ""),
        var dropoffs: List<Dropoff> = emptyList(),
        var selectedDay: String = Calendar.getInstance().getDisplayName(java.util.Calendar.DAY_OF_WEEK, Calendar.LONG, Locale.getDefault())
){
    /**
     * Anytime we get the visible deliveries, we check the day selected and then just pass off whatever should be visible.
     * A caching implementation with multiple end points for each day would be ideal in a production application
     * but since this is small, we just kinda load everything then choose what to show the user
     */
    val visibleDeliveries: List<Delivery> get() = dropoffs.firstOrNull{
        it.day == selectedDay
    }.takeIf { it != null }?.deliveries ?: emptyList()

    /**
     * This handles the empty state for the lottie animation
     * You just bind to this and anytime the delivery list is updated, it'll automatically check this.
     */
    fun emptyState(): Int{
        return if(visibleDeliveries.isEmpty()) View.VISIBLE
        else View.GONE
    }
}

/**
 * All your view specific failures go here too. This forces you to account for every fail state and have them in a nice spot
 */
data class AddressReceiveFailure(
        @StringRes val topAddressLine: Int,
        @StringRes val bottomAddressLine: Int
) : Failure

data class AddressPermissionFailure(
        @StringRes val topAddressLine: Int,
        @StringRes val bottomAddressLine: Int
) : Failure

data class AddressEmptyFailure(
        @StringRes val topAddressLine: Int
) : Failure

/**
 * Then finally the binding adapters needed for the layout
 */
@BindingAdapter("animateOnUpdate")
fun animateOnUpdate(view: LottieAnimationView, any: Any) = view.playAnimation()

@BindingAdapter("locationLoading")
fun locationLoading(view: LottieAnimationView, topAddressLine: String) {
    when(topAddressLine == ""){
        true -> view.repeatMode = LottieDrawable.INFINITE
        false -> view.repeatMode = 0
    }
}

@BindingAdapter("dropoffsRefresh")
fun dropoffsRefresh(view: SwipeRefreshLayout, any: Any){
    if(view.isRefreshing) view.isRefreshing = false
}
