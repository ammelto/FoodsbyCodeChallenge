package io.melton.foodsbycodechallenge.data.repository.dropoff

import io.melton.foodsbycodechallenge.android.network.NetworkHandler
import io.melton.foodsbycodechallenge.data.functional.Either
import io.melton.foodsbycodechallenge.data.model.Dropoff
import io.melton.foodsbycodechallenge.data.repository.Repository
import io.melton.foodsbycodechallenge.domain.exceptions.Failure
import javax.inject.Inject

/**
 * Created by Alexander Melton on 6/19/2018.
 */
interface DropoffsRepository: Repository {
    fun dropoffs(): Either<Failure, List<Dropoff>>

    class Network
    @Inject constructor(private val networkHandler: NetworkHandler,
                        private val service: DropoffService): DropoffsRepository{
        override fun dropoffs(): Either<Failure, List<Dropoff>> {
            return when(networkHandler.isConnected) {
                true -> request(service.dropoffs(), { it.dropoffs }, DropoffsEntity())
                else -> Either.Left(Failure.NetworkConnection())
            }
        }

    }
}