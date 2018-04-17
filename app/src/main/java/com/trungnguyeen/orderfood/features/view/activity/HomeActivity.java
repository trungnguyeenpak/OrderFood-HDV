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
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
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
import com.trungnguyeen.orderfood.design_supporter.CustomViewPager;
import com.trungnguyeen.orderfood.features.view.fragment.FoodFragment;
import com.trungnguyeen.orderfood.features.view.fragment.TableFragment;
import com.trungnguyeen.orderfood.login.view.LoginActivity;
import com.trungnguyeen.orderfood.utils.Constants;

public class HomeActivity extends AppCompatActivity{

    //Var of controls
    private Toolbar mToolbar;
    private BottomNavigationView bottomNavigationView;
    private NavigationView nav_left;
    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mToggle;
    private TextView mUsername;
    private CustomViewPager viewPager;

    //Var of dev
    private final static String TAG = HomeActivity.class.getSimpleName();
    private Employee mEmployee;
    private SingletonUser mUser = SingletonUser.getInstance();
    private boolean doubleBackToExit = false;
    private Fragment foodFragment = new FoodFragment();
    private Fragment tableFragment = new TableFragment();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        //loading the default fragment
        //loadFragment(new FoodFragment());
        mEmployee = mUser.getEmployee();
        Log.i(TAG, "onCreate: Employee " + mEmployee.getFullName());
        setupToolbar();
        setupViewPager();
        setupBottomNavigation();
        setupNavigationLeft();
        setupDrawerLayout();

    }

    private void setupViewPager() {
        viewPager = findViewById(R.id.viewPager);
        viewPager.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                switch (position) {
                    case 0:
                        return foodFragment;
                    case 1:
                        return tableFragment;
                }
                return null;
            }

            @Override
            public int getCount() {
                return 2;
            }
        });

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                bottomNavigationView.getMenu().getItem(position).setChecked(true);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        viewPager.setPagingEnabled(false);
    }

    private void setupBottomNavigation() {
        bottomNavigationView = findViewById(R.id.nav_bottom);
        bottomNavigationView.setOnNavigationItemSelectedListener(mBottomNavListener);
    }

    private void setupNavigationLeft() {
        nav_left = findViewById(R.id.navigation_bar);
        View hView = nav_left.getHeaderView(0);
        mUsername = hView.findViewById(R.id.userName);
        Log.i(TAG, "setupNavigationLeft: " + mUsername.getText());
        if (mEmployee != null) {
            mUsername.setText(mEmployee.getFullName());
        }

        nav_left.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.logout:
                        Log.i("TAG", "onNavigationItemSelected: Logout");
                        SharedPreferences prefer = getSharedPreferences(Constants.KEY_PREFERENCES, Context.MODE_PRIVATE);
                        prefer.edit().clear().apply();
                        Intent logoutIntent = new Intent(HomeActivity.this, LoginActivity.class);
                        startActivity(logoutIntent);
                        finish();
                        break;
                }
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



    private BottomNavigationView.OnNavigationItemSelectedListener mBottomNavListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Log.i(TAG, "onNavigationItemSelected: id " + item.getItemId());
            viewPager.setCurrentItem(item.getOrder());
            return true;
        }
    };


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
