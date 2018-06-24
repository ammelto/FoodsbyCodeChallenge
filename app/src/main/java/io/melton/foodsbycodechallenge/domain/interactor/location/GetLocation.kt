package io.melton.foodsbycodechallenge.domain.interactor.location

import io.melton.foodsbycodechallenge.data.model.Address
import io.melton.foodsbycodechallenge.data.repository.location.LocationRepository
import io.melton.foodsbycodechallenge.domain.interactor.AsyncUseCase
import javax.inject.Inject

/**
 * Created by Alexander Melton on 6/20/2018.
 */
class GetLocation @Inject constructor(private val locationRepository: LocationRepository) : AsyncUseCase<Address> {

    override suspend fun run() = locationRepository.location()
}