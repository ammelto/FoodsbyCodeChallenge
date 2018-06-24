package io.melton.foodsbycodechallenge.view.orders.recycler

import android.databinding.BindingAdapter
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.squareup.picasso.Picasso
import io.melton.foodsbycodechallenge.R
import io.melton.foodsbycodechallenge.data.model.Delivery
import io.melton.foodsbycodechallenge.databinding.ItemOrderBinding

/**
 * Created by Alexander Melton on 6/21/2018.
 *
 * Essentially the equivalent of a view for each line item. Adapters go here
 */
class OrdersViewHolder constructor(val binding: ItemOrderBinding): RecyclerView.ViewHolder(binding.root)

/**
 * Load the image with picasso.
 * View binding takes care of the nuances
 */
@BindingAdapter("restaurantImage")
fun loadImage(view: ImageView, imageUrl: String) = Picasso.get().load(imageUrl).into(view)

/**
 * This displays the text below the line item if there's a special status associated with it.
 * The string res is stored on the model itself
 */
@BindingAdapter("orderStatus")
fun loadOrderStatus(view: TextView, delivery: Delivery){
    when(delivery.statusText != null){
        true -> view.text = view.context.resources.getText(delivery.statusText!!)
        else -> view.visibility = View.GONE
    }
}