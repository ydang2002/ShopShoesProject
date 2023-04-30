package com.nhuy.shopshoesproject.view.Activity.Admin.Order;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.nhuy.shopshoesproject.R;
import com.nhuy.shopshoesproject.controller.Admin.AllCustomerOrderController;
import com.nhuy.shopshoesproject.models.OrderModel;
import com.nhuy.shopshoesproject.view.Adapter.OrderAdapter;

import java.util.ArrayList;

public class AllCustomerOrderActivity extends AppCompatActivity {

    private OrderAdapter mAdapter;
    private RecyclerView recyclerView;
    private ArrayList<OrderModel> orderArrayList;

    private TextView noOder;
    private DatabaseReference databaseReference;
    private ProgressBar progressBar;
    private AllCustomerOrderController allCustomerOrderController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_order);

        initAll();
        allCustomerOrderController.getAdminOrders(progressBar, orderArrayList, mAdapter, noOder, recyclerView);
    }

    private void initAll() {
        orderArrayList = new ArrayList<OrderModel>();
        recyclerView = findViewById(R.id.customer_order_list);
        progressBar = findViewById(R.id.spin_progress_bar_customer_order);
        noOder = findViewById(R.id.no_customer_order);
        databaseReference = FirebaseDatabase.getInstance().getReference();

        mAdapter = new OrderAdapter(orderArrayList, AllCustomerOrderActivity.this, true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        recyclerView.setNestedScrollingEnabled(false);
        recyclerView.setAdapter(mAdapter);
        mAdapter.notifyDataSetChanged();
        allCustomerOrderController = new AllCustomerOrderController(AllCustomerOrderActivity.this);
    }

    public void goBack(View view) {
        finish();
    }
}