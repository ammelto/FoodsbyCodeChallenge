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
 * By convention each [UseCase] implementation will execute its job in a background thread
 * (kotlin coroutine) and will post the result in the UI thread.
 */
// Use cases should have their own language, they define business logic. I feel as though you
// are creating an abstraction around threading, and not making an abstract use case.
// Business logic, imo, works best as an implementation of an interface. If you need something to
// be sync, or async, having it defined as a separate interface makes sense, and you can abstract
// threading logic even further

// Why do you need to clarify Type as Any here?
abstract class UseCase<out Type, in Params> where Type : Any {
    // Why do you need an abstract use case? Use cases should just be interfaces with generics as params
    abstract suspend fun run(params: Params): Either<Failure, Type>

    fun execute(onResult: (Either<Failure, Type>) -> Unit, params: Params) {
        // What if you don't want it to be async?
        val job = async(CommonPool) { run(params) }
        launch(UI) { onResult.invoke(job.await()) }
    }

    // Make a use case interface with no params
    class None
}