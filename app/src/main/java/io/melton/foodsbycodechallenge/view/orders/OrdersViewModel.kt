package io.melton.foodsbycodechallenge.view.orders

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import io.melton.foodsbycodechallenge.R
import io.melton.foodsbycodechallenge.data.model.Address
import io.melton.foodsbycodechallenge.data.model.Dropoff
import io.melton.foodsbycodechallenge.domain.exceptions.Failure
import io.melton.foodsbycodechallenge.domain.interactor.dropoff.GetDropoffs
import io.melton.foodsbycodechallenge.domain.interactor.location.GetLocation
import timber.log.Timber
import java.util.*
import javax.inject.Inject

/**
 * Created by Alexander Melton on 6/18/2018.
 *
 * View model that binds the data from the domain layer to the view layer
 * This viewmodel doesn't know it's in an Android app. Anything we need to do for the view that's NOT android specific is done here.
 * We create a View that the activity then translates to the layout through view binding.
 */
class OrdersViewModel @Inject internal constructor(private val getDropoffs: GetDropoffs, private val getLocation: GetLocation): ViewModel(){

    //Mutable data stream acts as the view itself
    var viewModelData: MutableLiveData<OrdersView> = MutableLiveData()
    var failure: MutableLiveData<Failure> = MutableLiveData()

    /**
     * If the ViewModel exists, then the View exists. It doesn't make sense otherwise.
     * The view should never ever ever be null.
     */
    init {
        viewModelData.value = OrdersView()
    }

    /**
     * Asks the domain layer for the dropoffs, completely unaware of the domain and data layer
     */
    fun getDropOffs(){
        getDropoffs.execute{
            it.either(::handleGenericFailure, ::handleDropOffs)
        }
    }

    /**
     * Asks the domain layer for the location
     */
    fun getLocation(){
        getLocation.execute{
            it.either(::handleLocationNetworkFailure, ::handleLocation)
        }
    }

    /**
     * Updates the adapter based on the selected day from the horizontal calendar
     */
    fun selectDay(date: Calendar){
        this.viewModelData.value = this.viewModelData.value?.copy(selectedDay = date.getDisplayName(Calendar.DAY_OF_WEEK, Calendar.LONG, Locale.getDefault()))
    }

    /**
     * On permission denied, we create a Failure object and pass it into our failure stream
     * It's up to the Activity how this is handled.
     */
    fun handlePermissionDenied(requestCode: Int){
        when(requestCode){
            0 -> this.failure.value = AddressPermissionFailure(R.string.top_address_permission_error, R.string.bottom_address_permission_error)
        }
    }

    /**
     * Updates the drop offs when new drop offs are received then swaps the data into the adapter
     */
    private fun handleDropOffs(dropoffs: List<Dropoff>){
        this.viewModelData.value = this.viewModelData.value!!.copy(dropoffs = dropoffs)
    }

    /**
     * Updates the location when the new location request resolves
     */
    private fun handleLocation(address: Address){
        when(address.topAddressLine == ""){
            true -> handleGenericFailure(AddressEmptyFailure(R.string.empty_address_error))
            else -> this.viewModelData.value = this.viewModelData.value!!.copy(address = address)
        }
    }

    private fun handleLocationNetworkFailure(failure: Failure){
        this.failure.value = AddressReceiveFailure(R.string.top_address_network_error, R.string.bottom_address_network_error)
    }

    private fun handleGenericFailure(failure: Failure){
        this.failure.value = failure
        Timber.e(failure.toString())
    }
}