package com.trungnguyeen.orderfood.food_feature.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;

import com.trungnguyeen.orderfood.R;
import com.trungnguyeen.orderfood.data.model.Order;
import com.trungnguyeen.orderfood.food_feature.view.adapter.MyPagerAdapter;
import com.trungnguyeen.orderfood.utils.Constants;

public class FoodListActivity extends AppCompatActivity {

    private final static String TAG = FoodListActivity.class.getSimpleName();
    private Toolbar mToolbar;
    private TabLayout mTablayout;
    private ViewPager mViewPager;
    private Order mOrder;

    String TABFRAGMENTORDER;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_list);

        Intent intent = getIntent();
        if (intent.hasExtra(Constants.ORDER)){
            this.mOrder = (Order) intent.getSerializableExtra(Constants.ORDER);
            Log.i(TAG, "onCreate: order id " + this.mOrder.getId());
        }

        setupToolbar();
        setupTablayoutAndViewPager();
    }

    private void setupTablayoutAndViewPager() {
        mTablayout = (TabLayout) findViewById(R.id.tablayout_food);
        mViewPager = (ViewPager) findViewById(R.id.viewpager_food);
        //setup mTablayout vs mViewPager
        mTablayout.setupWithViewPager(mViewPager);
        //khoi dong adapter
        MyPagerAdapter pagerAdapter = new MyPagerAdapter(getSupportFragmentManager());
        pagerAdapter.setOrder(this.mOrder);
        mViewPager.setAdapter(pagerAdapter);
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

    public String getTABFRAGMENTORDER() {
        return TABFRAGMENTORDER;
    }

    public void setTABFRAGMENTORDER(String TABFRAGMENTORDER) {
        this.TABFRAGMENTORDER = TABFRAGMENTORDER;
    }
}
