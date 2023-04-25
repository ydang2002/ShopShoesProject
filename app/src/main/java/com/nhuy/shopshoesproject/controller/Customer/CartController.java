package com.nhuy.shopshoesproject.controller.Customer;
import android.app.Activity;
import android.content.Context;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.nhuy.shopshoesproject.models.OrderModel;
import com.nhuy.shopshoesproject.models.Product;

import com.nhuy.shopshoesproject.view.Adapter.CartAdapter;

import java.util.ArrayList;

public class CartController {
    private Context context;
    private FirebaseDatabase database;
    private CartAdapter cartCustomAdapter;
    private String currentUserId;
    private DatabaseReference databaseReference;
    private OrderModel order;
    private ArrayList<Product> productArrayList;

    public CartController(Activity Context) {
        context = Context;
        database = FirebaseDatabase.getInstance();;
        currentUserId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        databaseReference = database.getReference("Cart").child(currentUserId);
        order=new OrderModel();
    }
    public interface FirebaseCallback{
        void onCallback(OrderModel orderModel);
    }
    public void getCartFormFirebase(FirebaseCallback firebaseCallback){
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    order = snapshot.getValue(OrderModel.class);
                }
                firebaseCallback.onCallback(order);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(context,"Error", Toast.LENGTH_LONG).show();

            }
        });
    }
    public void updateCartToFirebase(OrderModel order){
        String currentUserId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        DatabaseReference myRootRef = FirebaseDatabase.getInstance().getReference();;
        myRootRef.child("Cart").child(currentUserId).setValue(order).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

            }
        });
    }
}

