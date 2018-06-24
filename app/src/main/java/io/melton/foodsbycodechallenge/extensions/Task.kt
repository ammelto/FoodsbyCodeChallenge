package io.melton.foodsbycodechallenge.extensions

import com.google.android.gms.tasks.Task
import kotlinx.coroutines.experimental.CompletableDeferred
import kotlinx.coroutines.experimental.Deferred
import kotlinx.coroutines.experimental.async
import kotlin.coroutines.experimental.suspendCoroutine

/**
 * Created by Alexander Melton on 6/20/2018.
 */

@JvmName("awaitVoid")
fun Task<Void>.defer(): Deferred<Unit> {
    val deferred = CompletableDeferred<Unit>()
    this.addOnSuccessListener { deferred.complete({}.invoke()) }
    this.addOnFailureListener { deferred.completeExceptionally(it) }
    return deferred
}

fun <TResult> Task<TResult>.defer(): Deferred<TResult> {
    val deferred = CompletableDeferred<TResult>()
    this.addOnSuccessListener { deferred.complete(it) }
    this.addOnFailureListener { deferred.completeExceptionally(it) }
    return deferred
}