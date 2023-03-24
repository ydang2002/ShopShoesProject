package com.nhuy.shopshoesproject.controller.Admin;

import static android.content.ContentValues.TAG;
import static android.widget.Toast.LENGTH_SHORT;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.nhuy.shopshoesproject.R;
import com.nhuy.shopshoesproject.models.BrandModel;
import com.nhuy.shopshoesproject.models.Product;
import com.nhuy.shopshoesproject.view.Activity.Admin.Product.NewProductActivity;
import com.nhuy.shopshoesproject.view.Adapter.BrandAdapter;
import com.nhuy.shopshoesproject.view.Adapter.HiddenProductAdapter;
import com.nhuy.shopshoesproject.view.Adapter.ProductsAdapter;
import com.nhuy.shopshoesproject.view.constants.Constants;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nonnull;

public class CrudController extends AppCompatActivity {

    private FirebaseFirestore db;
    private Context context;
    StorageReference storageRef;
    private String downloadImageUrl = "";
    private ProductsAdapter mAdapter;
//    private ArrayList<Product> productArrayList;

    int PICK_IMAGE_REQUEST = 111;

    public CrudController(Activity Context) {
//        productArrayList =new ArrayList<Product>();
        db = FirebaseFirestore.getInstance();
        context = Context;
        storageRef = FirebaseStorage.getInstance().getReference();
//        mAdapter = new ProductsAdapter(productArrayList,true);

    }

    public void showMessage(String message) {
        Toast.makeText(context.getApplicationContext(), message, Toast.LENGTH_SHORT).show();
    }

    public <T> void saveDatabase(String id, String collection, T classModel) {
        DocumentReference docIdRef = db.collection(collection).document(id);// DocumentReference dùng để trỏ đến hoặc tạo một collection
        docIdRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {// dùng hàm get() lấy toàn bộ document
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) { //khi hoàn thành việc get() dữ liệu nằm trong task
                if (task.isSuccessful()) { // nếu task lấy về thành công
                    DocumentSnapshot document = task.getResult(); // task.getResult() trả về document
                    if (document.exists()) {
                        showMessage("Dữ liệu đã tồn tại");
                    } else {
                        db.collection(collection).document(id).set(classModel);
                        showMessage("Thêm dữ liệu thành công");
                        ((Activity) context).finish();
                    }
                } else {
                    showMessage("Thêm dữ liệu không thành công");
                }
            }
        });
    }

    public void getDataProduct(String collection, String field, Spinner spinner) {
        CollectionReference subjectsRef = db.collection(collection);
        List<String> data = new ArrayList<>();
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(context.getApplicationContext(), android.R.layout.simple_spinner_item, data);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        subjectsRef.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@org.checkerframework.checker.nullness.qual.NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        String subject = document.getString(field);
                        Log.d("ShowEventInfo:", subject);
                        data.add(subject);
                    }
                    adapter.notifyDataSetChanged();
                }
            }
        });
    }

    public void UploadImage(Uri filePath, ProgressBar progressBar, String storage, Product product, String id) {
        if (filePath != null) {
            progressBar.setVisibility(View.VISIBLE);
            final StorageReference childRef = storageRef.child(storage).child(id + ".jpg");
            final UploadTask uploadTask = childRef.putFile(filePath);
            uploadTask.addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@org.checkerframework.checker.nullness.qual.NonNull Exception e) {
                    String message = e.toString();
                    showMessage("Error: " + message);
                    progressBar.setVisibility(View.GONE);
                }
            }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    showMessage("Photo uploaded...");
                    Task<Uri> urlTask = uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                        @Override
                        public Task<Uri> then(@org.checkerframework.checker.nullness.qual.NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                            if (!task.isSuccessful()) {
                                throw task.getException();
                            }
                            downloadImageUrl = childRef.getDownloadUrl().toString();
                            return childRef.getDownloadUrl();
                        }
                    }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                        @Override
                        public void onComplete(@org.checkerframework.checker.nullness.qual.NonNull Task<Uri> task) {
                            if (task.isSuccessful()) {
                                downloadImageUrl = task.getResult().toString();
                                Log.d("imagUrl", downloadImageUrl);
                                product.setPhotoUrl(downloadImageUrl);

                                saveDatabase(id, Constants.PRODUCTS, product);
                            }
                        }
                    });
                }
            });

        } else {
            showMessage("Select an image");
        }
    }

//    public void uploadPhoto(){
//        Intent intent = new Intent();
//        intent.setType("image/*");
//        intent.setAction(Intent.ACTION_PICK);
//        startActivityForResult(Intent.createChooser(intent, "Select Image"), PICK_IMAGE_REQUEST);
//    }


    public void getDataFromFirebase(ProgressBar progressBar, String collection, ArrayList<Product> productArrayList, TextView noText, RecyclerView recyclerView, ProductsAdapter mAdapter) {
        progressBar.setVisibility(View.VISIBLE);
        final int[] counter = {0};
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        CollectionReference reference = db.collection(collection);
        reference
                .whereEqualTo("hidden", false)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@org.checkerframework.checker.nullness.qual.NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            QuerySnapshot snapshot = task.getResult();
                            for (QueryDocumentSnapshot document : snapshot) {
                                Log.d(TAG, document.getId() + " => " + document.getData());
                                Product product = new Product();
                                product = document.toObject(Product.class);
                                Log.d("ShowEventInfo:", product.toString());
                                productArrayList.add(product);
                                counter[0]++;
                                if (counter[0] == task.getResult().size()) {
                                    if (productArrayList.size() > 0) {
                                        recyclerView.setVisibility(View.VISIBLE);
                                        noText.setVisibility(View.GONE);
                                    } else {
                                        recyclerView.setVisibility(View.GONE);
                                        noText.setVisibility(View.VISIBLE);
                                        mAdapter.notifyDataSetChanged();
                                    }
                                    progressBar.setVisibility(View.GONE);
                                }
                                Log.d("ShowEventInfo:", product.toString());
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

    public void searchFunc(EditText nameInput, RecyclerView recyclerView, TextView noText, ArrayList<Product> productArrayList, Activity context) {
        nameInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.toString().trim().length() == 0) {
                    if (productArrayList.size() != 0) {
                        recyclerView.setVisibility(View.VISIBLE);
                        noText.setVisibility(View.GONE);
                    } else {
                        recyclerView.setVisibility(View.GONE);
                        noText.setVisibility(View.VISIBLE);
                    }

                    mAdapter = new ProductsAdapter(productArrayList, context, true);
                    recyclerView.setAdapter(mAdapter);
                    mAdapter.notifyDataSetChanged();
                } else {
                    ArrayList<Product> clone = new ArrayList<>();
                    for (Product element : productArrayList) {
                        if (element.getName().toLowerCase().contains(s.toString().toLowerCase())) {
                            clone.add(element);
                        }
                    }
                    if (clone.size() != 0) {
                        recyclerView.setVisibility(View.VISIBLE);
                        noText.setVisibility(View.GONE);
                    } else {
                        recyclerView.setVisibility(View.GONE);
                        noText.setVisibility(View.VISIBLE);
                    }

                    mAdapter = new ProductsAdapter(clone, context, true);
                    recyclerView.setAdapter(mAdapter);
                    mAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    private void updateDataProduct(Product product, String collection, String docsID) {
        db.collection(collection).document(docsID)
                .update(
                        "name", product.getName(),
                        "category", product.getCategory(),
                        "brand", product.getBrand(),
                        "sizeType", product.getSizeType(),
                        "size", product.getSize(),
                        "price", product.getPrice(),
                        "color", product.getColor(),
                        "stock", product.getStock(),
                        "description", product.getDescription(),
                        "photoUrl", product.getPhotoUrl()
                )
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        showMessage("Sửa thông tin thành công");
                        ((Activity) context).finish();
                    }
                });
    }

    public void UpdateImage(Uri filePath, ProgressBar progressBar, Product product, String docsID) {
        if (filePath != null) {
            progressBar.setVisibility(View.VISIBLE);
            final StorageReference childRef = storageRef.child("product_images").child(docsID + ".jpg");

            final UploadTask uploadTask = childRef.putFile(filePath);
            uploadTask.addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@org.checkerframework.checker.nullness.qual.NonNull Exception e) {
                    String message = e.toString();
                    showMessage("Error: " + message);
                    progressBar.setVisibility(View.GONE);
                }
            }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    showMessage("Đã tải ảnh...");
                    progressBar.setVisibility(View.GONE);
                    Task<Uri> urlTask = uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                        @Override
                        public Task<Uri> then(@org.checkerframework.checker.nullness.qual.NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                            if (!task.isSuccessful()) {
                                throw task.getException();
                            }
                            downloadImageUrl = childRef.getDownloadUrl().toString();
                            return childRef.getDownloadUrl();
                        }
                    }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                        @Override
                        public void onComplete(@org.checkerframework.checker.nullness.qual.NonNull Task<Uri> task) {
                            if (task.isSuccessful()) {
                                downloadImageUrl = task.getResult().toString();
                                Log.d("imagUrl", downloadImageUrl);
                                product.setPhotoUrl(downloadImageUrl);
                                updateDataProduct(product, Constants.PRODUCTS, docsID);
                            }
                        }
                    });
                }
            });

        } else {
            showMessage("Chọn ảnh mới");
        }
    }

}
