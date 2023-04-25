package com.nhuy.shopshoesproject.controller.Admin;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.nhuy.shopshoesproject.models.OrderModel;
import com.nhuy.shopshoesproject.view.Adapter.OrderAdapter;

import java.util.ArrayList;

public class CustomerOrderController {
    private DatabaseReference db;
    private Context context;

    public CustomerOrderController(Activity Context) {
        context = Context;
        db = FirebaseDatabase.getInstance().getReference();
    }

    public void getAdminOrders(ArrayList<OrderModel> orderModelArrayList, ProgressBar progressBar, TextView noOder, OrderAdapter mAdapter, RecyclerView recyclerView) {
        orderModelArrayList.clear();
        progressBar.setVisibility(View.VISIBLE);
        db.child("Order").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    for (DataSnapshot child : dataSnapshot.getChildren()) {
                        String id = child.getKey();
                        getDataFromFirebase(id, progressBar, orderModelArrayList, mAdapter, noOder, recyclerView);
                    }
                } else {
                    noOder.setVisibility(View.VISIBLE);
                    progressBar.setVisibility(View.GONE);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                progressBar.setVisibility(View.GONE);
            }
        });
    }

    private void getDataFromFirebase(String id, ProgressBar progressBar, ArrayList<OrderModel> orderModelArrayList, OrderAdapter mAdapter, TextView noOrder, RecyclerView recyclerView) {
        progressBar.setVisibility(View.VISIBLE);
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = database.getReference("Order").child(id);
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for (DataSnapshot post : snapshot.getChildren()) {
                    OrderModel order = post.getValue(OrderModel.class);
                    orderModelArrayList.add(order);
                }
                mAdapter.notifyDataSetChanged();
                setData(recyclerView, orderModelArrayList, noOrder, mAdapter);
                progressBar.setVisibility(View.GONE);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                noOrder.setVisibility(View.VISIBLE);
                progressBar.setVisibility(View.GONE);
                showMessage("Error");
            }
        });
    }

    private void setData(RecyclerView recyclerView, ArrayList<OrderModel> orderModelArrayList, TextView noOrder, OrderAdapter mAdapter) {
        if (orderModelArrayList.size() > 0) {
            recyclerView.setVisibility(View.VISIBLE);
            noOrder.setVisibility(View.GONE);
        } else {
            recyclerView.setVisibility(View.GONE);
            noOrder.setVisibility(View.VISIBLE);
            mAdapter.notifyDataSetChanged();
        }
    }

    public void showMessage(String message) {
        Toast.makeText(context.getApplicationContext(), message, Toast.LENGTH_SHORT).show();
    }
}
