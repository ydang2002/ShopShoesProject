package com.nhuy.shopshoesproject.view.Adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.nhuy.shopshoesproject.R;
import com.nhuy.shopshoesproject.models.CategoryModel;
import com.nhuy.shopshoesproject.view.Activity.Admin.Category.UpdateCategoryActivity;

import java.util.ArrayList;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.ViewHolder> {

    private Context context;
    private ArrayList<CategoryModel> categoryArrayList;
    private CategoryModel category;

    public CategoryAdapter(Activity context, ArrayList<CategoryModel> categoryArrayList) {
        this.context = context;
        this.categoryArrayList = categoryArrayList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_single_category, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        CategoryModel category = categoryArrayList.get(position);
        holder.categoryID.setText(category.getCategoryId());
        holder.categoryName.setText(category.getCategoryName());

        holder.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, UpdateCategoryActivity.class);
                intent.putExtra("categoryId", category.getCategoryId());
                intent.putExtra("category", category);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return categoryArrayList.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView categoryID, categoryName;
        LinearLayout layout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            categoryID = itemView.findViewById(R.id.category_id);
            categoryName = itemView.findViewById(R.id.category_name);
            layout = itemView.findViewById(R.id.layout_category);
        }
    }

}