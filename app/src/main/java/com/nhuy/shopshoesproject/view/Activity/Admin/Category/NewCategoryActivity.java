package com.nhuy.shopshoesproject.view.Activity.Admin.Category;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

import com.google.firebase.firestore.FirebaseFirestore;
import com.nhuy.shopshoesproject.R;
import com.nhuy.shopshoesproject.controller.Admin.CrudController;
import com.nhuy.shopshoesproject.models.CategoryModel;
import com.nhuy.shopshoesproject.view.constants.Constants;

public class NewCategoryActivity extends AppCompatActivity {

    private Button addBtnCategory;
    private EditText idCategory, nameCategory;
    private CategoryModel categoryModel;
    private CrudController crudController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_category);

        initAll();
        settingClickListners();
    }

    private void settingClickListners() {
        addBtnCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = nameCategory.getText().toString().trim();
                String id = idCategory.getText().toString().trim();

                if (TextUtils.isEmpty(id)) {
                    idCategory.setError("Nhập mã thể loại");
                    idCategory.requestFocus();
                    return;
                }

                if (TextUtils.isEmpty(name)) {
                    nameCategory.setError("Nhập tên thể loại");
                    nameCategory.requestFocus();
                    return;
                }

                categoryModel.setCategoryId(id);
                categoryModel.setCategoryName(name);
                categoryModel.setHidden(false);
                crudController.saveDatabase(id, Constants.CATEGORY, categoryModel);
            }
        });
    }

    private void initAll() {
        idCategory = findViewById(R.id.category_id);
        nameCategory = findViewById(R.id.category_name);
        addBtnCategory = findViewById(R.id.add_btn_category);

        categoryModel = new CategoryModel();
        crudController = new CrudController(NewCategoryActivity.this);
    }
}