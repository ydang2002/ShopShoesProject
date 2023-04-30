package com.nhuy.shopshoesproject.view.Activity.Admin.Order;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.nhuy.shopshoesproject.R;
import com.nhuy.shopshoesproject.controller.Admin.CustomerOrderDetailController;
import com.nhuy.shopshoesproject.controller.Customer.OrderDetailController;
import com.nhuy.shopshoesproject.models.OrderModel;
import com.nhuy.shopshoesproject.models.Product;

import java.util.ArrayList;

public class CustomerOrderDetailActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RelativeLayout confirmStatus;
    private TextView orderID, price, status, date, quantity, address, comment;
    private String ID, idCustomer;

    private CustomerOrderDetailController customerOrderDetailController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_order_detail_main);

        initAll();
        confirmOrderStatus();
    }

    private void initAll() {
        orderID = findViewById(R.id.orderID);
        status = findViewById(R.id.order_detail_status);
        date = findViewById(R.id.order_detail_date);
        quantity = findViewById(R.id.order_detail_quantity);
        price = findViewById(R.id.order_detail_total_price);
        recyclerView = findViewById(R.id.product_list_order);
        address = findViewById(R.id.order_address_view);
        comment = findViewById(R.id.order_comment_view);
        recyclerView = findViewById(R.id.product_list_order);
        confirmStatus = findViewById(R.id.order_confirm_status);

        ID = getIntent().getExtras().getString("id");
        idCustomer = getIntent().getExtras().getString("idCustomer");

        customerOrderDetailController = new CustomerOrderDetailController(CustomerOrderDetailActivity.this);
        customerOrderDetailController.getOrderFromFirebase(ID, orderID, comment, address, price, quantity, date, status, recyclerView, CustomerOrderDetailActivity.this, idCustomer);
    }

    private void confirmOrderStatus() {
        confirmStatus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                customerOrderDetailController.confirmStatus(idCustomer, ID);
            }
        });
    }

    public void goBack(View view) {
        finish();
    }
}