package com.nhuy.shopshoesproject.view.Activity.Admin.Product;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.nhuy.shopshoesproject.R;
import com.nhuy.shopshoesproject.controller.Admin.CrudController;
import com.nhuy.shopshoesproject.models.Product;
import com.nhuy.shopshoesproject.view.constants.Constants;

import org.checkerframework.checker.nullness.qual.NonNull;

import java.util.ArrayList;
import java.util.List;

public class NewProductActivity extends AppCompatActivity {

    String[] sizeList = {"", "34-35", "35", "35-36", "36", "36-37", "37", "37-38", "38", "38-39", "39", "39-40", "40", "40-41", "41", "41-42", "42", "42-43", "43", "43-44", "44", "44-45", "45", "46", "47", "48", "49"};
    Spinner categorySpinner, brandSpinner, sizeTypeSpinner, sizeSpinner;
    String category = "";
    String brand = "";
    String sizeType = "";
    String size = "";

    int PICK_IMAGE_REQUEST = 111;
    Uri filePath = null;
    StorageReference storageRef;
    FirebaseFirestore db;
    ImageView uploadPhotoBtn, productImg;
    Button addBtn;
    private EditText idProductEt, nameEt, priceEt, colorEt, stockEt, descriptionEt;
    private ProgressBar progressBar;
    Product product;
    private CrudController crudController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_product);

        initAll();

        ArrayAdapter<String> sizeAdapter = new ArrayAdapter<String>(NewProductActivity.this, android.R.layout.simple_list_item_1, sizeList);
        sizeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sizeSpinner.setAdapter(sizeAdapter);

        SettingClickListners();
    }

    private void initAll() {
        categorySpinner = findViewById(R.id.product_category_Spinner);
        brandSpinner = findViewById(R.id.product_brand_Spinner);
        sizeTypeSpinner = findViewById(R.id.product_size_type_Spinner);
        sizeSpinner = findViewById(R.id.product_size_Spinner);
        progressBar = findViewById(R.id.progress_bar);

        uploadPhotoBtn = findViewById(R.id.upload_image_btn);
        productImg = findViewById(R.id.product_image);
        addBtn = findViewById(R.id.add_btn);

        idProductEt = findViewById(R.id.product_id_et);
        nameEt = findViewById(R.id.product_name_et);
        priceEt = findViewById(R.id.price_et);
        colorEt = findViewById(R.id.color_et);
        stockEt = findViewById(R.id.stock_et);
        descriptionEt = findViewById(R.id.description_tv);

        storageRef = FirebaseStorage.getInstance().getReference();
        db = FirebaseFirestore.getInstance();
        product = new Product();
        crudController = new CrudController(NewProductActivity.this);
    }

    public void goBack(View view) {
        finish();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            filePath = data.getData();
            Bitmap bitmap;
            try {
                bitmap = BitmapFactory.decodeStream(getContentResolver().openInputStream(filePath));
                productImg.setImageBitmap(bitmap);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void SettingClickListners() {
        Spinner spinnerCategory = (Spinner) findViewById(R.id.product_category_Spinner);
        Spinner spinnerBrand = (Spinner) findViewById(R.id.product_brand_Spinner);
        Spinner spinnerSizeType = (Spinner) findViewById(R.id.product_size_type_Spinner);
        crudController.getDataProduct(Constants.CATEGORY, Constants.FIELD_CATEGORY, spinnerCategory);
        crudController.getDataProduct(Constants.BRAND, Constants.FIELD_BRAND, spinnerBrand);
        crudController.getDataProduct(Constants.SIZE_TYPE, Constants.FIELD_SIZE_TYPE, spinnerSizeType);


        categorySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                category = String.valueOf(parent.getItemAtPosition(position));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
        brandSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                brand = String.valueOf(parent.getItemAtPosition(position));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
        sizeTypeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                sizeType = String.valueOf(parent.getItemAtPosition(position));
                if (sizeType.equals("")) {

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
        sizeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                size = String.valueOf(parent.getItemAtPosition(position));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        uploadPhotoBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_PICK);
                startActivityForResult(Intent.createChooser(intent, "Select Image"), PICK_IMAGE_REQUEST);
            }
        });

        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkData();
            }
        });
    }

    private void checkData(){
        String name = nameEt.getText().toString().trim();
        String id = idProductEt.getText().toString().trim();
        String price = priceEt.getText().toString().trim();
        String color = colorEt.getText().toString().trim();
        String stock = stockEt.getText().toString().trim();
        String desc = descriptionEt.getText().toString().trim();
        if (filePath == null) {
            Toast.makeText(NewProductActivity.this, "Chưa chọn ảnh", Toast.LENGTH_SHORT).show();
        } else if (TextUtils.isEmpty(id)) {
            idProductEt.setError("Nhập mã sản phẩm");
            idProductEt.requestFocus();
        } else if (TextUtils.isEmpty(name)) {
            nameEt.setError("Nhập tên sản phẩm");
            nameEt.requestFocus();
        } else if (category.equals("")) {
            Toast.makeText(NewProductActivity.this, "Chưa chọn thể loại", Toast.LENGTH_SHORT).show();
        } else if (brand.equals("")) {
            Toast.makeText(NewProductActivity.this, "Chưa chọn thương hiệu", Toast.LENGTH_SHORT).show();
        } else if (sizeType.equals("")) {
            Toast.makeText(NewProductActivity.this, "Chưa chọn loại size", Toast.LENGTH_SHORT).show();
        } else if (size.equals("")) {
            Toast.makeText(NewProductActivity.this, "Chưa chọn size", Toast.LENGTH_SHORT).show();
        } else if (TextUtils.isEmpty(price)) {
            priceEt.setError("Nhập giá sản phẩm");
            priceEt.requestFocus();
        } else if (TextUtils.isEmpty(color)) {
            colorEt.setError("Nhập màu sản phẩm");
            colorEt.requestFocus();
        } else if (TextUtils.isEmpty(stock)) {
            stockEt.setError("Nhập số lượng sản phẩm");
            stockEt.requestFocus();
        } else if (TextUtils.isEmpty(desc)) {
            descriptionEt.setError("Nhập nội dung");
            descriptionEt.requestFocus();
        } else {
            product.setProductId(id);
            product.setName(name);
            product.setCategory(category);
            product.setBrand(brand);
            product.setSizeType(sizeType);
            product.setSize(size);
            product.setPrice(Integer.parseInt(price));
            product.setColor(color);
            product.setStock(Integer.parseInt(stock));
            product.setDescription(desc);

            crudController.UploadImage(filePath, progressBar, Constants.IMAGES, product, id);
        }
    }

}