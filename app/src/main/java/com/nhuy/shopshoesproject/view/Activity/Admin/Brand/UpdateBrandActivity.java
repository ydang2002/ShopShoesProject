package com.nhuy.shopshoesproject.view.Activity.Admin.Brand;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.firebase.firestore.FirebaseFirestore;
import com.nhuy.shopshoesproject.R;
import com.nhuy.shopshoesproject.controller.Admin.BrandController;
import com.nhuy.shopshoesproject.models.BrandModel;
import com.nhuy.shopshoesproject.view.constants.Constants;

public class UpdateBrandActivity extends AppCompatActivity {

    private Button updateBtn;
    private EditText nameBrandEdt;
    private TextView idBrandTv;
    private ProgressBar progressBar;
    private BrandModel brand;
    private BrandController brandController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_brand);

        initAll();

        SettingClickListners();

        brand = (BrandModel) getIntent().getSerializableExtra("brand");
        idBrandTv.setText(brand.getBrandId());
        nameBrandEdt.setText(brand.getBrandName());
    }

    private void initAll() {
        updateBtn = findViewById(R.id.update_btn_brand);
        idBrandTv = findViewById(R.id.brand_id_update);
        nameBrandEdt = findViewById(R.id.brand_name_update);
        progressBar = findViewById(R.id.progress_bar_brand_update);
        brandController = new BrandController(UpdateBrandActivity.this);

        brand = new BrandModel();
    }

    private void SettingClickListners() {
        updateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String name = nameBrandEdt.getText().toString().trim();

                if (TextUtils.isEmpty(name)) {
                    nameBrandEdt.setError("Nhập tên thương hiệu");
                    nameBrandEdt.requestFocus();
                } else {
                    brand.setBrandName(name);
                    final String docsID = (String) getIntent().getSerializableExtra("brandId");
                    brandController.UpdateDataCategory(progressBar, Constants.BRAND, brand, docsID);
                }
            }
        });
    }

    public void goBack(View view) {
        finish();
    }
}