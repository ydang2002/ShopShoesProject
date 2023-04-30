package com.nhuy.shopshoesproject.controller.Admin;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.nhuy.shopshoesproject.models.OrderModel;
import com.nhuy.shopshoesproject.view.Adapter.OrderAdapter;
import com.nhuy.shopshoesproject.view.constants.Constants;

import java.util.ArrayList;

public class AllCustomerOrderController {
    private Context context;
    private FirebaseDatabase database;
    private String currentUserId;

    public AllCustomerOrderController(Activity Context) {
        context = Context;
        database = FirebaseDatabase.getInstance();
        currentUserId = FirebaseAuth.getInstance().getCurrentUser().getUid();
    }

    public void showMessage(String message) {
        Toast.makeText(context.getApplicationContext(), message, Toast.LENGTH_SHORT).show();
    }

    public void getDataFromFirebase(String id, ProgressBar progressBar, ArrayList<OrderModel> orderArrayList, OrderAdapter mAdapter, TextView noOrder, RecyclerView recyclerView) {
        progressBar.setVisibility(View.VISIBLE);
        DatabaseReference databaseReference = database.getReference(Constants.ORDER).child(id);
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for (DataSnapshot post : snapshot.getChildren()) {
                    OrderModel order = post.getValue(OrderModel.class);
                    orderArrayList.add(order);
                }
                mAdapter.notifyDataSetChanged();
                if (orderArrayList.size() > 0) {
                    recyclerView.setVisibility(View.VISIBLE);
                    noOrder.setVisibility(View.GONE);
                } else {
                    recyclerView.setVisibility(View.GONE);
                    noOrder.setVisibility(View.VISIBLE);
                    mAdapter.notifyDataSetChanged();
                }
                progressBar.setVisibility(View.GONE);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                noOrder.setVisibility(View.VISIBLE);
                progressBar.setVisibility(View.GONE);
                showMessage("Lỗi");
            }
        });
    }

    public void getAdminOrders(ProgressBar progressBar, ArrayList<OrderModel> orderArrayList, OrderAdapter mAdapter, TextView noOrder, RecyclerView recyclerView) {
        orderArrayList.clear();
        progressBar.setVisibility(View.VISIBLE);
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
        databaseReference.child(Constants.ORDER).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    for (DataSnapshot child : dataSnapshot.getChildren()) {
                        String id = child.getKey();
                        getDataFromFirebase(id, progressBar, orderArrayList, mAdapter, noOrder, recyclerView);
                    }
                } else {
                    noOrder.setVisibility(View.VISIBLE);
                    progressBar.setVisibility(View.GONE);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                progressBar.setVisibility(View.GONE);
            }
        });

    }
}
