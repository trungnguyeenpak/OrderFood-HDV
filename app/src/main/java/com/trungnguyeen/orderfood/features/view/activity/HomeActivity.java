package com.trungnguyeen.orderfood.features.view.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.trungnguyeen.orderfood.R;
import com.trungnguyeen.orderfood.data.model.Employee;
import com.trungnguyeen.orderfood.data.model.SingletonUser;
import com.trungnguyeen.orderfood.features.view.fragment.FoodFragment;
import com.trungnguyeen.orderfood.features.view.fragment.TableFragment;
import com.trungnguyeen.orderfood.login.view.LoginActivity;
import com.trungnguyeen.orderfood.utilities.Constants;

public class HomeActivity extends AppCompatActivity {

    private final static String TAG = HomeActivity.class.getSimpleName();
    private Toolbar mToolbar;
    private BottomNavigationView bottomNavigationView;
    private NavigationView nav_left;
    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mToggle;
    private TextView mUsername;
    private Employee mEmployee;
    private SingletonUser mUser = SingletonUser.getInstance();
    private boolean doubleBackToExit = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        //loading the default fragment
        loadFragment(new FoodFragment());
        mEmployee = mUser.getEmployee();
        setupToolbar();
        setupNavigationLeft();
        setupDrawerLayout();
        setupBottomNavigation();

    }

    private void setupNavigationLeft() {
        nav_left = findViewById(R.id.navigation_bar);
        View hView = nav_left.getHeaderView(0);
        mUsername = hView.findViewById(R.id.userName);
        Log.i(TAG, "setupNavigationLeft: " + mUsername.getText());
        if (mEmployee != null){
            mUsername.setText(mEmployee.fullName());
        }
        nav_left.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.logout:
                        Log.i("TAG", "onNavigationItemSelected: Logout");
                        SharedPreferences prefer = getSharedPreferences(Constants.KEY_PREFERENCES, Context.MODE_PRIVATE);
                        prefer.edit().clear().apply();
                        Intent logoutIntent = new Intent(HomeActivity.this, LoginActivity.class);
                        startActivity(logoutIntent);
                        finish();
                        break;
                }
                mDrawerLayout.closeDrawers();  // CLOSE DRAWER
                return true;
            }
        });
    }

    private void setupDrawerLayout() {
        mDrawerLayout = findViewById(R.id.drawer_layout);
        mToggle = new ActionBarDrawerToggle(this, mDrawerLayout, mToolbar, R.string.open, R.string.close);
        mDrawerLayout.addDrawerListener(mToggle);
        mToggle.syncState();
        getSupportActionBar().setDisplayShowTitleEnabled(true);

    }

    private void setupBottomNavigation() {
        bottomNavigationView = findViewById(R.id.nav_bottom);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment fragment = null;

                switch (item.getItemId()) {
                    case R.id.action_foos:
                        fragment = new FoodFragment();
                        break;

                    case R.id.action_table:
                        fragment = new TableFragment();
                        break;
                }
                return loadFragment(fragment);
            }
        });
    }

    private boolean loadFragment(Fragment fragment) {
        //switching fragment
        if (fragment != null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.view_container, fragment)
                    .commit();
            return true;
        }
        return false;
    }

    private void setupToolbar() {
        mToolbar = findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        mToolbar.setTitle(R.string.app_name);
    }

    @Override
    public void onBackPressed() {
        Toast toast = Toast.makeText(getBaseContext(), "Please click back again to exit", Toast.LENGTH_SHORT);
        if (doubleBackToExit) {
            super.onBackPressed();
            toast.cancel();
            finish();
        }
        doubleBackToExit = true;
        toast.show();
        new CountDownTimer(1500, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
            }

            @Override
            public void onFinish() {
                doubleBackToExit = false;
            }
        }.start();
    }
}
