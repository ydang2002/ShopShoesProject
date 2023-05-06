package com.nhuy.shopshoesproject.view.Activity.Admin.Category;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.nhuy.shopshoesproject.R;
import com.nhuy.shopshoesproject.controller.Admin.BrandController;
import com.nhuy.shopshoesproject.controller.Admin.CategoryController;
import com.nhuy.shopshoesproject.models.BrandModel;
import com.nhuy.shopshoesproject.models.CategoryModel;
import com.nhuy.shopshoesproject.view.constants.Constants;

public class UpdateCategoryActivity extends AppCompatActivity {

    private Button updateBtn;
    private EditText nameCategoryEdt;
    private TextView idCategoryTv;
    private ProgressBar progressBar;
    private CategoryModel categoryModel;
    private CategoryController categoryController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_category);

        initAll();

        SettingClickListners();
        categoryModel = (CategoryModel) getIntent().getSerializableExtra("category");
        idCategoryTv.setText(categoryModel.getCategoryId());
        nameCategoryEdt.setText(categoryModel.getCategoryName());
    }

    private void SettingClickListners() {
        updateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = nameCategoryEdt.getText().toString().trim();

                if (TextUtils.isEmpty(name)) {
                    nameCategoryEdt.setText("Nhập tên thể loại");
                    nameCategoryEdt.requestFocus();
                    return;
                }

                categoryModel.setCategoryName(name);
                final String docsID = (String) getIntent().getSerializableExtra("categoryId");
                categoryController.UpdateDataCategory(progressBar, Constants.CATEGORY, categoryModel, docsID);
            }
        });
    }

    private void initAll() {
        updateBtn = findViewById(R.id.update_btn_category);
        idCategoryTv = findViewById(R.id.category_id_update);
        progressBar = findViewById(R.id.progress_bar_category_update);
        nameCategoryEdt = findViewById(R.id.category_name_update);
        categoryController = new CategoryController(UpdateCategoryActivity.this);

        categoryModel = new CategoryModel();
    }

    public void goBack(View view) {
        finish();
    }
}