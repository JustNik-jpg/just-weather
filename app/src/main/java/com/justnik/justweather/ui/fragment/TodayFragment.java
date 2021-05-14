package com.justnik.justweather.ui.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.justnik.justweather.R;
import com.justnik.justweather.databinding.FragmentTodayBinding;
import com.justnik.justweather.di.WeatherApplication;
import com.justnik.justweather.model.json.Forecast;
import com.justnik.justweather.ui.WeatherViewModel;
import com.justnik.justweather.ui.WeatherViewModelFactory;

import javax.inject.Inject;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import retrofit2.Response;

public class TodayFragment extends Fragment {
    @Inject
    WeatherViewModelFactory factory;

    private WeatherViewModel viewModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        ((WeatherApplication) requireActivity().getApplication()).getComponent().inject(this);
        //View view = inflater.inflate(R.layout.fragment_today, container, false);
        FragmentTodayBinding binding = DataBindingUtil.inflate(inflater, R.layout.fragment_today, container, false);
        View view = binding.getRoot();

        ImageView ivWeatherIcon = view.findViewById(R.id.ivWeatherIcon);

        viewModel = new ViewModelProvider(requireActivity(), factory).get(WeatherViewModel.class);

        viewModel.refreshForecast("Kyiv", getContext());
        viewModel.forecast.observe(getViewLifecycleOwner(), forecast -> {
            Log.d("JustToday", "onCreateView: " + forecast.toString());
            binding.setForecast(forecast);
            binding.notifyChange();
            ivWeatherIcon.setImageResource(getResources()
                    .getIdentifier("ic__"+forecast.getWeather()
                            .get(0)
                            .getIcon(),"drawable",requireActivity().getPackageName()));

        });


        return view;
    }

}