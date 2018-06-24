package io.melton.foodsbycodechallenge.data.repository.dropoff

import io.melton.foodsbycodechallenge.data.model.Dropoff
import java.io.Serializable

/**
 * Created by Alexander Melton on 6/19/2018.
 */
data class DropoffsEntity(
        val dropoffs: List<Dropoff> = emptyList()
)