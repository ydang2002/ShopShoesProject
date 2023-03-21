package com.nhuy.shopshoesproject.view.Activity.Admin.Brand;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.nhuy.shopshoesproject.R;
import com.nhuy.shopshoesproject.controller.Admin.BrandController;
import com.nhuy.shopshoesproject.controller.Admin.CrudController;
import com.nhuy.shopshoesproject.models.BrandModel;
import com.nhuy.shopshoesproject.view.constants.Constants;

public class NewBrandActivity extends AppCompatActivity {

    private FirebaseFirestore db;
    private Button addBtnBrand;
    private EditText idBrand, nameBrand;
    private ProgressBar progressBar;
    private BrandModel brand;
    private CrudController crudControllerr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_brand);

        initAll();
        settingClickListners();
    }

    private void initAll() {
        idBrand = findViewById(R.id.brand_id);
        nameBrand = findViewById(R.id.brand_name);
        progressBar = findViewById(R.id.progress_bar_brand);
        addBtnBrand = findViewById(R.id.add_btn_brand);

        db = FirebaseFirestore.getInstance();
        brand = new BrandModel();
        crudControllerr = new CrudController(NewBrandActivity.this);
    }

    private void settingClickListners() {
        addBtnBrand.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = nameBrand.getText().toString().trim();
                String id = idBrand.getText().toString().trim();

                if(TextUtils.isEmpty(id)) {
                    idBrand.setError("Nhập mã thể loại");
                    idBrand.requestFocus();
                } else if(TextUtils.isEmpty(name)) {
                    nameBrand.setError("Nhập tên thể loại");
                    nameBrand.requestFocus();
                } else {
                    brand.setBrandId(id);
                    brand.setBrandName(name);
                    crudControllerr.saveDatabase(id, Constants.BRAND, brand);
                }
            }
        });
    }

    public void goBack(View view) {
        finish();
    }
}