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
import com.nhuy.shopshoesproject.models.CategoryModel;
import com.nhuy.shopshoesproject.view.Adapter.CategoryAdapter;
import com.nhuy.shopshoesproject.view.constants.Constants;

import java.util.ArrayList;

public class ViewCategoryActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ArrayList<CategoryModel> categoryArrayList;
    private ProgressBar progressBar;
    private TextView noCategory;
    private EditText nameInput;
    private CategoryController categoryController;
    private CategoryAdapter mCategoryAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_category);

        categoryArrayList = new ArrayList<CategoryModel>();
        recyclerView = findViewById(R.id.category_recyclerview);
        progressBar = findViewById(R.id.progress_bar_category);
        noCategory = findViewById(R.id.no_category);
        nameInput = findViewById(R.id.name_input);
        categoryController = new CategoryController(ViewCategoryActivity.this);

        categoryController.getDataFromFirebase(progressBar, Constants.CATEGORY, categoryArrayList, noCategory, recyclerView, mCategoryAdapter, ViewCategoryActivity.this);
        categoryController.searchFunc(nameInput, categoryArrayList, recyclerView, noCategory, ViewCategoryActivity.this);
    }

    public void goBack(View view) {
        finish();
    }
}