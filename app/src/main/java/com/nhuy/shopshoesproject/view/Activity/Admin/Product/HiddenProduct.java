package com.nhuy.shopshoesproject.view.Activity.Admin.Product;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.nhuy.shopshoesproject.R;
import com.nhuy.shopshoesproject.controller.Admin.CrudController;
import com.nhuy.shopshoesproject.controller.Admin.HiddenProductController;
import com.nhuy.shopshoesproject.models.Product;
import com.nhuy.shopshoesproject.view.Activity.Admin.Brand.ViewAllBrandActivity;
import com.nhuy.shopshoesproject.view.Adapter.HiddenProductAdapter;
import com.nhuy.shopshoesproject.view.Adapter.ProductsAdapter;
import com.nhuy.shopshoesproject.view.constants.Constants;

import java.util.ArrayList;

public class HiddenProduct extends AppCompatActivity {

    private HiddenProductAdapter hiddenProductAdapter;
    private RecyclerView recyclerView;
    private ArrayList<Product> productArrayList;
    private ProgressBar progressBar;
    private TextView noText;
    private EditText nameInput;
    private Product product;
    private HiddenProductController controller;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hidden_product);

        innit();

        controller.getDataHiddenProduct(progressBar, Constants.PRODUCTS, productArrayList, noText, recyclerView, hiddenProductAdapter, HiddenProduct.this);
        controller.searchFunc(nameInput, productArrayList, recyclerView, noText, HiddenProduct.this);
    }

    private void innit() {
        productArrayList = new ArrayList<Product>();
        recyclerView = findViewById(R.id.brand_recyclerview_hidden);
        progressBar = findViewById(R.id.progress_bar_hidden);
        noText = findViewById(R.id.no_hidden);
        product = new Product();
        nameInput = findViewById(R.id.name_input_hidden);
        controller = new HiddenProductController(HiddenProduct.this);
    }

    public void goBack(View view) {
        finish();
    }

}