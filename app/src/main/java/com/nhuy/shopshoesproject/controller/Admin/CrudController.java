package com.nhuy.shopshoesproject.controller.Admin;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

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
import com.nhuy.shopshoesproject.models.Product;
import com.nhuy.shopshoesproject.view.Activity.Admin.Product.NewProductActivity;
import com.nhuy.shopshoesproject.view.constants.Constants;

import java.util.ArrayList;
import java.util.List;

public class CrudController extends AppCompatActivity {

    private FirebaseFirestore db;
    private Context context;
    StorageReference storageRef;
    private String downloadImageUrl = "";

    int PICK_IMAGE_REQUEST = 111;

    public CrudController(Activity Context){
        db = FirebaseFirestore.getInstance();
        context = Context;
        storageRef = FirebaseStorage.getInstance().getReference();
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
            final StorageReference childRef = storageRef.child(storage).child(System.currentTimeMillis() + ".jpg");
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
}
