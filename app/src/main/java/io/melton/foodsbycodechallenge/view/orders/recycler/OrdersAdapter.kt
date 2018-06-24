package io.melton.foodsbycodechallenge.view.orders.recycler

import android.arch.lifecycle.Observer
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
import io.melton.foodsbycodechallenge.view.orders.OrdersView


/**
 * Created by Alexander Melton on 6/21/2018.
 * The activity viewmodel also acts as the viewmodel for this. We simply make a different observer to decouple this from the activity.
 */
class OrdersAdapter : RecyclerView.Adapter<OrdersViewHolder>(){

    private var deliveries: List<Delivery> = emptyList()
    private var layoutInflater: LayoutInflater? = null
    var inputObserver: Observer<OrdersView> = Observer {
        this.swapData(it!!.visibleDeliveries)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrdersViewHolder {
        if (layoutInflater == null) layoutInflater = LayoutInflater.from(parent.context)

        val binding: ItemOrderBinding = DataBindingUtil.inflate(layoutInflater!!, R.layout.item_order, parent, false)
        return OrdersViewHolder(binding)
    }

    /**
     * We can bind the viewholder just like the views in the activity.
     * This takes care of all the dumb updating I/O logic we'd normally have to do through callbacks.
     * It's also much cleaner and easier to read than RxJava streams
     */
    override fun onBindViewHolder(holder: OrdersViewHolder, position: Int) {
        holder.binding.delivery = deliveries[position]
    }

    /**
     * Obligatory data swap method
     * Unfortunately, viewbinding doesnt notify a change in data set so we still have to do that
     */
    fun swapData(deliveries: List<Delivery>?){
        if(deliveries == null) this.deliveries = emptyList()
        else this.deliveries = deliveries
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int = deliveries.size

}