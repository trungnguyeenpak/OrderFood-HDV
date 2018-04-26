package com.trungnguyeen.orderfood.food_feature.view;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.trungnguyeen.orderfood.R;
import com.trungnguyeen.orderfood.food_feature.view.adapter.MyPagerAdapter;

public class FoodListActivity extends AppCompatActivity {


    private Toolbar mToolbar;
    private TabLayout mTablayout;
    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_list);

        setupToolbar();
        setupTablayoutAndViewPager();
    }

    private void setupTablayoutAndViewPager() {
        mTablayout = (TabLayout) findViewById(R.id.tablayout_food);
        mViewPager = (ViewPager) findViewById(R.id.viewpager_food);
        //setup mTablayout vs mViewPager
        mTablayout.setupWithViewPager(mViewPager);
        //khoi dong adapter
        mViewPager.setAdapter(new MyPagerAdapter(getSupportFragmentManager()));
    }
    private void setupToolbar() {
        mToolbar = findViewById(R.id.toolbar_food);
        mToolbar.setTitle("Món ăn");
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
