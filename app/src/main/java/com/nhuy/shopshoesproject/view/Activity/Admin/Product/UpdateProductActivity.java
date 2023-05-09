package com.nhuy.shopshoesproject.view.Activity.Admin.Product;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.nhuy.shopshoesproject.R;
import com.nhuy.shopshoesproject.controller.Admin.CrudController;
import com.nhuy.shopshoesproject.models.Product;
import com.nhuy.shopshoesproject.view.constants.Constants;
import com.squareup.picasso.Picasso;

import java.net.URI;
import java.net.URL;

public class UpdateProductActivity extends AppCompatActivity {

    String[] sizeList = {"", "34-35", "35", "35-36", "36", "36-37", "37", "37-38", "38", "38-39", "39", "39-40", "40", "40-41", "41", "41-42", "42", "42-43", "43", "43-44", "44", "44-45", "45", "46", "47", "48", "49"};
    Spinner categorySpinner, brandSpinner, sizeTypeSpinner, sizeSpinner;
    String category = "";
    String brand = "";
    String sizeType = "";
    String size = "";

    int PICK_IMAGE_REQUEST = 111;
    Uri filePath = null;
    URL url;
    StorageReference storageRef;
    private FirebaseFirestore db;
    ImageView uploadPhotoBtn, productImg;
    Button updateBtn, deleteBtn;
    private String downloadImageUrl = "";
    private EditText nameEt, priceEt, colorEt, stockEt, descriptionEt;
    private ProgressBar progressBar;
    Product product, productEdit;
    private ProgressDialog loader;
    private CollectionReference colRefProducts;
    private CrudController crudController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_product);

        initAll();

        SettingClickListners();
        product = (Product) getIntent().getSerializableExtra("product");

        ArrayAdapter<String> sizeAdapter = new ArrayAdapter<String>(UpdateProductActivity.this, android.R.layout.simple_list_item_1, sizeList);
        sizeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sizeSpinner.setAdapter(sizeAdapter);
        for (int i = 0; i < sizeList.length; i++) {
            if (product.getSize().equals(sizeList[i])) {
                sizeSpinner.setSelection(i);
                break;
            }
        }
        if (product.getPhotoUrl() != null) {
            if (!product.getPhotoUrl().equals("")) {
                Picasso.get().load(product.getPhotoUrl()).placeholder(R.drawable.no_background_icon).into(productImg);
            }
        }

        nameEt.setText(product.getName());
        colorEt.setText(product.getColor());
        priceEt.setText(String.valueOf(product.getPrice()));
        stockEt.setText(String.valueOf(product.getStock()));
        descriptionEt.setText(product.getDescription());
    }

    private void initAll() {
        categorySpinner = findViewById(R.id.product_category_Spinner_update);
        brandSpinner = findViewById(R.id.product_brand_Spinner_update);
        sizeTypeSpinner = findViewById(R.id.product_size_type_Spinner_update);
        sizeSpinner = findViewById(R.id.product_size_Spinner_update);
        progressBar = findViewById(R.id.progress_bar_update);

        uploadPhotoBtn = findViewById(R.id.upload_image_btn_update);
        productImg = findViewById(R.id.product_image_update);
        updateBtn = findViewById(R.id.btn_update);
//        deleteBtn = findViewById(R.id.btn_delete);

        nameEt = findViewById(R.id.product_name_update);
        priceEt = findViewById(R.id.price_edt_update);
        colorEt = findViewById(R.id.color_edt_update);
        stockEt = findViewById(R.id.stock_edt_update);
        descriptionEt = findViewById(R.id.description_edt_update);
        product = new Product();
        crudController = new CrudController(UpdateProductActivity.this);

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
        Spinner spinnerCategory = (Spinner) findViewById(R.id.product_category_Spinner_update);
        Spinner spinnerBrand = (Spinner) findViewById(R.id.product_brand_Spinner_update);
        Spinner spinnerSizeType = (Spinner) findViewById(R.id.product_size_type_Spinner_update);
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

        updateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkData();
            }
        });
    }

    private void checkData() {
        String name = nameEt.getText().toString().trim();
        String price = priceEt.getText().toString().trim();
        String color = colorEt.getText().toString().trim();
        String stock = stockEt.getText().toString().trim();
        String desc = descriptionEt.getText().toString().trim();

        if (TextUtils.isEmpty(name)) {
            nameEt.setError("Nhập tên sản phẩm");
            nameEt.requestFocus();
        } else if (category.equals("")) {
            Toast.makeText(UpdateProductActivity.this, "Chưa chọn thể loại", Toast.LENGTH_SHORT).show();
        } else if (brand.equals("")) {
            Toast.makeText(UpdateProductActivity.this, "Chưa chọn thương hiệu ", Toast.LENGTH_SHORT).show();
        } else if (sizeType.equals("")) {
            Toast.makeText(UpdateProductActivity.this, "Chưa chọn loại size", Toast.LENGTH_SHORT).show();
        } else if (size.equals("")) {
            Toast.makeText(UpdateProductActivity.this, "Chưa chọn size", Toast.LENGTH_SHORT).show();
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

            product.setName(name);
            product.setCategory(category);
            product.setBrand(brand);
            product.setSizeType(sizeType);
            product.setSize(size);
            product.setPrice(Integer.parseInt(price));
            product.setColor(color);
            product.setStock(Integer.parseInt(stock));
            product.setDescription(desc);

            final String docsID = (String) getIntent().getSerializableExtra("productId");
            crudController.UpdateImage(filePath, progressBar, product, docsID);
        }
    }
}