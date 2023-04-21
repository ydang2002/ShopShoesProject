package com.nhuy.shopshoesproject.controller.Customer;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.nhuy.shopshoesproject.models.CustomerModel;
import com.nhuy.shopshoesproject.view.Activity.Admin.AdminHomeActivity;
import com.nhuy.shopshoesproject.view.Activity.Customer.MainActivity;

public class LoginController {

    private FirebaseAuth mAuth;
    private DatabaseReference myRootRef;
    private Context context;
    private boolean flag = false;
    int count =0;
    private PeopleModel user;
    private CustomerModel customerModel;


    public LoginController(Activity Context) {
        mAuth = FirebaseAuth.getInstance();
        context = Context;
        myRootRef = FirebaseDatabase.getInstance().getReference();
    }

    public void showMessage(String message) {
        Toast.makeText(context.getApplicationContext(), message, Toast.LENGTH_SHORT).show();
    }

    public void fucLogin(String userEmail, String userPass, ProgressBar progressBar, Button loginBtn, String constant) {
        progressBar.setVisibility(View.VISIBLE);
        loginBtn.setVisibility(View.GONE);
        Log.d("logtest2: ", "1");

        mAuth.signInWithEmailAndPassword(userEmail, userPass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    String currentUserId = FirebaseAuth.getInstance().getCurrentUser().getUid();
                    myRootRef.child(constant).child(currentUserId).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if (snapshot.exists()) {
                                progressBar.setVisibility(View.GONE);
                                if( currentUserId.equals("k7kIJUfiitZOv2YIgNIoKdMD46i1")) {
                                    Intent intent = new Intent(context, AdminHomeActivity.class);
                                    Toast.makeText(context, "Welcome Admin", Toast.LENGTH_SHORT).show();
                                    context.startActivity(intent);
                                    ((Activity) context).finish();
                                } else {
                                    Intent intent = new Intent(context, MainActivity.class);
                                    Toast.makeText(context, "Welcome Customer", Toast.LENGTH_SHORT).show();
                                    context.startActivity(intent);
                                    ((Activity) context).finish();
                                }
                            }
                            else {
                                mAuth.signOut();
                                showMessage("This is not Admin login details");
                                progressBar.setVisibility(View.GONE);
                                loginBtn.setVisibility(View.VISIBLE);
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {
                            mAuth.signOut();
                            showMessage("This is not Admin login details.ERROR");
                            progressBar.setVisibility(View.GONE);
                            loginBtn.setVisibility(View.VISIBLE);
                        }
                    });
                } else {
                    showMessage("Authentication failed.");
                    progressBar.setVisibility(View.GONE);
                    loginBtn.setVisibility(View.VISIBLE);
                }
            }
        });
    }
    public void getProfileData(TextView UserNameDrawer) {
        String currentUserId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = database.getReference("Users").child(currentUserId);
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                if (snapshot.exists()) {
                    user = snapshot.getValue(PeopleModel.class);
                    UserNameDrawer.setText(user.getName());
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}

