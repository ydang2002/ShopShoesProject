package com.nhuy.shopshoesproject.controller.Customer;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;

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
import com.nhuy.shopshoesproject.view.Activity.Customer.ProductDetailActivity;
import com.nhuy.shopshoesproject.view.Adapter.CartAdapter;
import com.nhuy.shopshoesproject.view.Adapter.OrderAdapter;
import com.nhuy.shopshoesproject.view.constants.Constants;

import java.util.ArrayList;

public class OrderController {
    private Context context;
    private FirebaseDatabase database;
    private OrderAdapter orderAdapter;
    private String currentUserId;
    private DatabaseReference databaseReference;
    private OrderModel order;
    private ArrayList<OrderModel> orderArrayList;

    public OrderController(Activity Context) {
        context = Context;
        database = FirebaseDatabase.getInstance();;
        currentUserId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        databaseReference = database.getReference(Constants.ORDER).child(currentUserId);
        order=new OrderModel();
        orderArrayList = new ArrayList<>();
    }
    public interface FirebaseCallback{
        void onCallback(ArrayList<OrderModel> orderModelArrayList);
    }
    public void addOrderToFirebase(OrderModel order){
        DatabaseReference myRootRef = FirebaseDatabase.getInstance().getReference();;
        myRootRef.child("Cart").child(currentUserId).setValue(order).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                Toast.makeText(context, "Added to cart", Toast.LENGTH_LONG).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(context, "Error", Toast.LENGTH_LONG).show();

            }
        });
    }
    public void getOrderFromFirebase(FirebaseCallback firebaseCallback) {
        databaseReference = database.getReference(Constants.ORDER).child(currentUserId);
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                orderArrayList.clear();
                for (DataSnapshot post : snapshot.getChildren()){
                    OrderModel order = post.getValue(OrderModel.class);
                    orderArrayList.add(order);
                }
                firebaseCallback.onCallback(orderArrayList);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(context,"Error", Toast.LENGTH_LONG).show();

            }
        });
    }

}
