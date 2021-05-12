package com.justnik.justweather.di;

import androidx.lifecycle.ViewModel;

import com.justnik.justweather.annotation.ViewModelKey;
import com.justnik.justweather.ui.WeatherViewModel;

import dagger.Binds;
import dagger.Module;
import dagger.multibindings.IntoMap;

@Module
public abstract class WeatherViewModelModule {
    @Binds
    @IntoMap
    @ViewModelKey(WeatherViewModel.class)
    public abstract ViewModel bindAuthViewModel(WeatherViewModel viewModel);
}
