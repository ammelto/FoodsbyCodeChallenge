package io.melton.foodsbycodechallenge.data.repository.location

import android.location.Location
import io.melton.foodsbycodechallenge.FoodsbyCodeChallenge
import io.melton.foodsbycodechallenge.android.network.NetworkHandler
import io.melton.foodsbycodechallenge.data.functional.Either
import io.melton.foodsbycodechallenge.data.model.Address
import io.melton.foodsbycodechallenge.data.repository.Repository
import io.melton.foodsbycodechallenge.domain.exceptions.Failure
import io.melton.foodsbycodechallenge.extensions.address
import io.melton.foodsbycodechallenge.extensions.defer
import io.melton.foodsbycodechallenge.extensions.toFoodsbyAddress
import javax.inject.Inject

/**
 * Created by Alexander Melton on 6/20/2018.
 */
interface LocationRepository: Repository{
    suspend fun location(): Either<Failure, Address>

    class Network @Inject constructor(private val networkHandler: NetworkHandler,
                                      private var locationService: LocationService,
                                      val foodsbyCodeChallenge: FoodsbyCodeChallenge): LocationRepository{
        override suspend fun location(): Either<Failure, Address> {
            return when(networkHandler.isConnected){
                true -> request(locationService.location().defer(), {
                    (it as Location).toFoodsbyAddress(foodsbyCodeChallenge)
                }, Address())
                else -> Either.Failure(Failure.NetworkConnection())
            }
        }
    }
}