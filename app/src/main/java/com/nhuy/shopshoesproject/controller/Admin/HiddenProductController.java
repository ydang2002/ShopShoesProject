package com.nhuy.shopshoesproject.controller.Admin;

import static android.content.ContentValues.TAG;

import android.app.Activity;
import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.nhuy.shopshoesproject.models.BrandModel;
import com.nhuy.shopshoesproject.models.Product;
import com.nhuy.shopshoesproject.view.Adapter.BrandAdapter;
import com.nhuy.shopshoesproject.view.Adapter.HiddenProductAdapter;

import java.util.ArrayList;

public class HiddenProductController {
    private HiddenProductAdapter hiddenProductAdapter;
    private FirebaseFirestore db;
    private Context context;

    public HiddenProductController(Activity Context) {
        db = FirebaseFirestore.getInstance();
        context = Context;
    }

    public HiddenProductController() {

    }

    public void showMessage(String message) {
        Toast.makeText(context.getApplicationContext(), message, Toast.LENGTH_SHORT).show();
    }

    public void hiddenProduct(Product product, String collection, String docsID) {
        db = FirebaseFirestore.getInstance();
        db.collection(collection).document(docsID)
                .update(

                        "hidden", product.getHidden()
                )
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
//
                    }
                });
    }

    public void getDataHiddenProduct(ProgressBar progressBar, String collection, ArrayList<Product> productArrayList, TextView noText, RecyclerView recyclerView, HiddenProductAdapter mAdapter, Activity context) {
        progressBar.setVisibility(View.VISIBLE);
        final int[] counter = {0};
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        CollectionReference reference = db.collection(collection);
        reference.get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@org.checkerframework.checker.nullness.qual.NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            QuerySnapshot snapshot = task.getResult();
                            for (QueryDocumentSnapshot document : snapshot) {
                                Log.d(TAG, document.getId() + " => " + document.getData());
                                Product product = new Product();
                                product = document.toObject(Product.class);
                                productArrayList.add(product);
                                counter[0]++;
                                if (counter[0] == task.getResult().size()) {
                                    if (productArrayList.size() > 0) {

                                        hiddenProductAdapter = new HiddenProductAdapter(context, productArrayList);
                                        recyclerView.setNestedScrollingEnabled(false);
                                        recyclerView.setLayoutManager(new LinearLayoutManager(context));
                                        recyclerView.setAdapter(hiddenProductAdapter);
                                        hiddenProductAdapter.notifyDataSetChanged();

                                        recyclerView.setVisibility(View.VISIBLE);
                                        noText.setVisibility(View.GONE);
                                    } else {
                                        recyclerView.setVisibility(View.GONE);
                                        noText.setVisibility(View.VISIBLE);
                                        mAdapter.notifyDataSetChanged();
                                    }
                                    progressBar.setVisibility(View.GONE);
                                }
                            }
                        } else {
                            noText.setVisibility(View.VISIBLE);
                            progressBar.setVisibility(View.GONE);
                            Log.d(TAG, "Error getting documents: ", task.getException());
                            showMessage("Error" + task.getException());
                        }
                    }
                });
    }

    public void searchFunc(EditText nameInput, ArrayList<Product> productArrayList, RecyclerView recyclerView, TextView noBranch, Activity context) {
        nameInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.toString().trim().length() == 0) {
                    if (productArrayList.size() != 0) {
                        recyclerView.setVisibility(View.VISIBLE);
                        noBranch.setVisibility(View.GONE);
                    } else {
                        recyclerView.setVisibility(View.GONE);
                        noBranch.setVisibility(View.VISIBLE);
                    }

                    hiddenProductAdapter = new HiddenProductAdapter(context, productArrayList);
                    recyclerView.setAdapter(hiddenProductAdapter);
                    hiddenProductAdapter.notifyDataSetChanged();
                } else {
                    ArrayList<Product> clone = new ArrayList<>();
                    for (Product element : productArrayList) {
                        if (element.getName().toLowerCase().contains(s.toString().toLowerCase())) {
                            clone.add(element);
                        }
                    }
                    if (clone.size() != 0) {
                        recyclerView.setVisibility(View.VISIBLE);
                        noBranch.setVisibility(View.GONE);
                    } else {
                        recyclerView.setVisibility(View.GONE);
                        noBranch.setVisibility(View.VISIBLE);
                    }

                    hiddenProductAdapter = new HiddenProductAdapter(context, clone);
                    recyclerView.setAdapter(hiddenProductAdapter);
                    hiddenProductAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }
}