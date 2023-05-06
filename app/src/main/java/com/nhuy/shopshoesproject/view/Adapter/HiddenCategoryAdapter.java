package com.nhuy.shopshoesproject.view.Adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.nhuy.shopshoesproject.R;
import com.nhuy.shopshoesproject.controller.Admin.HiddenBrandController;
import com.nhuy.shopshoesproject.controller.Admin.HiddenCategoryController;
import com.nhuy.shopshoesproject.models.BrandModel;
import com.nhuy.shopshoesproject.models.CategoryModel;
import com.nhuy.shopshoesproject.view.constants.Constants;

import java.util.List;

public class HiddenCategoryAdapter extends RecyclerView.Adapter<HiddenCategoryAdapter.MyViewHolder> {

    List<CategoryModel> categoryList;
    Activity context;

    public HiddenCategoryAdapter(Activity context, List<CategoryModel> categoryList) {
        this.categoryList = categoryList;
        this.context = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_single_hidden_category, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        CategoryModel categoryModel = categoryList.get(position);
        holder.id.setText(categoryModel.getCategoryId());
        holder.name.setText(categoryModel.getCategoryName());

        if (categoryModel.getHidden() == false) {
            holder.aSwitch.setChecked(false);
        } else {
            holder.aSwitch.setChecked(true);
        }

        holder.aSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if (isChecked) {
                    categoryModel.setHidden(true);
                    holder.hiddenCategoryController.hiddenCategory(categoryModel, Constants.CATEGORY, categoryModel.getCategoryId());
                    Toast.makeText(context, "Thương hiệu đã ẩn", Toast.LENGTH_SHORT).show();
                } else {
                    categoryModel.setHidden(false);
                    holder.hiddenCategoryController.hiddenCategory(categoryModel, Constants.CATEGORY, categoryModel.getCategoryId());
                    Toast.makeText(context, "Thương hiệu hiển thị", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return categoryList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView name, id;
        Switch aSwitch;
        HiddenCategoryController hiddenCategoryController;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            id = itemView.findViewById(R.id.hidden_id_category);
            name = itemView.findViewById(R.id.hidden_name_category);
            aSwitch = itemView.findViewById(R.id.idSwitch_category);
            hiddenCategoryController = new HiddenCategoryController();
        }
    }
}
