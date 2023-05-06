package com.nhuy.shopshoesproject.view.Activity.Admin.Brand;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.nhuy.shopshoesproject.R;
import com.nhuy.shopshoesproject.controller.Admin.HiddenBrandController;
import com.nhuy.shopshoesproject.controller.Admin.HiddenProductController;
import com.nhuy.shopshoesproject.models.BrandModel;
import com.nhuy.shopshoesproject.models.Product;
import com.nhuy.shopshoesproject.view.Activity.Admin.Product.HiddenProduct;
import com.nhuy.shopshoesproject.view.Adapter.HiddenBrandAdapter;
import com.nhuy.shopshoesproject.view.Adapter.HiddenProductAdapter;
import com.nhuy.shopshoesproject.view.constants.Constants;

import java.util.ArrayList;

public class HiddenBrandActivity extends AppCompatActivity {

    private HiddenBrandAdapter hiddenBrandAdapter;
    private RecyclerView recyclerView;
    private ArrayList<BrandModel> brandModelArrayList;
    private ProgressBar progressBar;
    private TextView noText;
    private EditText nameInput;
    private BrandModel brandModel;
    private HiddenBrandController controller;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hidden_brand);

        innit();

        controller.getDataFromFirebase(progressBar, Constants.BRAND, brandModelArrayList, noText, recyclerView, hiddenBrandAdapter, HiddenBrandActivity.this);
    }

    private void innit() {
        brandModelArrayList = new ArrayList<BrandModel>();
        recyclerView = findViewById(R.id.brand_recyclerview_hidden);
        progressBar = findViewById(R.id.progress_bar_hidden_brand);
        noText = findViewById(R.id.no_hidden_brand);
        brandModel = new BrandModel();
        nameInput = findViewById(R.id.name_input_hidden_brand);
        controller = new HiddenBrandController(HiddenBrandActivity.this);
    }

    public void goBack(View view) {
        finish();
    }
}