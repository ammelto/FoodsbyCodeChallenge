package io.melton.foodsbycodechallenge.android.lifecycle

import java.util.concurrent.atomic.AtomicBoolean

/**
 * Created by Alexander Melton on 6/18/2018.
 *
 * Interface used to track an [AtomicBoolean] that lets consumers check if the app has any activities visible
 */
interface ProcessVisibility  {
    val isVisible : AtomicBoolean
}