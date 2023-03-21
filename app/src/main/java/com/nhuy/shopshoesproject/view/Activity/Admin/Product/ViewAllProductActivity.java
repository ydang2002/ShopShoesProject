package com.nhuy.shopshoesproject.view.Activity.Admin.Product;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.FirebaseFirestore;
import com.nhuy.shopshoesproject.R;
import com.nhuy.shopshoesproject.controller.Admin.CrudController;
import com.nhuy.shopshoesproject.models.Product;
import com.nhuy.shopshoesproject.view.Adapter.ProductsAdapter;
import com.nhuy.shopshoesproject.view.constants.Constants;

import java.util.ArrayList;

public class ViewAllProductActivity extends AppCompatActivity {

    private ProductsAdapter mAdapter;
    private RecyclerView recyclerView;
    private ArrayList<Product> productArrayList;

    DatabaseReference myRootRef;
    private ProgressBar progressBar;
    private TextView noText;
    private EditText nameInput;
    private FirebaseFirestore firestore;
    private CrudController crudController;
    private Product product;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_all_product);

        productArrayList = new ArrayList<Product>();
        recyclerView = findViewById(R.id.product_list);
        progressBar = findViewById(R.id.spin_progress_bar);
        noText = findViewById(R.id.no_product);
        nameInput = findViewById(R.id.name_input);
        myRootRef = FirebaseDatabase.getInstance().getReference();

        mAdapter = new ProductsAdapter(productArrayList, ViewAllProductActivity.this, true);
        recyclerView.setNestedScrollingEnabled(false);
        recyclerView.setLayoutManager(new GridLayoutManager(getApplicationContext(), 2));
        recyclerView.setAdapter(mAdapter);
        mAdapter.notifyDataSetChanged();
        crudController = new CrudController(ViewAllProductActivity.this);
        product = new Product();

        crudController.getDataFromFirebase(progressBar, Constants.PRODUCTS, productArrayList, noText, recyclerView, mAdapter);
        crudController.searchFunc(nameInput, recyclerView, noText, productArrayList, ViewAllProductActivity.this);
    }

    public void goBack(View view) {
        finish();
    }
}