package io.melton.foodsbycodechallenge.data.repository.dropoff

/**
 * Created by Alexander Melton on 6/19/2018.
 */

import retrofit2.Retrofit
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DropoffService
@Inject constructor(val dropoffsApi: DropoffsApi) : DropoffsApi {

    override fun dropoffs() = dropoffsApi.dropoffs()
}