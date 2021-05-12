package com.justnik.justweather.ui;

import androidx.lifecycle.ViewModel;

import com.justnik.justweather.model.json.Forecast;
import com.justnik.justweather.repository.WeatherRepository;

import javax.inject.Inject;

import io.reactivex.rxjava3.core.Observable;
import retrofit2.Response;

public class WeatherViewModel extends ViewModel {

    private WeatherRepository repository;
    private Observable<Response<Forecast>> forecast;

    @Inject
    public WeatherViewModel(WeatherRepository repository) {
        this.repository = repository;
    }

    public Observable<Response<Forecast>> loadForecast(String city){
        forecast = repository.getForecast(city);
        return forecast;
    }

    public Observable<Response<Forecast>> getForecast() {
        return forecast;
    }
}
