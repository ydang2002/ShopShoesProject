package com.nhuy.shopshoesproject.controller.Admin;

import static android.content.ContentValues.TAG;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.View;
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
import com.nhuy.shopshoesproject.models.CategoryModel;
import com.nhuy.shopshoesproject.view.Adapter.HiddenBrandAdapter;
import com.nhuy.shopshoesproject.view.Adapter.HiddenCategoryAdapter;

import java.util.ArrayList;

public class HiddenCategoryController {
    private HiddenCategoryAdapter hiddenCategoryAdapter;
    private FirebaseFirestore db;
    private Context context;

    public HiddenCategoryController(Activity Context) {
        db = FirebaseFirestore.getInstance();
        context = Context;
    }

    public HiddenCategoryController() {

    }

    public void showMessage(String message) {
        Toast.makeText(context.getApplicationContext(), message, Toast.LENGTH_SHORT).show();
    }

    public void getDataFromFirebase(ProgressBar progressBar, String collection, ArrayList<CategoryModel> categoryArrayList, TextView noText, RecyclerView recyclerView, HiddenCategoryAdapter mAdapter, Activity context) {
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
                               CategoryModel categoryModel = new CategoryModel();
                                categoryModel = document.toObject(CategoryModel.class);
                                categoryArrayList.add(categoryModel);
                                counter[0]++;
                                if (counter[0] == task.getResult().size()) {
                                    if (categoryArrayList.size() > 0) {

                                        hiddenCategoryAdapter = new HiddenCategoryAdapter(context, categoryArrayList);
                                        recyclerView.setNestedScrollingEnabled(false);
                                        recyclerView.setLayoutManager(new LinearLayoutManager(context));
                                        recyclerView.setAdapter(hiddenCategoryAdapter);
                                        hiddenCategoryAdapter.notifyDataSetChanged();

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

    public void hiddenCategory(CategoryModel categoryModel, String collection, String docsID) {
        db = FirebaseFirestore.getInstance();
        db.collection(collection).document(docsID)
                .update(

                        "hidden", categoryModel.getHidden()
                )
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                    }
                });
    }
}
