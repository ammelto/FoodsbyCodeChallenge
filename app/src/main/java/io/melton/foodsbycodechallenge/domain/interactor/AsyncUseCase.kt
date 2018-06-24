package io.melton.foodsbycodechallenge.domain.interactor

import io.melton.foodsbycodechallenge.data.functional.Either
import io.melton.foodsbycodechallenge.domain.exceptions.Failure
import kotlinx.coroutines.experimental.CommonPool
import kotlinx.coroutines.experimental.android.UI
import kotlinx.coroutines.experimental.async
import kotlinx.coroutines.experimental.launch


/**
 * Created by Alexander Melton on 6/19/2018.
 *
 * Abstract class for a Use Case (Interactor in terms of Clean Architecture).
 * This abstraction represents an execution unit for different use cases (this means than any use
 * case in the application should implement this contract).
 *
 * By convention each [AsyncUseCase] implementation will execute its job in a background thread
 * (kotlin coroutine) and will post the result in the UI thread.
 */
interface AsyncUseCaseInput<out Type, in Params> {

    suspend fun run(params: Params): Either<Failure, Type>

    fun execute(onResult: (Either<Failure, Type>) -> Unit, params: Params) {
        val job = async(CommonPool) { run(params) }
        launch(UI) { onResult.invoke(job.await()) }
    }
}

interface AsyncUseCase<out Type> {

    suspend fun run(): Either<Failure, Type>

    fun execute(onResult: (Either<Failure, Type>) -> Unit) {
        val job = async(CommonPool) { run() }
        launch(UI) { onResult.invoke(job.await()) }
    }
}