package io.melton.foodsbycodechallenge.di.viewmodel;

import android.arch.lifecycle.ViewModel;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import dagger.MapKey;
import dagger.multibindings.ClassKey;

/**
 * Used to annotate methods in the Dagger setup that return ViewModels, using the ViewModel implementation
 * class as the key.
 */

// This is only used in one file, so you should probably just put it in that file
@Documented
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@MapKey
@interface ViewModelKey {
    Class<? extends ViewModel> value();
}
