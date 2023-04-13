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
import com.nhuy.shopshoesproject.controller.Admin.HiddenProductController;
import com.nhuy.shopshoesproject.models.BrandModel;
import com.nhuy.shopshoesproject.view.constants.Constants;

import java.util.List;

public class HiddenBrandAdapter extends RecyclerView.Adapter<HiddenBrandAdapter.MyViewHolder> {

    List<BrandModel> brandList;
    Activity context;

    public HiddenBrandAdapter(Activity context, List<BrandModel> brandList) {
        this.brandList = brandList;
        this.context = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_single_hidden_brand, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        BrandModel brandModel = brandList.get(position);
        holder.id.setText(brandModel.getBrandId());
        holder.name.setText(brandModel.getBrandName());

        if (brandModel.getHidden() == false) {
            holder.aSwitch.setChecked(false);
        } else {
            holder.aSwitch.setChecked(true);
        }

        holder.aSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if (isChecked) {
                    brandModel.setHidden(true);
                    holder.hiddenBrandController.hiddenBrand(brandModel, Constants.BRAND, brandModel.getBrandId());
                    Toast.makeText(context, "Thương hiệu đã ẩn", Toast.LENGTH_SHORT).show();
                } else {
                    brandModel.setHidden(false);
                    holder.hiddenBrandController.hiddenBrand(brandModel, Constants.BRAND, brandModel.getBrandId());
                    Toast.makeText(context, "Thương hiệu hiển thị", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return brandList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView name, id;
        Switch aSwitch;
        HiddenBrandController hiddenBrandController;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            id = itemView.findViewById(R.id.hidden_id_brand);
            name = itemView.findViewById(R.id.hidden_name_brand);
            aSwitch = itemView.findViewById(R.id.idSwitch_brand);
            hiddenBrandController = new HiddenBrandController();
        }
    }
}
