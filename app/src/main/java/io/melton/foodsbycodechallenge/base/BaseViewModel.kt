package io.melton.foodsbycodechallenge.base

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import io.melton.foodsbycodechallenge.domain.exceptions.Failure
import java.io.Serializable

/**
 * Created by Alexander Melton on 6/19/2018.
 */
// Why serializable, also, this doesn't seem to be doing much of anything.
// Why not just extend view model for each class and have a separate interface for this?
abstract class BaseViewModel: ViewModel(), Serializable{
    var failure: MutableLiveData<Failure> = MutableLiveData()

    protected fun handleFailure(failure: Failure) {
        this.failure.value = failure
    }
}