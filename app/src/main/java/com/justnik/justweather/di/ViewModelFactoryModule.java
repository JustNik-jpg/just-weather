package com.justnik.justweather.di;

import androidx.lifecycle.ViewModelProvider;

import com.justnik.justweather.ui.WeatherViewModelFactory;

import dagger.Binds;
import dagger.Module;

@Module
public abstract class ViewModelFactoryModule {

    @Binds
    public abstract ViewModelProvider.Factory bindViewModelFactory(WeatherViewModelFactory daggerViewModelFactory);

//    Both are same (above is same as below using @Provides annotation).
//    @Provides
//    static ViewModelProvider.Factory bindViewModelFactory2(ViewModelProviderFactory factory) {
//        return factory;
//    }


}
