package com.nhuy.shopshoesproject.view.Adapter;


import android.app.Activity;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.nhuy.shopshoesproject.R;
import com.nhuy.shopshoesproject.models.BrandModel;
import com.nhuy.shopshoesproject.view.Activity.Customer.FilterSearchActivity;

import java.util.ArrayList;

public class FilterBrandAdapter extends RecyclerView.Adapter<FilterBrandAdapter.MyViewHolder> {
    ArrayList<BrandModel> brandList;
    Activity context;

    public FilterBrandAdapter(ArrayList<BrandModel> brandList, Activity context) {
        this.brandList = brandList;
        this.context = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.single_item_category, parent, false);

        return new FilterBrandAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        BrandModel brand = brandList.get(position);
        holder.nameBrand.setText(brand.getBrandName());
        for (int i = 0; i< FilterSearchActivity.Clickbrand.size(); i++){
            if (brand.getBrandId().equals(FilterSearchActivity.Clickbrand.get(i).getBrandId())){
                holder.cardView.setCardBackgroundColor(Color.argb(255, 255, 193, 7));
                holder.check = true;
            }
        }

        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!holder.check) {
                    holder.cardView.setCardBackgroundColor(Color.argb(255, 255, 193, 7));
                    FilterSearchActivity.Clickbrand.add(brand);
                    holder.check = true;
                }
                else{
                    holder.cardView.setCardBackgroundColor(Color.WHITE);
                    for (int i = 0; i < FilterSearchActivity.Clickbrand.size(); i++){
                        if (FilterSearchActivity.Clickbrand.get(i).getBrandId().equals(brand.getBrandId()))
                            FilterSearchActivity.Clickbrand.remove(i);
                    }
                    holder.check = false;
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return brandList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        CardView cardView;
        TextView nameBrand;
        Boolean check = false;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            cardView = itemView.findViewById(R.id.Filter_layout);
            nameBrand = itemView.findViewById(R.id.Filter_name);
        }
    }
}
