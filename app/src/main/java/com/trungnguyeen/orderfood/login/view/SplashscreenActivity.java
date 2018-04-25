package com.trungnguyeen.orderfood.login.view;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.trungnguyeen.orderfood.R;
import com.trungnguyeen.orderfood.login.presenter.LoginPresenter;
import com.trungnguyeen.orderfood.login.presenter.SplashViewListener;
import com.trungnguyeen.orderfood.table_featrue.view.TableActivity;
import com.trungnguyeen.orderfood.utils.Constants;

public class SplashscreenActivity extends AppCompatActivity implements SplashViewListener {


    private static final String TAG = SplashscreenActivity.class.getSimpleName();
    private ProgressBar mProgressBar;
    private LoginPresenter mLoginPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splashscreen);

        getControls();
        mLoginPresenter = new LoginPresenter(this, this);
        mLoginPresenter.receiveHandleCheckLogin();
        Log.i(TAG, "onCreate: SplashScreen");
    }

    private void getControls() {
        mProgressBar = (ProgressBar) findViewById(R.id.progressBar);

    }

    @Override
    public void onBackPressed() {
    }

    @Override
    public void loggedin() {
        Log.i(TAG, "loggedin: loggedin");
        new CountDownTimer(1100, 1000) {
            public void onTick(long millisUntilFinished) {

            }
            public void onFinish() {
                Intent homeIntent = new Intent(SplashscreenActivity.this, TableActivity.class);
                startActivity(homeIntent);
                finish();
            }
        }.start();

    }

    @Override
    public void noLogin(int responseCode) {
        if(responseCode == Constants.SERVICE_ERROR_CODE){
            Toast.makeText(this, "No Service", Toast.LENGTH_SHORT).show();
            new CountDownTimer(3000, 1000){
                @Override
                public void onFinish() {
                    Toast.makeText(SplashscreenActivity.this, "No service", Toast.LENGTH_SHORT).show();
                    finish();
                    System.exit(0);
                }
                @Override
                public void onTick(long millisUntilFinished) {

                }
            }.start();
        }
        else {
            Log.i(TAG, "noLogin: noLogin");
            new CountDownTimer(1100, 1000) {
                public void onTick(long millisUntilFinished) {

                }
                public void onFinish() {
                    Intent homeIntent = new Intent(SplashscreenActivity.this, LoginActivity.class);
                    startActivity(homeIntent);
                    finish();
                }
            }.start();
        }
    }

}
