package com.justnik.justweather.repository;


import com.justnik.justweather.BuildConfig;
import com.justnik.justweather.api.WeatherAPI;
import com.justnik.justweather.model.json.Forecast;

import javax.inject.Inject;

import io.reactivex.rxjava3.core.Observable;

public class WeatherRepository implements MainRepository{

    WeatherAPI weatherAPI;

    @Inject
    public WeatherRepository(WeatherAPI weatherAPI) {
        this.weatherAPI = weatherAPI;
    }

    @Override
    public Observable<Forecast> getForecast(String city) {
        Observable<Forecast> response = weatherAPI.getWeatherForecast(city,"metric",BuildConfig.API_KEY);

        return response;
    }
}
