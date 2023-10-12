package com.foysaltech.navigationdrawertheme;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import com.foysaltech.navigationdrawertheme.Fragment.CategoriesFragment;
import com.foysaltech.navigationdrawertheme.Fragment.HomeFragment;
import com.foysaltech.navigationdrawertheme.Fragment.LoginFragment;
import com.foysaltech.navigationdrawertheme.Fragment.MissingPlaceFragment;
import com.foysaltech.navigationdrawertheme.Fragment.ProfileFragment;
import com.foysaltech.navigationdrawertheme.Fragment.SearchFragment;
import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity implements
        NavigationView.OnNavigationItemSelectedListener {

    Constant constant;
    SharedPreferences.Editor editor;
    SharedPreferences app_preferences;
    int appTheme;
    int themeColor;
    int appColor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        app_preferences = PreferenceManager.getDefaultSharedPreferences(this);
        appColor = app_preferences.getInt("color", 0);
        appTheme = app_preferences.getInt("theme", 0);
        themeColor = appColor;
        constant.color = appColor;

        if (themeColor == 0) {
            setTheme(Constant.theme);
        } else if (appTheme == 0) {
            setTheme(Constant.theme);
        } else {
            setTheme(appTheme);
        }

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Home");
        toolbar.setBackgroundColor(Constant.color);


        getSupportFragmentManager().beginTransaction().replace(R.id.containerId,
                new HomeFragment()).commit();


        DrawerLayout drawerLayout = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this,
                drawerLayout, toolbar, R.string.navigation_drawer_open,
                R.string.navigation_drawer_close);
        drawerLayout.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.setBackgroundColor(Constant.color);

        View header = navigationView.getHeaderView(0);
        RelativeLayout relativeLayout = header.findViewById(R.id.nav_header_id);
        relativeLayout.setBackgroundColor(Constant.color);

    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        Fragment fragment = null;
        int id = item.getItemId();
        if (id == R.id.nav_home) {
            fragment = new HomeFragment();
            showFragment(fragment, "Home");
        } else if (id == R.id.nav_search) {
            fragment = new SearchFragment();
            showFragment(fragment, "Search");
        } else if (id == R.id.nav_all_categories) {
            fragment = new CategoriesFragment();
            showFragment(fragment, "Categories");
        } else if (id == R.id.addMissingPlace) {
            fragment = new MissingPlaceFragment();
            showFragment(fragment, "MissingPlace");
        } else if (id == R.id.nav_login) {
            fragment = new LoginFragment();
            showFragment(fragment, "Login");
        } else if (id == R.id.nav_profile) {
            fragment = new ProfileFragment();
            showFragment(fragment, "Profile");
        } else if (id == R.id.nav_settings) {
            Intent intent = new Intent(MainActivity.this, SettingsActivity.class);
            startActivity(intent);
        }

        DrawerLayout drawerLayout = findViewById(R.id.drawer_layout);
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    private void showFragment(Fragment fragment, String title) {
        getSupportActionBar().setTitle(title);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.containerId, fragment)
                .addToBackStack(null).commit();
    }
}