package io.melton.foodsbycodechallenge.domain.interactor.location

import android.location.Location
import io.melton.foodsbycodechallenge.data.model.Dropoff
import io.melton.foodsbycodechallenge.data.repository.dropoff.DropoffsRepository
import io.melton.foodsbycodechallenge.data.repository.location.LocationRepository
import io.melton.foodsbycodechallenge.domain.interactor.UseCase
import javax.inject.Inject

/**
 * Created by Alexander Melton on 6/20/2018.
 */
class GetLocation
@Inject constructor(private val locationRepository: LocationRepository) : UseCase<Location, UseCase.None>() {

    // If you have no params, you shouldn't have to pass, you could give this a default param to remedy this, although run should really just not have params
    override suspend fun run(params: None) = locationRepository.location()
}