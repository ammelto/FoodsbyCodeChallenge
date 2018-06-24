package io.melton.foodsbycodechallenge.domain.interactor.dropoff

import io.melton.foodsbycodechallenge.data.model.Dropoff
import io.melton.foodsbycodechallenge.data.repository.dropoff.DropoffsRepository
import io.melton.foodsbycodechallenge.domain.interactor.AsyncUseCase
import javax.inject.Inject


/**
 * Created by Alexander Melton on 6/19/2018.
 */
class GetDropoffs @Inject constructor(private val dropoffsRepository: DropoffsRepository) : AsyncUseCase<List<Dropoff>> {

    override suspend fun run() = dropoffsRepository.dropoffs()
}