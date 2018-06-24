package io.melton.foodsbycodechallenge.domain.exceptions

/**
 * Created by Alexander Melton on 6/19/2018.
 *
 * Base Class for handling errors/failures/exceptions.
 * Every feature specific failure should extend [FeatureFailure] class.
 */
interface Failure {
    class NetworkConnection: Failure
    class ServerError: Failure

    /** * Extend this class for feature specific failures.*/
    abstract class FeatureFailure: Failure
}