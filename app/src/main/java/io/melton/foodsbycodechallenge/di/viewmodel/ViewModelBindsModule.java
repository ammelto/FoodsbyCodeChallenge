package io.melton.foodsbycodechallenge.di.viewmodel;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;

import dagger.Binds;
import dagger.Module;
import dagger.multibindings.IntoMap;
import io.melton.foodsbycodechallenge.view.orders.OrdersViewModel;

// Why is this in java?
@Module
public abstract class ViewModelBindsModule {

    // Multibinds :+1:
    @Binds
    @IntoMap
    @ViewModelKey(OrdersViewModel.class)
    abstract ViewModel bindOrdersViewModel(OrdersViewModel ordersViewModel);

    @Binds
    abstract ViewModelProvider.Factory bindViewModelFactory(FoodsbyViewModelFactory factory);
}
