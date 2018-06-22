package io.melton.foodsbycodechallenge.view.orders

import android.databinding.BindingAdapter
import android.view.View
import com.airbnb.lottie.LottieAnimationView
import devs.mulham.horizontalcalendar.HorizontalCalendar
import io.melton.foodsbycodechallenge.data.model.Delivery
import io.melton.foodsbycodechallenge.data.model.Dropoff
import io.melton.foodsbycodechallenge.view.orders.recycler.OrdersAdapter
import timber.log.Timber
import java.util.*

/**
 * Created by Alexander Melton on 6/20/2018.
 */
// Just a personal opinion, but OrdersView is a misleading name. I expected it to extend FrameLayout or something
data class OrdersView(
        // Dont use string constructor, either make nullable, or use "" or static constant for empty string is better
        // No need to add type if instantiating as string
    var topAddressLine: String = String(),
    var bottomAddressLine: String = String(),
    var dropoffs: List<Dropoff> = emptyList(),
    // Not sure how I feel about holding an adapter inside of a data class like this
    // It's an Android specific component, I think you should keep the data `List<Delivery>` in here
    // Then just bind it in the actual view. It's easier to inject different adapters that way too
    var adapter: OrdersAdapter = OrdersAdapter(),
    var selectedDay: Calendar = Calendar.getInstance()
){
    // Function name implies boolean return, but is actually returning integer
    fun isEmpty(): Int{
        // When statements are more clear than one line if/elses
        return if(adapter.itemCount > 0) View.GONE
        else View.VISIBLE
    }
}

// Didn't know about bindingAdapter, dis sick fam :+1:
@BindingAdapter("animateOnUpdate")
fun animateOnUpdate(view: LottieAnimationView, any: Any) = view.playAnimation()