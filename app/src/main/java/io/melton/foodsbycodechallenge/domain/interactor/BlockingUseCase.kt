package io.melton.foodsbycodechallenge.domain.interactor

import io.melton.foodsbycodechallenge.data.functional.Either
import io.melton.foodsbycodechallenge.domain.exceptions.Failure
import kotlinx.coroutines.experimental.CommonPool
import kotlinx.coroutines.experimental.android.UI
import kotlinx.coroutines.experimental.async
import kotlinx.coroutines.experimental.launch
import kotlinx.coroutines.experimental.runBlocking

/**
 * Created by Alexander Melton on 6/23/2018.
 */
interface BlockingUseCaseInput<out Type, in Params> {

    suspend fun run(params: Params): Either<Failure, Type>

    fun execute(onResult: (Either<Failure, Type>) -> Unit, params: Params) {
        val job = runBlocking { run(params) }
        launch(UI) { onResult.invoke(job) }
    }
}

interface BlockingUseCase<out Type> {

    suspend fun run(): Either<Failure, Type>

    fun execute(onResult: (Either<Failure, Type>) -> Unit) {
        val job = runBlocking { run() }
        launch(UI) { onResult.invoke(job) }
    }
}