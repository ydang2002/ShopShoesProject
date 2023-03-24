package com.nhuy.shopshoesproject.view.Adapter;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.nhuy.shopshoesproject.R;
import com.nhuy.shopshoesproject.controller.Admin.HiddenProductController;
import com.nhuy.shopshoesproject.models.Product;
import com.nhuy.shopshoesproject.view.Activity.Admin.Product.UpdateProductActivity;
import com.nhuy.shopshoesproject.view.constants.Constants;
import com.squareup.picasso.Picasso;

import org.checkerframework.checker.nullness.qual.NonNull;

import java.util.ArrayList;
import java.util.List;

public class HiddenProductAdapter extends RecyclerView.Adapter<HiddenProductAdapter.MyViewHolder> {

    List<Product> productList;
    Activity context;

    public HiddenProductAdapter(Activity context, List<Product> productList) {
        this.productList = productList;
        this.context = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_single_hidden_product, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Product product = productList.get(position);
        holder.id.setText(product.getProductId());
        holder.name.setText(product.getName());

        if(product.getHidden() == false) {
            holder.aSwitch.setChecked(false);
        } else {
            holder.aSwitch.setChecked(true);
        }

        holder.aSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if (isChecked) {
                    product.setHidden(true);
                    holder.controller.hiddenProduct(product, Constants.PRODUCTS, product.getProductId());
                    Toast.makeText(context, "Sản phẩm đã ẩn", Toast.LENGTH_SHORT).show();
                } else {
                    product.setHidden(false);
                    holder.controller.hiddenProduct(product, Constants.PRODUCTS, product.getProductId());
                    Toast.makeText(context, "Sản phẩm hiển thị", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return productList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView name, id;
        Switch aSwitch;
        HiddenProductController controller;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            id = itemView.findViewById(R.id.hidden_id);
            name = itemView.findViewById(R.id.hidden_name);
            aSwitch = itemView.findViewById(R.id.idSwitch);
            controller = new HiddenProductController();
        }
    }
}
