package com.trungnguyeen.orderfood.login.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.trungnguyeen.orderfood.R;
import com.trungnguyeen.orderfood.data.model.Employee;
import com.trungnguyeen.orderfood.login.presenter.LoginPresenter;
import com.trungnguyeen.orderfood.login.presenter.LoginViewListener;
import com.trungnguyeen.orderfood.table_featrue.view.TableActivity;

public class LoginActivity extends AppCompatActivity implements LoginViewListener {

    private final static String TAG = LoginActivity.class.getSimpleName();
    private EditText edtUsername;
    private EditText edtPassword;
    private Button btnLogin;
    private Animation uptodown;
    private Animation downtoup;
    private ImageView imageView;
    private ConstraintLayout constraintLayoutLogin;
    private ProgressBar progressBar;

    private LoginPresenter mLoginPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mLoginPresenter = new LoginPresenter(this, this);
        getControls();
        setEvents();
    }

    private void getControls() {
        edtUsername = findViewById(R.id.userName);
        edtPassword = findViewById(R.id.password);
        btnLogin = findViewById(R.id.login);
        progressBar = findViewById(R.id.login_progressbar);

        imageView = findViewById(R.id.img_app);
        uptodown = AnimationUtils.loadAnimation(this, R.anim.uptodown);
        imageView.setAnimation(uptodown);

        constraintLayoutLogin = findViewById(R.id.constrainlayoutlogin);
        downtoup = AnimationUtils.loadAnimation(this, R.anim.downtoup);
        constraintLayoutLogin.setAnimation(downtoup);

    }

    private void setEvents() {
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = edtUsername.getText().toString().trim();
                String password = edtPassword.getText().toString();
                if(!TextUtils.isEmpty(username) && !TextUtils.isEmpty(password)){
                    progressBar.setVisibility(View.VISIBLE);
                    mLoginPresenter.handleLogin(username, password);
                }else{
                    Toast.makeText(LoginActivity.this, "Please fill all fields", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public void onLoginSuccess(Employee employee) {
        Intent intent = new Intent(this, TableActivity.class);
        Log.i(TAG, "onLoginSuccess: " + employee.getLastName());
        startActivity(intent);
        finish();
    }

    @Override
    public void onLoginFailed() {
        progressBar.setVisibility(View.GONE);
        Toast.makeText(this, "Login failed", Toast.LENGTH_SHORT).show();
    }
}
