package com.justnik.justweather.di;

import com.justnik.justweather.api.WeatherAPI;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

@Module
public class ApiModule {

    private final String BASE_URL = "https://api.openweathermap.org/data/2.5/";

    @Singleton
    @Provides
    public WeatherAPI provideWeatherApi(){
        return new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())

                .build()
                .create(WeatherAPI.class);

    }
}
