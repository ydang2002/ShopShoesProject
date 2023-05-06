package com.nhuy.shopshoesproject.view.Activity.Admin.Category;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.nhuy.shopshoesproject.R;
import com.nhuy.shopshoesproject.controller.Admin.CategoryController;
import com.nhuy.shopshoesproject.controller.Admin.HiddenBrandController;
import com.nhuy.shopshoesproject.controller.Admin.HiddenCategoryController;
import com.nhuy.shopshoesproject.models.BrandModel;
import com.nhuy.shopshoesproject.models.CategoryModel;
import com.nhuy.shopshoesproject.view.Adapter.HiddenBrandAdapter;
import com.nhuy.shopshoesproject.view.Adapter.HiddenCategoryAdapter;
import com.nhuy.shopshoesproject.view.constants.Constants;

import java.util.ArrayList;

public class HiddenCategoryActivity extends AppCompatActivity {

    private HiddenCategoryAdapter hiddenCategoryAdapter;
    private RecyclerView recyclerView;
    private ArrayList<CategoryModel> categoryModelArrayList;
    private ProgressBar progressBar;
    private TextView noText;
    private EditText nameInput;
    private CategoryModel categoryModel;
    private HiddenCategoryController controller;
    private CategoryController categoryController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hidden_category);

        innit();
        controller.getDataFromFirebase(progressBar, Constants.CATEGORY, categoryModelArrayList, noText, recyclerView, hiddenCategoryAdapter, HiddenCategoryActivity.this);
        categoryController.searchFunc(nameInput, categoryModelArrayList, recyclerView, noText, HiddenCategoryActivity.this);
    }

    private void innit() {
        categoryModelArrayList = new ArrayList<CategoryModel>();
        recyclerView = findViewById(R.id.category_recyclerview_hidden);
        progressBar = findViewById(R.id.progress_bar_hidden_category);
        noText = findViewById(R.id.no_hidden_category);
        categoryModel = new CategoryModel();
        nameInput = findViewById(R.id.name_input_hidden_category);
        controller = new HiddenCategoryController(HiddenCategoryActivity.this);
        categoryController = new CategoryController(HiddenCategoryActivity.this);
    }

    public void goBack(View view) {
        finish();
    }
}