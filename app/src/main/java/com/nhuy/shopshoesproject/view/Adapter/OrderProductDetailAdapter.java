package com.nhuy.shopshoesproject.view.Adapter;

import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.nhuy.shopshoesproject.R;
import com.nhuy.shopshoesproject.models.OrderModel;
import com.nhuy.shopshoesproject.models.Product;
import com.nhuy.shopshoesproject.view.Activity.Customer.ProductDetailActivity;
import com.squareup.picasso.Picasso;

import java.util.List;

public class OrderProductDetailAdapter extends RecyclerView.Adapter<OrderProductDetailAdapter.MyViewHolder> {
    List<Product> productList;
    Activity context;

    public OrderProductDetailAdapter(List<Product> productList, Activity context) {
        this.productList = productList;
        this.context = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.single_product_order_item, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Product product = productList.get(position);
        Log.d("LOGOrdermodel", String.valueOf(productList.get(position)));

        if (product.getPhotoUrl() != null) {
            if (!product.getPhotoUrl().equals("")) {
                holder.productImg.setVisibility(View.VISIBLE);
                Picasso.get().load(product.getPhotoUrl()).placeholder(R.drawable.no_background_icon).into(holder.productImg);
            }
        }

        Log.d("LogName", String.valueOf(product.getPrice()));
        holder.name.setText(product.getName());
        holder.price.setText("Giá " + product.getPrice() + "VND");
        holder.quality.setText("Số lượng " + product.getQuantityInCart());
        holder.totalprice.setText("Thành tiền " + product.getQuantityInCart() * product.getPrice());

        holder.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ProductDetailActivity.class);
                intent.putExtra("productId", product.getProductId());
                intent.putExtra("product", product);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return productList == null ? 0 : productList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        LinearLayout layout;
        ImageView productImg;
        TextView name, price, quality, totalprice;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            layout = itemView.findViewById(R.id.order_product_layout);
            productImg = itemView.findViewById(R.id.order_product_img);
            name = itemView.findViewById(R.id.order_product_name);
            price = itemView.findViewById(R.id.order_product_price);
            quality = itemView.findViewById(R.id.order_product_quantity);
            totalprice = itemView.findViewById(R.id.order_product_total_price);
        }
    }
}