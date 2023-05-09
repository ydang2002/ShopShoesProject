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
import com.nhuy.shopshoesproject.models.CategoryModel;
import com.nhuy.shopshoesproject.view.Activity.Customer.FilterSearchActivity;

import java.util.ArrayList;

public class FilterCategoryAdapter extends RecyclerView.Adapter<FilterCategoryAdapter.MyViewHolder> {
    ArrayList<CategoryModel> categoryList;
    Activity context;


    public FilterCategoryAdapter(ArrayList<CategoryModel> categoryList, Activity context) {
        this.categoryList = categoryList;
        this.context = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.single_item_category, parent, false);

        return new FilterCategoryAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        CategoryModel category = categoryList.get(position);
        holder.nameCategory.setText(category.getCategoryName());
        for (int i = 0; i< FilterSearchActivity.Clickcategory.size(); i++){
            if (category.getCategoryId().equals(FilterSearchActivity.Clickcategory.get(i).getCategoryId())){
                holder.cardView.setCardBackgroundColor(Color.argb(255, 255, 193, 7));
                holder.check = true;
            }
        }

        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!holder.check) {
                    holder.cardView.setCardBackgroundColor(Color.argb(255, 255, 193, 7));
                    FilterSearchActivity.Clickcategory.add(category);
                    holder.check = true;
                }
                else{
                    holder.cardView.setCardBackgroundColor(Color.WHITE);
                    for (int i = 0; i < FilterSearchActivity.Clickcategory.size(); i++){
                        if (FilterSearchActivity.Clickcategory.get(i).getCategoryId().equals(category.getCategoryId()))
                            FilterSearchActivity.Clickcategory.remove(i);
                    }
                    holder.check = false;
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return categoryList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        CardView cardView;
        TextView nameCategory;
        Boolean check = false;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            cardView = itemView.findViewById(R.id.Filter_layout);
            nameCategory = itemView.findViewById(R.id.Filter_name);
        }
    }
}

