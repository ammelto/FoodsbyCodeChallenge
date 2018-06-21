package io.melton.foodsbycodechallenge.di.viewmodel;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;

import dagger.Binds;
import dagger.Module;
import dagger.multibindings.IntoMap;
import io.melton.foodsbycodechallenge.view.orders.OrdersViewModel;

@Module
public abstract class ViewModelBindsModule {
    @Binds
    @IntoMap
    @ViewModelKey(OrdersViewModel.class)
    abstract ViewModel bindOrdersViewModel(OrdersViewModel ordersViewModel);

    @Binds
    abstract ViewModelProvider.Factory bindViewModelFactory(FoodsbyViewModelFactory factory);
}
