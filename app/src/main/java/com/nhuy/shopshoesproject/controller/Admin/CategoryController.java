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
import com.nhuy.shopshoesproject.models.CategoryModel;
import com.nhuy.shopshoesproject.view.Adapter.BrandAdapter;
import com.nhuy.shopshoesproject.view.Adapter.CategoryAdapter;

import java.util.ArrayList;

public class CategoryController {
    private Context context;
    private FirebaseFirestore db;
    private CategoryAdapter mCategoryAdapter;

    public CategoryController(Activity Context) {
        context = Context;
        db = FirebaseFirestore.getInstance();

    }

    public void showMessage(String message) {
        Toast.makeText(context.getApplicationContext(), message, Toast.LENGTH_SHORT).show();
    }

    public void getDataFromFirebase(ProgressBar progressBar, String collection, ArrayList<CategoryModel> CategoryArrayList, TextView noText, RecyclerView recyclerView, CategoryAdapter mAdapter, Activity context) {
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
                                CategoryModel category = new CategoryModel();
                                category = document.toObject(CategoryModel.class);
                                CategoryArrayList.add(category);
                                counter[0]++;
                                if (counter[0] == task.getResult().size()) {
                                    if (CategoryArrayList.size() > 0) {

                                        mCategoryAdapter = new CategoryAdapter(context, CategoryArrayList);
                                        recyclerView.setNestedScrollingEnabled(false);
                                        recyclerView.setLayoutManager(new LinearLayoutManager(context));
                                        recyclerView.setAdapter(mCategoryAdapter);
                                        mCategoryAdapter.notifyDataSetChanged();

                                        recyclerView.setVisibility(View.VISIBLE);
                                        noText.setVisibility(View.GONE);
                                    } else {
                                        recyclerView.setVisibility(View.GONE);
                                        noText.setVisibility(View.VISIBLE);
                                        mAdapter.notifyDataSetChanged();
                                    }
                                    progressBar.setVisibility(View.GONE);
                                }
                                Log.d("ShowEventInfo:", category.toString());
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

    public void searchFunc(EditText nameInput, ArrayList<CategoryModel> CategoryArrayList, RecyclerView recyclerView, TextView noBranch, Activity context) {
        nameInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.toString().trim().length() == 0) {
                    if (CategoryArrayList.size() != 0) {
                        recyclerView.setVisibility(View.VISIBLE);
                        noBranch.setVisibility(View.GONE);
                    } else {
                        recyclerView.setVisibility(View.GONE);
                        noBranch.setVisibility(View.VISIBLE);
                    }

                    mCategoryAdapter = new CategoryAdapter(context, CategoryArrayList);
                    recyclerView.setAdapter(mCategoryAdapter);
                    mCategoryAdapter.notifyDataSetChanged();
                } else {
                    ArrayList<CategoryModel> clone = new ArrayList<>();
                    for (CategoryModel element : CategoryArrayList) {
                        if (element.getCategoryName().toLowerCase().contains(s.toString().toLowerCase())) {
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

                    mCategoryAdapter = new CategoryAdapter(context, clone);
                    recyclerView.setAdapter(mCategoryAdapter);
                    mCategoryAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    public void UpdateDataCategory(ProgressBar progressBar, String collection, CategoryModel categoryModel, String docsID ) {
        progressBar.setVisibility(View.VISIBLE);
        db.collection(collection).document(docsID)
                .update("CategoryId", categoryModel.getCategoryId(),
                        "CategoryName", categoryModel.getCategoryName())
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        showMessage("Sửa thông tin thành công");
                        progressBar.setVisibility(View.GONE);
                    }
                });
        ((Activity) context).finish();
    }
}
