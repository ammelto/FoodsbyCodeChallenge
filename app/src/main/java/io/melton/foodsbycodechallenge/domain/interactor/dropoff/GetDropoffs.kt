package io.melton.foodsbycodechallenge.domain.interactor.dropoff

import io.melton.foodsbycodechallenge.data.model.Dropoff
import io.melton.foodsbycodechallenge.data.repository.dropoff.DropoffsRepository
import io.melton.foodsbycodechallenge.domain.interactor.UseCase
import io.melton.foodsbycodechallenge.domain.interactor.UseCase.None
import javax.inject.Inject


/**
 * Created by Alexander Melton on 6/19/2018.
 */
class GetDropoffs
@Inject constructor(private val dropoffsRepository: DropoffsRepository) : UseCase<List<Dropoff>, None>() {

    // Same thing I said for GetLocation
    override suspend fun run(params: None) = dropoffsRepository.dropoffs()
}