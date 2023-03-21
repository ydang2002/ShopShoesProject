package com.nhuy.shopshoesproject.view.Activity.Admin.Brand;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.firebase.firestore.FirebaseFirestore;
import com.nhuy.shopshoesproject.R;
import com.nhuy.shopshoesproject.controller.Admin.BrandController;
import com.nhuy.shopshoesproject.models.BrandModel;
import com.nhuy.shopshoesproject.view.Activity.Admin.Product.ViewAllProductActivity;
import com.nhuy.shopshoesproject.view.Adapter.BrandAdapter;
import com.nhuy.shopshoesproject.view.constants.Constants;

import java.util.ArrayList;

public class ViewAllBrandActivity extends AppCompatActivity {

    private BrandAdapter mBrandAdapter;
    private RecyclerView recyclerView;
    private ArrayList<BrandModel> brandArrayList;

    private ProgressBar progressBar;
    private TextView noBranch;
    private EditText nameInput;
    private BrandModel brand;
    private BrandController brandController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_all_brand);

        brandArrayList = new ArrayList<BrandModel>();
        recyclerView = findViewById(R.id.brand_recyclerview);
        progressBar = findViewById(R.id.progress_bar_brand);
        noBranch = findViewById(R.id.no_brand);
        nameInput = findViewById(R.id.name_input);
        brand = new BrandModel();
        brandController = new BrandController(ViewAllBrandActivity.this);

        brandController.getDataFromFirebase(progressBar, Constants.BRAND, brandArrayList, noBranch, recyclerView, mBrandAdapter, ViewAllBrandActivity.this);
        brandController.searchFunc(nameInput, brandArrayList, recyclerView, noBranch, ViewAllBrandActivity.this);
    }

    public void goBack(View view) {
        finish();
    }
}