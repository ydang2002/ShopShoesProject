package com.nhuy.shopshoesproject.controller.Customer;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.nhuy.shopshoesproject.models.CustomerModel;
import com.nhuy.shopshoesproject.view.Activity.Customer.MainActivity;

public class SignUpController {

    private FirebaseAuth mAuth;
    private DatabaseReference myRootRef;
    private Context context;

    public SignUpController(Activity Context) {
        mAuth = FirebaseAuth.getInstance();
        context = Context;
        myRootRef = FirebaseDatabase.getInstance().getReference();
    }

    public void showMessage(String message) {
        Toast.makeText(context.getApplicationContext(), message, Toast.LENGTH_SHORT).show();
    }

    public void SignUp(String userEmail, String userPass, CustomerModel customerModel) {
        mAuth.createUserWithEmailAndPassword(userEmail,userPass).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
            @Override
            public void onSuccess(AuthResult authResult) {
                showMessage("Đăng ký tài khoản thành công");
                String currentUserId = FirebaseAuth.getInstance().getCurrentUser().getUid();
                customerModel.setUserId(currentUserId);
                myRootRef.child("Users").child(currentUserId).setValue(customerModel).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        showMessage("Welcome");
                        Intent intent=new Intent(context, MainActivity.class);
                        context.startActivity(intent);
                        ((Activity) context).finish();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        showMessage("Đăng ký tài khoàn không thành công");
                    }
                });
            }
        });
    }
}
