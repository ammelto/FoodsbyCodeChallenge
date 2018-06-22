package io.melton.foodsbycodechallenge.data.repository

import com.google.android.gms.tasks.Task
import io.melton.foodsbycodechallenge.data.functional.Either
import io.melton.foodsbycodechallenge.domain.exceptions.Failure
import io.melton.foodsbycodechallenge.extensions.await
import kotlinx.coroutines.experimental.async
import org.junit.internal.runners.statements.Fail
import retrofit2.Call
import timber.log.Timber

/**
 * Created by Alexander Melton on 6/19/2018.
 */
// I like that you are wrapping exceptions as failures in an actual object :+1: Good pattern fam
interface Repository{
    fun <T, R> request(call: Call<T>, transform: (T) -> R, default: T): Either<Failure, R> {
        return try {
            val response = call.execute()
            Timber.d(response.message())
            when (response.isSuccessful) {
                true -> Either.Right(transform((response.body() ?: default)))
                false -> Either.Left(Failure.ServerError())
            }
        } catch (exception: Throwable) {
            Either.Left(Failure.ServerError())
        }
    }

    suspend fun <T, R> request(task: Task<T>, transform: (T) -> R, default: T): Either<Failure, R>{
        return try{
            val response: T = task.await()
            Timber.d(response.toString())
            when(task.isSuccessful){
                true -> Either.Right(transform(response ?: default))
                else -> Either.Left(Failure.ServerError())
            }
        }catch (e: Exception){
            Either.Left(Failure.ServerError())
        }
    }
}