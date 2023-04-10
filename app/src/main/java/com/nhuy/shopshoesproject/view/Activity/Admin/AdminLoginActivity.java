package com.nhuy.shopshoesproject.view.Activity.Admin;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.nhuy.shopshoesproject.R;
import com.nhuy.shopshoesproject.controller.Customer.LoginController;
import com.nhuy.shopshoesproject.view.constants.Constants;

public class AdminLoginActivity extends AppCompatActivity {
    private EditText email, password;
    private Button loginBtn;
    private ProgressBar progressBar;
    private LoginController loginController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_login);

        initAll();
        settingUpListeners();
    }

    private void settingUpListeners() {
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                login();
            }
        });
    }

    private void initAll() {
        email = findViewById(R.id.Admin_login_email);
        password = findViewById(R.id.Admin_login_pass);
        loginBtn = findViewById(R.id.Admin_login_btn);
        progressBar = findViewById(R.id.Admin_login_progress_bar);
        progressBar.setVisibility(View.GONE);
        loginController = new LoginController(AdminLoginActivity.this);
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

        loginController.fucLogin(userEmail, userPass, progressBar, loginBtn, Constants.ADMIN);
    }
}