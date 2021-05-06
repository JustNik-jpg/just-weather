package com.justnik.justweather.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toolbar;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import com.justnik.justweather.R;

public class MainActivity extends AppCompatActivity{

    NavController navController;
    DrawerLayout mDrawerLayout;
    ActionBarDrawerToggle toggle;
    boolean enabled;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initNavController();
        System.out.println(getActionBar());

        setupDrawer();
        setupBottomNav();

    }

    private void initNavController(){
        NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.navHostFragment);
        navController = navHostFragment.getNavController();
        navController.addOnDestinationChangedListener((controller, destination, arguments) -> {
            if ((destination.getId()==R.id.settingsFragment)||(destination.getId()==R.id.aboutFragment)){
                enabled=true;
            }
        });
    }

    private void setupBottomNav(){
        BottomNavigationView bottomNavigation = findViewById(R.id.bottomNavigationView);
        NavigationUI.setupWithNavController(bottomNavigation,navController);
    }

    private void setupDrawer(){
        mDrawerLayout = findViewById(R.id.drawerLayout);
        AppBarConfiguration appBarConfiguration =
                new AppBarConfiguration.Builder(R.id.weeklyFragment,R.id.todayFragment,R.id.shareFragment,R.id.aboutFragment,R.id.settingsFragment)
                        .setOpenableLayout(mDrawerLayout)
                        .build();

        NavigationView navView = findViewById(R.id.navView);
        NavigationUI.setupActionBarWithNavController(this,navController,appBarConfiguration);
        NavigationUI.setupWithNavController(navView, navController);
        NavigationUI.setupActionBarWithNavController(this,navController,mDrawerLayout);

        toggle = new ActionBarDrawerToggle(this, mDrawerLayout,R.string.nav_app_bar_open_drawer_description,R.string.nav_app_bar_navigate_up_description);
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
        if (toggle.onOptionsItemSelected(item))
        {
            if (enabled==true){
                enabled=false;
                onSupportNavigateUp();
                mDrawerLayout.close();
            }

            return true;
        }
        return super.onOptionsItemSelected(item);
        /*switch (item.getItemId()) {
            case R.id.miSearch:
                // User chose the "Search" item, show the app settings UI...
                return true;

            default:
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);

        }*/
    }

    @Override
    public void onBackPressed(){
        System.out.println("aaaaaaaaaaaa");
    }

}