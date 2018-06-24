package io.melton.foodsbycodechallenge.view.orders

import android.Manifest
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.content.pm.PackageManager
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import io.melton.foodsbycodechallenge.R
import io.melton.foodsbycodechallenge.base.BaseActivity
import io.melton.foodsbycodechallenge.databinding.ActivityOrdersBinding
import io.melton.foodsbycodechallenge.di.foodsby.FoodsbyActivityComponent
import android.databinding.DataBindingUtil
import android.graphics.drawable.InsetDrawable
import android.support.v7.widget.LinearLayoutManager
import java.util.*
import javax.inject.Inject
import devs.mulham.horizontalcalendar.HorizontalCalendar
import devs.mulham.horizontalcalendar.utils.HorizontalCalendarListener
import io.melton.foodsbycodechallenge.view.orders.recycler.OrdersAdapter
import android.support.v7.widget.RecyclerView
import android.util.TypedValue
import io.melton.foodsbycodechallenge.android.view.decorators.DividerItemDecorator
import io.melton.foodsbycodechallenge.domain.exceptions.Failure


/**
 * Created by Alexander Melton on 6/18/2018.
 *
 * Activity that displays the orders
 *
 * The idea here is that the Activity only handles android specific things.
 * We bind the layout to the activity. That makes the activity act as sort of the 'glue' between the ViewModel and the Layout
 * The ViewModel has no reference to any android specific things and should work only with POKOs
 */
class OrdersActivity: BaseActivity(){

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private lateinit var ordersViewModel: OrdersViewModel
    private lateinit var recyclerView: RecyclerView
    private lateinit var ordersAdapter: OrdersAdapter
    private lateinit var binding: ActivityOrdersBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //Ask the factory to get the generated view model of this activity then bind it to the layout
        //This way we can pretend 'find view by id' doesn't exist and we also get a bunch of cool separation of data which allows for some slick code reuse
        ordersViewModel = ViewModelProviders.of(this, viewModelFactory).get(OrdersViewModel::class.java)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_orders)
        recyclerView = binding.dropoffRecyclerView

        setupRecyclerView()
        buildCalendar()
        setupSwipeToRefresh()

        //Now that the UI is setup, let's connect the viewmodel to the layout.
        //This is where we 'plug it in' so to speak
        ordersViewModel.viewModelData.observe(this, Observer { binding.viewmodel = it })
        ordersViewModel.viewModelData.observe( this , ordersAdapter.inputObserver)
        ordersViewModel.failure.observe(this, Observer { handleFailStates(it) })

        //Now that we're connected to the domain layer, let's get some I/O
        checkForLocationPermission()
        ordersViewModel.getDropOffs()

    }

    /**
     * The horizontal calender view needs to know what days to load,
     * so we construct that here. The data I was provided is only Monday through Sunday so
     * the calendar just cycles through those based on the day. It's implemented like this since
     * I imagine in production a client will want to see orders more than 5 days in advanced
     */
    private fun buildCalendar(){
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

    /**
     * All we really need to do here is setup the refresh listener.
     * Viewbinding does the rest for us
     */
    private fun setupSwipeToRefresh(){
        binding.swiperefresh.setOnRefreshListener {
            ordersViewModel.getDropOffs()
        }
    }

    /**
     * Builds out the recycler view to the Material Design spec
     */
    private fun setupRecyclerView(){
        //Instantiate adapter, then set it
        ordersAdapter = OrdersAdapter()
        recyclerView.adapter = ordersAdapter

        //Give our recycler a layout manager
        val layoutManager = LinearLayoutManager(this)
        recyclerView.layoutManager = layoutManager

        //Now we do a bunch of dumb stuff because android decorators are dumb
        //and Google hasnt given us tools for the new Material spec yet.
        //Reference (Three item list with a picture): https://material.io/design/components/lists.html#specs
        val spacer =  TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 120f, resources.displayMetrics)
        val inset = InsetDrawable(getDrawable(R.drawable.divider), spacer.toInt(), 0, 0, 0)
        val divider = DividerItemDecorator(inset)
        recyclerView.addItemDecoration(divider)
    }

    /**
     * We check to make sure we have location permissions, and if so we fetch the location.
     * If we don't, we'll go ahead and request it
     */
    private fun checkForLocationPermission(){
        if (ContextCompat.checkSelfPermission(this,
                        Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            // Permission is not granted
            ActivityCompat.requestPermissions(this,
                    arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                    0)
        } else {
            // Permission has already been granted
            ordersViewModel.getLocation()
        }
    }

    /**
     * On permission result, if the user gives us permission to get the location then we tell the viewmodel to get it.
     * If we dont, then we tell the viewmodel we were denied.
     */
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        // If request is cancelled, the result arrays are empty.
        when((grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)){
            true -> when(requestCode){
                0 -> ordersViewModel.getLocation()
            }
            else -> ordersViewModel.handlePermissionDenied(0)
        }

    }

    /**
     * This is the failure handler for the Activity view logic we need to relay to the user an error has happened.
     * The activity doesnt know anything unless the viewmodel tells us. So, it's up to the viewmodel to say 'yo dawg, something broke'
     * then we can handle it from a UI perspective here.
     */
    private fun handleFailStates(failure: Failure?){
        when(failure){
            is AddressReceiveFailure -> handleAddressReceiveFailure(failure)
            is AddressPermissionFailure -> handleAddressPermissionFailure(failure)
        }
    }

    /**
     * This allows us to write the error message to the test values so the user can see it without
     * actually storing the error messages as the address value in the viewmodel.
     * Doing this keeps us from overriding existing values with error messages. It's more useful for cached values or form data, but still good practice
     * The failure and data streams should never 'cross' so to speak.
     */
    private fun handleAddressReceiveFailure(failure: AddressReceiveFailure){
        binding = binding.apply {
            addressLineTop.text = resources.getText(failure.topAddressLine)
            addressLineBottom.text = resources.getText(failure.bottomAddressLine)
        }
    }
    //
    private fun handleAddressPermissionFailure(failure: AddressPermissionFailure){
        binding = binding.apply {
            addressLineTop.text = resources.getText(failure.topAddressLine)
            addressLineBottom.text = resources.getText(failure.bottomAddressLine)
        }
    }

    /**
     * Inject ourself so we can get DI stuff
     */
    override fun injectSelf(component: FoodsbyActivityComponent) = component.inject(this)
}