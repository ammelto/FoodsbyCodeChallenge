package io.melton.foodsbycodechallenge.base

import android.arch.lifecycle.ViewModel
import android.os.Bundle
import org.json.JSONObject

/**
 * Created by Alexander Melton on 6/18/2018.
 *
 * [ViewModel] used to handle functions common to the [BaseActivity] outside of an
 * Android specific context
 */
class BaseActivityViewModel : ViewModel() {

    /**
     * Converts a bundle to a JSONObject
     */
    fun convertBundleToJsonObject(bundle: Bundle): JSONObject {
        val json = JSONObject()
        val keys = bundle.keySet()
        for (key in keys) {
            json.put(key, JSONObject.wrap(bundle.get(key)))
        }
        return json
    }
}