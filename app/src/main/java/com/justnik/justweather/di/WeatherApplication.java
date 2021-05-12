package com.justnik.justweather.di;

import android.app.Application;
import android.content.Context;
import android.util.Log;

import com.justnik.justweather.model.json.Forecast;
import com.justnik.justweather.repository.WeatherRepository;

import javax.inject.Inject;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import retrofit2.Response;


public class WeatherApplication extends Application {
    protected ApplicationComponent applicationComponent;
    @Inject
    WeatherRepository repository;

    public static WeatherApplication get(Context context){
        return (WeatherApplication) context.getApplicationContext();
    }

    @Override
    public void onCreate() {
        super.onCreate();


        applicationComponent = DaggerApplicationComponent.builder()
                .applicationModule(new ApplicationModule(this))
                .build();
        applicationComponent.inject(this);


        /*repository.getForecast("Paris")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new Observer<Response<Forecast>>() {


                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onNext(@NonNull Response<Forecast> forecastResponse) {
                        Log.d("JustInternet", "onNext: "+forecastResponse.body()+" \n"+forecastResponse.message());

                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        Log.d("JustInternet", "onError: " + e.getMessage());

                    }

                    @Override
                    public void onComplete() {

                    }
                });*/

    }

    public ApplicationComponent getComponent(){
        return applicationComponent;
    }
}
