package com.nhuy.shopshoesproject.view.Activity.Customer;


import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.nhuy.shopshoesproject.R;
import com.nhuy.shopshoesproject.controller.Customer.BillController;
import com.nhuy.shopshoesproject.controller.Customer.OrderController;
import com.nhuy.shopshoesproject.models.OrderModel;
import com.nhuy.shopshoesproject.view.Adapter.OrderAdapter;

import java.util.ArrayList;

public class BillActivity extends AppCompatActivity {
    private OrderAdapter mAdapter;
    private RecyclerView recyclerView;
    private ArrayList<OrderModel> orderArrayList;
    private TextView noOder;
    private ProgressBar progressBar;
    private BillController billController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bill);
        initAll();

    }

    private void initAll() {
        orderArrayList =new ArrayList<OrderModel>();
        recyclerView = findViewById(R.id.customer_order_list);
        progressBar = findViewById(R.id.spin_progress_bar_customer_order);
        noOder = findViewById(R.id.no_customer_order);
        mAdapter = new OrderAdapter(orderArrayList, BillActivity.this, false);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        recyclerView.setNestedScrollingEnabled(false);
        recyclerView.setAdapter(mAdapter);
        billController = new BillController(this);
        progressBar.setVisibility(View.GONE);
        billController.getBillFromFirebase(new OrderController.FirebaseCallback() {
            @Override
            public void onCallback(ArrayList<OrderModel> orderModelArrayList) {
                Log.d("test",orderModelArrayList.size()+" ");
                if (orderModelArrayList.size()>0) {
                    orderArrayList.clear();
                    orderArrayList = (ArrayList<OrderModel>) orderModelArrayList.clone();
                    setData();
                    progressBar.setVisibility(View.GONE);
                }
                else {
                    noOder.setVisibility(View.VISIBLE);
                    progressBar.setVisibility(View.GONE);
                }
            }
        });
    }

    private void setData() {
        if(orderArrayList.size()>0){
            mAdapter = new OrderAdapter(orderArrayList,this,false);
            recyclerView.setAdapter(mAdapter);
            recyclerView.setVisibility(View.VISIBLE);
            noOder.setVisibility(View.GONE);
        }
        else{
            recyclerView.setVisibility(View.GONE);
            noOder.setVisibility(View.VISIBLE);
            mAdapter.notifyDataSetChanged();
        }
    }

    public void goBack(View view) {
        finish();
    }
}
