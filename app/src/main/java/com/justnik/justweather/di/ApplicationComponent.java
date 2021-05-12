package com.justnik.justweather.di;

import android.app.Application;
import android.content.Context;

import com.justnik.justweather.annotation.ApplicationContext;
import com.justnik.justweather.api.WeatherAPI;
import com.justnik.justweather.repository.WeatherRepository;
import com.justnik.justweather.ui.MainActivity;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {ApplicationModule.class,ApiModule.class, ViewModelFactoryModule.class,WeatherViewModelModule.class})
public interface ApplicationComponent {
    void inject(WeatherApplication weatherApplication);
    void inject(MainActivity mainActivity);

    @ApplicationContext
    Context getContext();

    Application getApplication();

    WeatherAPI getWeatherAPI();

    WeatherRepository getWeatherRepository();


}