package com.justnik.justweather.ui;

import android.content.Intent;
import android.content.res.Resources;
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

public class MainActivity extends AppCompatActivity{

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


    }

    private void initNavController() {
        NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.navHostFragment);

        navController = navHostFragment.getNavController();
        navController.addOnDestinationChangedListener((controller, destination, arguments) -> {
            if (destination.getId() == R.id.aboutFragment) {
                enabled = true;
            }
        });
    }

    private void setupBottomNav() {
        BottomNavigationView bottomNavigation = findViewById(R.id.bottomNavigationView);
        NavigationUI.setupWithNavController(bottomNavigation, navController);
        bottomNavigation.setOnNavigationItemSelectedListener(item -> {
            if (item.getItemId()==R.id.shareFragment){

                Forecast forecast = viewModel.forecast.getValue();

                if (forecast!=null){

                    Intent shareIntent = new Intent(Intent.ACTION_SEND);
                    shareIntent.setType("text/plain");
                    String builder = "Check out today's weather!" +
                            "\nTemperature: " +
                            forecast.getMain().getTemp() +
                            "Clouds: " +
                            forecast.getWeather().get(0).getDescription() +
                            "\nCheck more on JustWeather!";
                    shareIntent.putExtra(Intent.EXTRA_TEXT, builder);
                    Intent intent = Intent.createChooser(shareIntent,"Share");
                    startActivity(intent);
                }
                return true;

            }
            navController.navigate(item.getItemId());
            return true;
        });


    }

    private void setupDrawer() {
        mDrawerLayout = findViewById(R.id.drawerLayout);
        AppBarConfiguration appBarConfiguration =
                new AppBarConfiguration.Builder(R.id.weeklyFragment, R.id.todayFragment)
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
                    viewModel.refreshForecast(s,getApplicationContext());
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

}