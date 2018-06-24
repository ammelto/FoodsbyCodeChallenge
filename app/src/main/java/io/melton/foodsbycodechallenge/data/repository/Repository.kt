package io.melton.foodsbycodechallenge.data.repository

import io.melton.foodsbycodechallenge.data.functional.Either
import io.melton.foodsbycodechallenge.domain.exceptions.Failure
import kotlinx.coroutines.experimental.Deferred

/**
 * Created by Alexander Melton on 6/19/2018.
 */
interface Repository{
    suspend fun <T, R> request(deferred: Deferred<T>, transform: (T) -> R, default: T): Either<Failure, R> {
        return try {
            val response: T = deferred.await()
            when (deferred.isCompletedExceptionally) {
                true -> Either.Failure(Failure.ServerError())
                false -> Either.Success(transform((response ?: default)))
            }
        } catch (exception: Throwable) {
            Either.Failure(Failure.ServerError())
        }
    }
}