package io.melton.foodsbycodechallenge.data.repository.location

import android.location.Location
import io.melton.foodsbycodechallenge.android.network.NetworkHandler
import io.melton.foodsbycodechallenge.data.functional.Either
import io.melton.foodsbycodechallenge.data.repository.Repository
import io.melton.foodsbycodechallenge.domain.exceptions.Failure
import javax.inject.Inject

/**
 * Created by Alexander Melton on 6/20/2018.
 */
interface LocationRepository: Repository{
    suspend fun location(): Either<Failure, Location>

    class Network
    @Inject constructor(private val networkHandler: NetworkHandler, private var locationService: LocationService): LocationRepository{
        override suspend fun location(): Either<Failure, Location> {
            return when(networkHandler.isConnected){
                true -> request(locationService.location(), { it }, Location(""))
                else -> Either.Left(Failure.NetworkConnection())
            }
        }
    }
}