package io.melton.foodsbycodechallenge.data.functional

/**
 * Created by Alexander Melton on 6/19/2018.
 *
 * Represents a value of one of two possible types (a disjoint union).
 * Instances of [Either] are either an instance of [Left] or [Right].
 * FP Convention dictates that [Left] is used for "failure"
 * and [Right] is used for "success".
 *
 * @see Left
 * @see Right
 */
// This is just an implementation of Pair, it seems like an unnecessary abstraction to me
// I see what you are going for here, making a generic wrapper for success and failure.
// If so, you should just outright say it is failure or success, don't use vague terms like left and right
// They don't mean anything. Also the term Either is vague, give it a term that shows it is a wrapper for
// payloads that can return errors. ( Naming is hard /shrug)
sealed class Either<out L, out R> {
    /** * Represents the left side of [Either] class which by convention is a "Failure". */
    // If you have a pair, where one side is an item, and one side is Nothing, then that is just a single item, I don't see why these two classes are necessary
    // You should just make functions that can create the wrapper out of an error or out of a success, then have values that check for errors.
    data class Left<out L>(val a: L) : Either<L, Nothing>()
    /** * Represents the right side of [Either] class which by convention is a "Success". */
    data class Right<out R>(val b: R) : Either<Nothing, R>()

    // This check is very heavy for just determining if there is an error or not, there shouldn't be a need to perform any casting
    val isRight get() = this is Right<R>
    val isLeft get() = this is Left<L>

    fun <L> left(a: L) = Either.Left(a)
    fun <R> right(b: R) = Either.Right(b)

    fun either(fnL: (L) -> Any, fnR: (R) -> Any): Any =
            when (this) {
                is Left -> fnL(a)
                is Right -> fnR(b)
            }
}

// Credits to Alex Hart -> https://proandroiddev.com/kotlins-nothing-type-946de7d464fb
// Composes 2 functions
// What in the absolute fuck, like, I know what this is doing, but this looks awful
fun <A, B, C> ((A) -> B).c(f: (B) -> C): (A) -> C = {
    f(this(it))
}

// You aren't using the two methods below, these are incredibly confusing on their own anyway.
fun <T, L, R> Either<L, R>.flatMap(fn: (R) -> Either<L, T>): Either<L, T> =
        when (this) {
            is Either.Left -> Either.Left(a)
            is Either.Right -> fn(b)
        }

// Conceptually, this doesn't make sense. Map would be a subset of flatMap, not the other way around
// A flatMap is a map, that also flattens the collection out. In this case, you don't even really have a collection,
// So their is nothing to flatten
fun <T, L, R> Either<L, R>.map(fn: (R) -> (T)): Either<L, T> = this.flatMap(fn.c(::right))