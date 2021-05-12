package com.justnik.justweather.ui;

import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import com.justnik.justweather.R;
import com.justnik.justweather.di.WeatherApplication;
import com.justnik.justweather.model.json.Forecast;
import com.justnik.justweather.ui.fragment.CitySelectDialogFragment;
import com.justnik.justweather.util.SearchDialogClickListener;

import javax.inject.Inject;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private NavController navController;
    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle toggle;
    private boolean enabled;
    private WeatherViewModel viewModel;

    @Inject
    WeatherViewModelFactory factory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ((WeatherApplication)getApplication()).getComponent().inject(this);
        initNavController();
        viewModel = new ViewModelProvider(getViewModelStore(),factory).get(WeatherViewModel.class);
        setupDrawer();
        setupBottomNav();

        if (viewModel.getForecast()==null){
            loadForecast("Kyiv");
        }

    }

    private void initNavController() {
        NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.navHostFragment);

        navController = navHostFragment.getNavController();
        navController.addOnDestinationChangedListener((controller, destination, arguments) -> {
            if ((destination.getId() == R.id.settingsFragment) || (destination.getId() == R.id.aboutFragment)) {
                enabled = true;
            }
        });
    }

    private void setupBottomNav() {
        BottomNavigationView bottomNavigation = findViewById(R.id.bottomNavigationView);
        NavigationUI.setupWithNavController(bottomNavigation, navController);
    }

    private void setupDrawer() {
        mDrawerLayout = findViewById(R.id.drawerLayout);
        AppBarConfiguration appBarConfiguration =
                new AppBarConfiguration.Builder(R.id.weeklyFragment, R.id.todayFragment, R.id.shareFragment)
                        .setOpenableLayout(mDrawerLayout)
                        .build();

        NavigationView navView = findViewById(R.id.navView);
        NavigationUI.setupWithNavController(navView, navController);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);

        toggle = new ActionBarDrawerToggle(this, mDrawerLayout, R.string.nav_app_bar_open_drawer_description, R.string.nav_app_bar_navigate_up_description);
        mDrawerLayout.addDrawerListener(toggle);
        toggle.syncState();

    }

    @Override
    public boolean onSupportNavigateUp() {
        return NavigationUI.navigateUp(navController, mDrawerLayout) || super.onSupportNavigateUp();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        System.out.println("Option selected");
        if (toggle.onOptionsItemSelected(item)) {
            if (enabled) {
                enabled = false;
                onSupportNavigateUp();
                mDrawerLayout.close();
            }

            return true;
        } else if (item.getItemId()==R.id.miSearch){
            CitySelectDialogFragment dialog = new CitySelectDialogFragment();
            dialog.setListener(new SearchDialogClickListener() {
                @Override
                public void onDialogPositiveClick(String s) {
                    Log.d("JustDialog", "onDialogPositiveClick: "+s);
                }

                @Override
                public void onDialogNegativeClick() {
                    Log.d("JustDialog", "onDialogNegativeClick: ");
                }
            });
            dialog.show(getSupportFragmentManager(),"CitySearchDialogFragment");
        }
        return super.onOptionsItemSelected(item);

    }

    public void loadForecast(String s){
         viewModel.loadForecast(s).subscribeOn(Schedulers.io())
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
                });
    }

}