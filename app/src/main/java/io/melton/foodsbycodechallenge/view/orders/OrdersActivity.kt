package io.melton.foodsbycodechallenge.view.orders

import android.Manifest
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import io.melton.foodsbycodechallenge.R
import io.melton.foodsbycodechallenge.base.BaseActivity
import io.melton.foodsbycodechallenge.data.model.Dropoff
import io.melton.foodsbycodechallenge.databinding.ActivityOrdersBinding
import io.melton.foodsbycodechallenge.di.foodsby.FoodsbyActivityComponent
import io.melton.foodsbycodechallenge.extensions.address
import android.databinding.DataBindingUtil
import android.graphics.Canvas
import android.graphics.Rect
import android.graphics.drawable.Drawable
import android.graphics.drawable.GradientDrawable
import android.graphics.drawable.InsetDrawable
import android.support.v7.widget.LinearLayoutManager
import timber.log.Timber
import java.util.*
import javax.inject.Inject
import devs.mulham.horizontalcalendar.HorizontalCalendar
import devs.mulham.horizontalcalendar.utils.HorizontalCalendarListener
import io.melton.foodsbycodechallenge.view.orders.recycler.OrdersAdapter
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.DividerItemDecoration
import android.util.TypedValue
import android.view.View
import android.widget.LinearLayout.HORIZONTAL
import io.melton.foodsbycodechallenge.android.view.decorators.DividerItemDecorator


/**
 * Created by Alexander Melton on 6/18/2018.
 */
class OrdersActivity: BaseActivity(){

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private lateinit var ordersViewModel: OrdersViewModel
    private lateinit var recyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        ordersViewModel = ViewModelProviders.of(this, viewModelFactory).get(OrdersViewModel::class.java)
        val binding: ActivityOrdersBinding = DataBindingUtil.setContentView(this, R.layout.activity_orders)
        recyclerView = binding.dropoffRecyclerView

        // This is super clear what is happening, and I love it :+1:
        setupRecyclerView()
        checkForLocationPermission()
        buildCalendar()

        // View model work is dope :+1:
        ordersViewModel.viewmodeldata.observe(this, Observer { binding.viewmodel = it })
        ordersViewModel.getDropOffs()
    }

    fun buildCalendar(){
        /* starts before 1 month from now */
        val startDate = Calendar.getInstance()
        startDate.add(Calendar.MONTH, -1)

        /* ends after 1 month from now */
        val endDate = Calendar.getInstance()
        endDate.add(Calendar.MONTH, 1)

        val horizontalCalendar = HorizontalCalendar.Builder(this, R.id.calendarView)
                .range(startDate, endDate)
                .datesNumberOnScreen(5)
                .build()

        horizontalCalendar.calendarListener = object : HorizontalCalendarListener() {
            override fun onDateSelected(date: Calendar, position: Int) {
                ordersViewModel.selectDay(date)
            }
        }

    }

    // Formatting in this method is off, I'd do some breaking up of logical components with line breaks here
    fun setupRecyclerView(){
        recyclerView.adapter = ordersViewModel.viewmodeldata.value?.adapter
        val layoutManager = LinearLayoutManager(this)
        recyclerView.layoutManager = layoutManager
        val spacer =  TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 120f, resources.displayMetrics)
        val inset = InsetDrawable(getDrawable(R.drawable.divider), spacer.toInt(), 0, 0, 0)
        val divider = DividerItemDecorator(inset)
        recyclerView.addItemDecoration(divider)
    }

    fun checkForLocationPermission(){
        if (ContextCompat.checkSelfPermission(this,
                        Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            // Permission is not granted
            ActivityCompat.requestPermissions(this,
                    arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                    0)
        } else {
            // Permission has already been granted
            ordersViewModel.getLocation(this)
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        // I assume you are going to fill out these comments with code?
        when(requestCode){
            0 -> {
                // If request is cancelled, the result arrays are empty.
                if ((grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                    // permission was granted
                    ordersViewModel.getLocation(this)
                } else {
                    // permission denied
                }
                return
            }
        }
    }

    override fun injectSelf(component: FoodsbyActivityComponent) = component.inject(this)

}