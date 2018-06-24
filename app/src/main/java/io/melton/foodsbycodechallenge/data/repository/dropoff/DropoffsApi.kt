package io.melton.foodsbycodechallenge.data.repository.dropoff

/**
 * Created by Alexander Melton on 6/19/2018.
 */

import com.google.gson.annotations.SerializedName
import kotlinx.coroutines.experimental.Deferred
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET

interface DropoffsApi {
    companion object {
        private const val DROPOFFS = "/dropoff/"
    }

    @SerializedName("dropoffs") @GET(DROPOFFS) fun dropoffs(): Deferred<DropoffsEntity>
}