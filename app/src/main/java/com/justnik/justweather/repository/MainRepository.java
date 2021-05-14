package com.justnik.justweather.repository;


import com.justnik.justweather.model.json.Forecast;

import io.reactivex.rxjava3.core.Observable;
import retrofit2.Response;

public interface MainRepository {

    Observable<Forecast> getForecast(String city);
}
