package com.justnik.justweather.repository;


import com.justnik.justweather.model.json.Forecast;

import io.reactivex.rxjava3.core.Observable;
import retrofit2.Response;

public interface MainRepository {

    Observable<Response<Forecast>> getForecast(String city);
}
