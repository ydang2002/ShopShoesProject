package com.nhuy.shopshoesproject.view.Activity.Customer;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.nhuy.shopshoesproject.R;
import com.nhuy.shopshoesproject.controller.Customer.LoginController;
import com.nhuy.shopshoesproject.view.Activity.Admin.AdminLoginActivity;
import com.nhuy.shopshoesproject.view.constants.Constants;

public class CustomersLoginActivity extends AppCompatActivity {

    private TextView email, password, didNotHaveAcc;
    private ProgressBar progressBar;
    private AppCompatButton loginBtn;
    private ImageView manager_btn;
    private LoginController loginController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customers_login);

        initUi();
        settingUpListeners();
    }

    private void initUi() {
        email = findViewById(R.id.login_email);
        password = findViewById(R.id.login_pass);
        loginBtn = findViewById(R.id.login_btn);
        manager_btn = findViewById(R.id.manager_portal_btn);
        didNotHaveAcc = findViewById(R.id.login_signup_btn);
        progressBar = findViewById(R.id.login_progress_bar);
        progressBar.setVisibility(View.GONE);

        loginController = new LoginController(CustomersLoginActivity.this);
    }

    private void settingUpListeners() {
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                login();
            }
        });

        didNotHaveAcc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CustomersLoginActivity.this, SignUpActivity.class);
                startActivity(intent);
            }
        });

        manager_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CustomersLoginActivity.this, AdminLoginActivity.class);
                startActivity(intent);
            }
        });
    }

    private void login() {
        String userEmail = email.getText().toString().trim();
        String userPass = password.getText().toString().trim();
        if (TextUtils.isEmpty(userEmail)) {
            email.setError("enter email");
            email.requestFocus();
            return;
        }

        if (TextUtils.isEmpty(userPass)) {
            password.setError("enter pass");
            password.requestFocus();
            return;
        }

        if (userPass.length() < 8) {
            password.setError("password must be 7 characters large");
            password.requestFocus();
            return;
        }

        loginController.fucLogin(userEmail, userPass, progressBar, loginBtn, Constants.USERS);
    }
}