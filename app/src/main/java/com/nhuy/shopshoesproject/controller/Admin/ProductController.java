package com.nhuy.shopshoesproject.controller.Admin;

import static android.content.ContentValues.TAG;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
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
import com.nhuy.shopshoesproject.view.Adapter.ProductsAdapter;

import java.util.ArrayList;

public class ProductController {

    private Context context;
    private FirebaseFirestore db;
    private ProductsAdapter productsAdapter;

    public ProductController(Activity Context) {
        context = Context;
        db = FirebaseFirestore.getInstance();

    }

    public void showMessage(String message) {
        Toast.makeText(context.getApplicationContext(), message, Toast.LENGTH_SHORT).show();
    }
    public interface FirebaseCallback{
        void onCallback(ArrayList<Product> products);
    }
    public void getDataFromFirebase(FirebaseCallback firebaseCallback){
        ArrayList<Product> productArrayList= new ArrayList<>();
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        CollectionReference reference = db.collection("Products");
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
                                Log.d("ShowEventInfo:", product.toString());
                            }
                        } else {
                            Log.d(TAG, "Error getting documents: ", task.getException());
                            showMessage("Error" + task.getException());
                        }
                        firebaseCallback.onCallback(productArrayList);
                    }
                });
    }

//    public void searchFunc(EditText nameInput, ArrayList<BrandModel> brandArrayList, RecyclerView recyclerView, TextView noBranch, Activity context) {
//        nameInput.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//
//            }
//
//            @Override
//            public void onTextChanged(CharSequence s, int start, int before, int count) {
//                if (s.toString().trim().length() == 0) {
//                    if (brandArrayList.size() != 0) {
//                        recyclerView.setVisibility(View.VISIBLE);
//                        noBranch.setVisibility(View.GONE);
//                    } else {
//                        recyclerView.setVisibility(View.GONE);
//                        noBranch.setVisibility(View.VISIBLE);
//                    }
//
//                    mBrandAdapter = new BrandAdapter(context, brandArrayList);
//                    recyclerView.setAdapter(mBrandAdapter);
//                    mBrandAdapter.notifyDataSetChanged();
//                } else {
//                    ArrayList<BrandModel> clone = new ArrayList<>();
//                    for (BrandModel element : brandArrayList) {
//                        if (element.getBrandName().toLowerCase().contains(s.toString().toLowerCase())) {
//                            clone.add(element);
//                        }
//                    }
//                    if (clone.size() != 0) {
//                        recyclerView.setVisibility(View.VISIBLE);
//                        noBranch.setVisibility(View.GONE);
//                    } else {
//                        recyclerView.setVisibility(View.GONE);
//                        noBranch.setVisibility(View.VISIBLE);
//                    }
//
//                    mBrandAdapter = new BrandAdapter(context, clone);
//                    recyclerView.setAdapter(mBrandAdapter);
//                    mBrandAdapter.notifyDataSetChanged();
//                }
//            }
//
//            @Override
//            public void afterTextChanged(Editable s) {
//
//            }
//        });
//    }
//
//    public void UpdateData(ProgressBar progressBar, String collection, BrandModel brand, String docsID ) {
//        progressBar.setVisibility(View.VISIBLE);
//        db.collection(collection).document(docsID)
//                .update("brandId", brand.getBrandId(),
//                        "brandName", brand.getBrandName())
//                .addOnSuccessListener(new OnSuccessListener<Void>() {
//                    @Override
//                    public void onSuccess(Void unused) {
//                        showMessage("Sửa thông tin thành công");
//                        progressBar.setVisibility(View.GONE);
//                    }
//                });
//        ((Activity) context).finish();
//    }

}
