package com.nhuy.shopshoesproject.view.Activity.Customer;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.nhuy.shopshoesproject.R;
import com.nhuy.shopshoesproject.controller.Customer.BillController;
import com.nhuy.shopshoesproject.controller.Customer.OrderController;
import com.nhuy.shopshoesproject.models.OrderModel;
import com.nhuy.shopshoesproject.models.Product;
import com.nhuy.shopshoesproject.view.Adapter.OrderProductDetailAdapter;
import java.util.ArrayList;

public class BillDetailActivity extends AppCompatActivity {

    private OrderProductDetailAdapter mAdapter;
    private RecyclerView recyclerView;
    private ArrayList<Product> productArrayList;
    private OrderModel order;
    private ImageView img;
    private BillController billController;


    private TextView orderID, orderPrice, orderstatus, orderDate, orderQuantity, address, comment;
    private String ID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bill_detail);
        initAll();
        OnClickListeners();
    }

    private void initAll() {
        orderID = findViewById(R.id.orderID);
        orderstatus = findViewById(R.id.order_detail_status);
        orderDate = findViewById(R.id.order_detail_date);
        orderQuantity = findViewById(R.id.order_detail_quantity);
        orderPrice = findViewById(R.id.order_detail_total_price);
        recyclerView = findViewById(R.id.product_list_order);
        address = findViewById(R.id.order_address_view);
        comment = findViewById(R.id.order_comment_view);
        recyclerView = findViewById(R.id.product_list_order);
        billController = new BillController(this);

        img = findViewById(R.id.order_back);
        if (getIntent().getExtras().getBoolean("isAdmin"))
            ID = getIntent().getExtras().getString("idCustomer");

        else ID = getIntent().getExtras().getString("orderID");

//        ID = getIntent().getExtras().getString("orderID");

        billController.getBillFromFirebase(ID, new OrderController.FirebaseCallback() {
            @Override
            public void onCallback(ArrayList<OrderModel> orderModelArrayList) {
                order = orderModelArrayList.get(0);
                productArrayList = (ArrayList<Product>) order.getProductArrayList().clone();
                orderID.setText(order.getId());
                orderstatus.setText(order.getStatus());
                orderDate.setText(order.getDateOfOrder());
                orderQuantity.setText(String.valueOf(order.getProductArrayList().size()));
                orderPrice.setText(String.valueOf((int) order.getTotalPrice() + 10000) + " VND");
                address.setText(order.getAddress());
                comment.setText(order.getComments());

                DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(BillDetailActivity.this, DividerItemDecoration.VERTICAL);
                recyclerView.addItemDecoration(dividerItemDecoration);
                mAdapter = new OrderProductDetailAdapter(productArrayList, BillDetailActivity.this);
                recyclerView.setNestedScrollingEnabled(false);
                recyclerView.setLayoutManager(new LinearLayoutManager(BillDetailActivity.this));
                recyclerView.setAdapter(mAdapter);
                mAdapter.notifyDataSetChanged();

            }
        });

    }

    private void OnClickListeners() {


        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();

            }
        });
    }
}