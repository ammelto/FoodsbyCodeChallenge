package io.melton.foodsbycodechallenge.di.contract

import io.melton.foodsbycodechallenge.di.foodsby.FoodsbyActivityComponent

/**
 * Created by Alexander Melton on 6/18/2018.
 *
 * Boilerplate functionality that is required by Dagger2's architecture.
 *
 * This interface isn't needed by dagger, but is used in our base classes to
 * enforce that the activities inject themselves, so you don't forget.
 *
 * It's useful for teams to enforce DI usage.
 *
 */
interface DaggerActivityAware {
    /**
     * Boilerplate required by Dagger2 for injecting the current object.
     *
     * This method is just boilerplate and should always be implemented exactly as:
     *
     * public void injectSelf(ActivityComponent component) {
     * component.inject(this);
     * }
     *
     * Make sure the class is added as an explicit method in the
     * `ActivityComponent` class.
     *
     * @see FoodsbyActivityComponent
     *
     * @param component Activity-level Dependency Component for doing injections.
     */
    fun injectSelf(component: FoodsbyActivityComponent)
}
