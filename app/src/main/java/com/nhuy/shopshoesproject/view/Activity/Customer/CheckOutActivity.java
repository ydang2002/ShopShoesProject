package com.nhuy.shopshoesproject.view.Activity.Customer;


import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.nhuy.shopshoesproject.R;
import com.nhuy.shopshoesproject.controller.Customer.CartController;
import com.nhuy.shopshoesproject.controller.Customer.CheckOutController;
import com.nhuy.shopshoesproject.models.OrderModel;
import com.nhuy.shopshoesproject.models.Product;
import com.nhuy.shopshoesproject.view.Adapter.CartAdapter;

import java.util.ArrayList;

public class CheckOutActivity extends AppCompatActivity {

    private ImageView checkOutBackBtn;
    private TextView orderPrice, totalPayablePrice, checkOutBtn;
    private EditText usercomments, userAdress;

    private ProgressDialog pd;
    private AlertDialog.Builder builder;

    private OrderModel order;
    private ArrayList<Product> productArrayList;

    private String street;
    private String comments;

    private CartController cartController;
    private CheckOutController checkOutController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_out);

        initAll();

        builder = new AlertDialog.Builder(this);
        builder.setTitle("Xác nhận");
        builder.setMessage("Xác nhận thanh toán");

        OnClickListeners();
    }

    private void OnClickListeners() {

        checkOutBackBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        checkOutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                street = userAdress.getText().toString();
                comments = usercomments.getText().toString();

                if (TextUtils.isEmpty(street)) {
                    userAdress.setError("Nhập địa chỉ");
                    userAdress.requestFocus();
                    return;
                }

                if (TextUtils.isEmpty(comments)) {
                    usercomments.setError("Nhập lời nhắn");
                    usercomments.requestFocus();
                    return;
                }

                builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int which) {

                        if (order.getTotalPrice() > 0) {
                            order.setStatus("Pending");
                            orderConfirmation();
                            finish();
                        } else {
                            Toast.makeText(CheckOutActivity.this, "No Item in Cart", Toast.LENGTH_SHORT).show();
                        }
                        dialog.dismiss();
                    }
                });

                builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        // Do nothing
                        dialog.dismiss();
                    }
                });

                AlertDialog alert = builder.create();
                alert.show();
            }
        });
    }

    private void initAll() {
        //view
        checkOutBackBtn = findViewById(R.id.checkout_back_btn);
        orderPrice = findViewById(R.id.checkout_order_price_view);

        totalPayablePrice = findViewById(R.id.checkout_total_price_view);
        usercomments = findViewById(R.id.checkout_comment_view);
        checkOutBtn = findViewById(R.id.checkout_btn);
        pd = new ProgressDialog(this);
        order = new OrderModel();
        productArrayList = new ArrayList<>();
        userAdress = findViewById(R.id.checkout_address_view);
        cartController = new CartController(CheckOutActivity.this);
        checkOutController = new CheckOutController(CheckOutActivity.this);

        cartController.getCartFormFirebase(new CartController.FirebaseCallback() {
            @Override
            public void onCallback(OrderModel orderModel) {
                order = orderModel;
                productArrayList = (ArrayList<Product>) order.getProductArrayList().clone();
                orderPrice.setText((int) order.getTotalPrice() + " VND");
                totalPayablePrice.setText((int) (order.getTotalPrice() + 10000) + " VND");
                Log.d("LogTotalPrice: ", String.valueOf(order.getTotalPrice()));
            }
        });
    }

    private void orderConfirmation() {
        checkOutController.updateOrderToFirebase(CheckOutActivity.this, street, comments, pd, order, productArrayList);
        Intent intent = new Intent(CheckOutActivity.this, MainActivity.class);
        startActivity(intent);
    }
}
