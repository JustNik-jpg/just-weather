package com.justnik.justweather.api;

import com.justnik.justweather.model.json.Forecast;

import io.reactivex.rxjava3.core.Observable;
import retrofit2.Response;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface WeatherAPI {

    @GET("weather/")
    Observable<Response<Forecast>> getWeatherForecast(@Query("q") String city,
                                                      @Query("units") String units,
                                                      @Query("appid") String appid);


}
