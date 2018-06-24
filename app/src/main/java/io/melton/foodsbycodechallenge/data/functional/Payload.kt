package io.melton.foodsbycodechallenge.data.functional

/**
 * Created by Alexander Melton on 6/19/2018.
 *
 * Represents a value of one of two possible types (a disjoint union).
 * Instances of [Either] are either an instance of [Failure] or [Success].
 * FP Convention dictates that [Failure] is used for "failure"
 * and [Success] is used for "success".
 *
 * This is a bit overkill for a payload in this app, but it's just to show what my implementation of a Monad would be.
 *
 * @see Failure
 * @see Success
 */
sealed class Either<out L, out R> {
    enum class Type{
        FAILURE,
        SUCCESS
    }
    var type: Type? = null
    /** * Represents the failed side of [Either] class which by convention is a "Failure". */
    data class Failure<out L>(val a: L) : Either<L, Nothing>(){
        init {
            this.type = Type.FAILURE
        }
    }
    /** * Represents the succeeded side of [Either] class which by convention is a "Success". */
    data class Success<out R>(val b: R) : Either<Nothing, R>(){
        init {
            this.type = Type.SUCCESS
        }
    }

    fun <L> failed(a: L) = Either.Failure(a)
    fun <R> succeeded(b: R) = Either.Success(b)

    fun either(fnL: (L) -> Any, fnR: (R) -> Any): Any =
            when (this) {
                is Failure -> fnL(a)
                is Success -> fnR(b)
            }
}