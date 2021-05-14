package com.justnik.justweather.ui;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.justnik.justweather.R;
import com.justnik.justweather.model.json.Forecast;
import com.justnik.justweather.repository.WeatherRepository;

import javax.inject.Inject;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import io.reactivex.rxjava3.subjects.BehaviorSubject;
import retrofit2.Response;

public class WeatherViewModel extends ViewModel {

    private WeatherRepository repository;
    public MutableLiveData<Forecast> forecast;

    @Inject
    public WeatherViewModel(WeatherRepository repository) {
        this.repository = repository;
        forecast = new MutableLiveData<>();
    }

    public void refreshForecast(String city, Context context) {
        Observable<Forecast> response = repository.getForecast(city);

        response.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new Observer<Forecast>() {


                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onNext(@NonNull Forecast forecastResponse) {
                        Log.d("JustInternet", "onNext: " + forecastResponse);
                        forecast.setValue(forecastResponse);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        Log.d("JustInternet", "onError: " + e.getMessage());
                        Toast.makeText(context, context.getString(R.string.invalid_city), Toast.LENGTH_SHORT).show();

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }


}
