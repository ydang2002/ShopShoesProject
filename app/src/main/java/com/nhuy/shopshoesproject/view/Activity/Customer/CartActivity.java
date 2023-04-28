package com.nhuy.shopshoesproject.view.Activity.Customer;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.nhuy.shopshoesproject.R;
import com.nhuy.shopshoesproject.controller.Customer.CartController;
import com.nhuy.shopshoesproject.models.OrderModel;
import com.nhuy.shopshoesproject.models.Product;
import com.nhuy.shopshoesproject.view.Adapter.CartAdapter;

import java.util.ArrayList;

public class CartActivity extends AppCompatActivity {
    public static TextView totalPriceView;
    private ImageView deleteCart,cartBackArrow;
    CardView checkOut;
    private OrderModel order;
    private RecyclerView cartRecyclerView;
    private CartAdapter cartCustomAdapter;
    private ArrayList<Product> productArrayList;

    private CartController cartController;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
        initAll();
        clickListener();
    }



    private void clickListener() {
        deleteCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                order.getCartProductList().clear();
                order.setTotalPrice(0);
                productArrayList.clear();
                cartController.updateCartToFirebase(order);
                totalPriceView.setText("0 VND");
                cartCustomAdapter.notifyDataSetChanged();
            }
        });

        checkOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(order.getTotalPrice()>0){
                    Intent intent=new Intent(CartActivity.this,CheckOutActivity.class);
                    startActivity(intent);
                }
                else{
                    Toast.makeText(CartActivity.this, "Không có sản phẩm trong giỏ hàng", Toast.LENGTH_SHORT).show();
                }

            }
        });
        cartBackArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void initAll() {
        cartController = new CartController(CartActivity.this);
        order=new OrderModel();
        cartRecyclerView=findViewById(R.id.cart_order_recyclerview);
        cartRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        cartRecyclerView.setNestedScrollingEnabled(false);
        productArrayList =new ArrayList<>();
        totalPriceView=findViewById(R.id.cart_total_price_view);

        deleteCart=findViewById(R.id.delete_cart_imageView);
        cartBackArrow=findViewById(R.id.cart_back_arrow);

        cartController.getCartFormFirebase(new CartController.FirebaseCallback() {
            @Override
            public void onCallback(OrderModel orderModel) {
                order = orderModel;
                productArrayList = (ArrayList<Product>) orderModel.getProductArrayList().clone();
                cartRecyclerView=findViewById(R.id.cart_order_recyclerview);
                cartCustomAdapter=new CartAdapter(CartActivity.this, productArrayList,order);
                cartRecyclerView.setLayoutManager(new LinearLayoutManager(CartActivity.this));
                cartRecyclerView.setNestedScrollingEnabled(false);
                cartRecyclerView.setAdapter(cartCustomAdapter);
                Log.d("LogTotalPrice: ", String.valueOf((int)order.getTotalPrice()));
                totalPriceView.setText((int)order.getTotalPrice() + " VND");
            }
        });


        checkOut=findViewById(R.id.check_out_btn);


    }

}