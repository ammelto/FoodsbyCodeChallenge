package io.melton.foodsbycodechallenge.view.orders

import android.arch.lifecycle.MutableLiveData
import android.content.Context
import android.location.Address
import io.melton.foodsbycodechallenge.base.BaseViewModel
import io.melton.foodsbycodechallenge.data.model.Delivery
import io.melton.foodsbycodechallenge.data.model.Dropoff
import io.melton.foodsbycodechallenge.domain.exceptions.Failure
import io.melton.foodsbycodechallenge.domain.interactor.UseCase
import io.melton.foodsbycodechallenge.domain.interactor.dropoff.GetDropoffs
import io.melton.foodsbycodechallenge.domain.interactor.location.GetLocation
import io.melton.foodsbycodechallenge.extensions.address
import timber.log.Timber
import java.util.*
import javax.inject.Inject

/**
 * Created by Alexander Melton on 6/18/2018.
 */
class OrdersViewModel @Inject internal constructor(private val getDropoffs: GetDropoffs, private val getLocation: GetLocation): BaseViewModel(){

    var viewmodeldata: MutableLiveData<OrdersView> = MutableLiveData()

    init {
        viewmodeldata.value = OrdersView()
    }

    fun getDropOffs(){
        // consistency in method refs :+1:
        getDropoffs.execute({
            it.either(::handleFailure, ::handleDropOffs)
        }, UseCase.None())
    }

    fun getLocation(context: Context){
        // Make use of method references consistent here
        getLocation.execute({
            it.either(::handleLocationFailure, {location ->  handleLocation(location.address(context))})
        }, UseCase.None())
    }

    fun selectDay(date: Calendar){
        // Use `copy`
        this.viewmodeldata.value = this.viewmodeldata.value?.apply {
            this.selectedDay = date
            // This argument for swap data should be assigned to a val for naming before passed, less confusing that way
            this.adapter.swapData(dropoffs.firstOrNull {
                it.day == date.getDisplayName(Calendar.DAY_OF_WEEK, Calendar.LONG, Locale.getDefault())
            }?.deliveries)
        }
    }

    private fun handleDropOffs(dropoffs: List<Dropoff>){
        // Don't use `apply`, use `copy`
        this.viewmodeldata.value = this.viewmodeldata.value?.apply {
            this.dropoffs = dropoffs
            // This argument for swap data should be assigned to a val for naming before passed, less confusing that way
            this.adapter.swapData(dropoffs.firstOrNull {
                it.day == this.selectedDay.getDisplayName(Calendar.DAY_OF_WEEK, Calendar.LONG, Locale.getDefault())
            }?.deliveries)
        }
    }


    private fun handleLocation(address: Address){
        Timber.d("ADDRESS IS $address")
        this.viewmodeldata.value = this.viewmodeldata.value?.apply {
            // Do this decision making outside of apply, then use `copy` instead
            if(address.maxAddressLineIndex == 0) this.topAddressLine = address.getAddressLine(0)
            if(address.maxAddressLineIndex > 0) this.bottomAddressLine = address.getAddressLine(1)
            //Fills in county if there isn't a second address line
            else this.bottomAddressLine = address.subAdminArea
        }
    }

    // You don't actually use this `failure` parameter
    private fun handleLocationFailure(failure: Failure) {
        // Use `.copy` with named params instead of apply here
        this.viewmodeldata.value = this.viewmodeldata.value.apply {
            this?.topAddressLine = "Unfortunately location services are unavailable"
            this?.bottomAddressLine = "Please check your network"
        }
    }
}