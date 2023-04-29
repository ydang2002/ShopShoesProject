package com.nhuy.shopshoesproject.view.Activity.Customer;

import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.nhuy.shopshoesproject.R;
import com.nhuy.shopshoesproject.controller.Customer.OrderController;
import com.nhuy.shopshoesproject.controller.Customer.OrderDetailController;
import com.nhuy.shopshoesproject.models.OrderModel;
import com.nhuy.shopshoesproject.models.Product;
import com.nhuy.shopshoesproject.view.Adapter.OrderProductDetailAdapter;

import java.util.ArrayList;

public class OrderDetailActivity extends AppCompatActivity {

    private OrderProductDetailAdapter mAdapter;
    private RecyclerView recyclerView;
    private ArrayList<Product> productArrayList;
    private OrderModel order;
    DatabaseReference myRootRef;
    private RelativeLayout delete;
    private ImageView img;
    private AlertDialog.Builder builder;
    private OrderDetailController orderDetailController;


    private TextView orderID, Price, status, Date, Quantity, address, comment;
    private String ID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_detail);
        initAll();
        OnClickListeners();
    }

    private void initAll() {
        orderID = findViewById(R.id.orderID);
        status = findViewById(R.id.order_detail_status);
        Date = findViewById(R.id.order_detail_date);
        Quantity = findViewById(R.id.order_detail_quantity);
        Price = findViewById(R.id.order_detail_total_price);
        recyclerView = findViewById(R.id.product_list_order);
        address = findViewById(R.id.order_address_view);
        comment = findViewById(R.id.order_comment_view);
        recyclerView = findViewById(R.id.product_list_order);
        delete = findViewById(R.id.order_delete);
        img = findViewById(R.id.order_back);
        myRootRef = FirebaseDatabase.getInstance().getReference();
        ID = getIntent().getExtras().getString("orderID");
        order = new OrderModel();
        productArrayList = new ArrayList<>();
        orderDetailController = new OrderDetailController(OrderDetailActivity.this);
        getDataFirebase();

        builder = new AlertDialog.Builder(this);
        builder.setTitle("Xác nhận");
        builder.setMessage("Bạn có muốn hủy đơn hàng");

    }

    private void getDataFirebase() {
        orderDetailController.getOrderFromFirebase(ID, orderID, comment, address, Price, Quantity, Date, status, recyclerView, OrderDetailActivity.this);
    }

    private void OnClickListeners() {

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        orderDetailController.cancelOrder(ID);
                        dialog.dismiss();
                    }
                });

                builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                AlertDialog alert = builder.create();
                alert.show();
            }
        });

        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();

            }
        });
    }
}
