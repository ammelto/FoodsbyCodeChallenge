package io.melton.foodsbycodechallenge.view.orders.recycler

import android.databinding.BindingAdapter
import android.support.v7.widget.RecyclerView
import android.view.ViewGroup
import io.melton.foodsbycodechallenge.data.model.Delivery
import android.view.LayoutInflater
import android.databinding.DataBindingUtil
import android.widget.ImageView
import com.squareup.picasso.Picasso
import io.melton.foodsbycodechallenge.R
import io.melton.foodsbycodechallenge.databinding.ItemOrderBinding


/**
 * Created by Alexander Melton on 6/21/2018.
 */
class OrdersAdapter : RecyclerView.Adapter<OrdersViewHolder>(){

    private var deliveries: List<Delivery> = emptyList()
    private var layoutInflater: LayoutInflater? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrdersViewHolder {
        if (layoutInflater == null) {
            layoutInflater = LayoutInflater.from(parent.context)
        }

        val binding: ItemOrderBinding = DataBindingUtil.inflate(layoutInflater!!, R.layout.item_order, parent, false)
        return OrdersViewHolder(binding)
    }

    override fun onBindViewHolder(holder: OrdersViewHolder, position: Int) {
        holder.binding.delivery = deliveries[position]
    }

    // This shouldn't be nullable, just pass empty list, and you can get rid of the null check
    fun swapData(deliveries: List<Delivery>?){
        if(deliveries == null) this.deliveries = emptyList()
        else this.deliveries = deliveries
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int = deliveries.size

}