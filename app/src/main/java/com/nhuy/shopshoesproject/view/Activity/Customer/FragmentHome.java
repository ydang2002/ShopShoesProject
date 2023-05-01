package com.nhuy.shopshoesproject.view.Activity.Customer;

import static android.content.ContentValues.TAG;
import static android.widget.Toast.LENGTH_SHORT;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.nhuy.shopshoesproject.controller.Admin.ProductController;
import com.nhuy.shopshoesproject.view.constants.Constants;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;


import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.nhuy.shopshoesproject.R;
import com.nhuy.shopshoesproject.models.BrandModel;
import com.nhuy.shopshoesproject.models.Product;
import com.nhuy.shopshoesproject.view.Adapter.ProductsAdapter;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FragmentHome#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentHome extends Fragment {

    final static int REQUEST_CODE_FILTER = 1;
    private ProductsAdapter mAdapter;
    private RecyclerView recyclerView;
    private ArrayList<Product> productArrayList;

    DatabaseReference myRootRef;
    private ProgressBar progressBar;
    private TextView noJokeText;
    private EditText nameInput;
    private ImageView filer;
    private ProductController productController;
    FragmentActivity c;
    ArrayList<BrandModel> brandArrayList = new ArrayList<>();
//    ArrayList<Category> categoryArrayList = new ArrayList<>();

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public FragmentHome() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FragmentHome.
     */
    // TODO: Rename and change types and number of parameters
    public static FragmentHome newInstance(String param1, String param2) {
        FragmentHome fragment = new FragmentHome();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        productArrayList =new ArrayList<>();
        recyclerView =view.findViewById(R.id.product_list);
        progressBar = view.findViewById(R.id.spin_progress_bar);
        noJokeText = view.findViewById(R.id.no_product);
        nameInput = view.findViewById(R.id.name_input);
        filer = view.findViewById(R.id.filters_btn);
        myRootRef = FirebaseDatabase.getInstance().getReference();

        c = getActivity();
        mAdapter = new ProductsAdapter(productArrayList, c,false);
        recyclerView.setNestedScrollingEnabled(false);
        recyclerView.setLayoutManager(new GridLayoutManager(c.getApplicationContext(), 2));
        recyclerView.setAdapter(mAdapter);
        productController = new ProductController(c);
        progressBar.setVisibility(View.VISIBLE);
        productController.getDataFromFirebase(new ProductController.FirebaseCallback() {
            @Override
            public void onCallback(ArrayList<Product> products) {
                if (products.size()>0) {
                    productArrayList.clear();
                    productArrayList = (ArrayList<Product>) products.clone();
                    setData();
                    progressBar.setVisibility(View.GONE);
                }
                else {
                    noJokeText.setVisibility(View.VISIBLE);
                    progressBar.setVisibility(View.GONE);

                }
            }
        });
        progressBar.setVisibility(View.GONE);

        searchFunc();
        filer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), FilterSearchActivity.class);
                startActivityForResult(intent,REQUEST_CODE_FILTER);
            }
        });
        return view;
    }

    private void searchFunc() {
        nameInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.toString().trim().length() == 0) {
                    if(productArrayList.size()!=0){
                        recyclerView.setVisibility(View.VISIBLE);
                        noJokeText.setVisibility(View.GONE);
                    }
                    else{
                        recyclerView.setVisibility(View.GONE);
                        noJokeText.setVisibility(View.VISIBLE);
                    }

                    mAdapter = new ProductsAdapter(productArrayList,c,false);
                    recyclerView.setAdapter(mAdapter);
                    mAdapter.notifyDataSetChanged();
                } else {
                    ArrayList<Product> clone = new ArrayList<>();
                    for (Product element : productArrayList) {
                        if (element.getName().toLowerCase().contains(s.toString().toLowerCase())) {
                            clone.add(element);
                        }
                    }
                    if(clone.size()!=0){
                        recyclerView.setVisibility(View.VISIBLE);
                        noJokeText.setVisibility(View.GONE);
                    }
                    else{
                        recyclerView.setVisibility(View.GONE);
                        noJokeText.setVisibility(View.VISIBLE);
                    }

                    mAdapter = new ProductsAdapter(clone,c,false);
                    recyclerView.setAdapter(mAdapter);
                    mAdapter.notifyDataSetChanged();
                }
            }
            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }



    private void setData() {
        if(productArrayList.size()>0){
            mAdapter = new ProductsAdapter(productArrayList,c,false);
            recyclerView.setAdapter(mAdapter);
            mAdapter.notifyDataSetChanged();
            recyclerView.setVisibility(View.VISIBLE);
            noJokeText.setVisibility(View.GONE);
        }
        else{
            recyclerView.setVisibility(View.GONE);
            noJokeText.setVisibility(View.VISIBLE);
            mAdapter.notifyDataSetChanged();
        }
    }

//    @Override
//    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        if (requestCode == REQUEST_CODE_FILTER){
//            if (resultCode == getActivity().RESULT_OK){
//                brandArrayList = ( ArrayList<Brand>)data.getSerializableExtra("Brand");
//                categoryArrayList = ( ArrayList<Category>)data.getSerializableExtra("Category");
//
//                if (brandArrayList.size()==0&&categoryArrayList.size()==0) {getDataFromFirebase(); return;}
//                progressBar.setVisibility(View.VISIBLE);
//                final int[] counter = {0};
//                productArrayList.clear();
//                FirebaseFirestore db = FirebaseFirestore.getInstance();
//                CollectionReference reference = db.collection(Constants.PRODUCTS);
//                reference.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
//                    @Override
//                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
//                        if (task.isSuccessful()) {
//                            QuerySnapshot snapshot = task.getResult();
//                            for (QueryDocumentSnapshot document : snapshot) {
//                                Log.d(TAG, document.getId() + " => " + document.getData());
//                                Product product = new Product();
//                                product = document.toObject(Product.class);
//                                if(brandArrayList.size()>0&&categoryArrayList.size()>0) {
//                                    for (int i = 0; i < brandArrayList.size(); i++) {
//                                        if (product.getBrand().equals(brandArrayList.get(i).getBrandName())) {
//                                            for (int j = 0; j < categoryArrayList.size(); j++)
//                                                if (product.getCategory().equals(categoryArrayList.get(j).getCategoryName())) {
//                                                    productArrayList.add(product);
//                                                    counter[0]++;
//                                                }
//                                        }
//                                    }
//                                }
//                                else if (brandArrayList.size()>0){
//                                    for (int i = 0; i < brandArrayList.size(); i++) {
//                                        if (product.getBrand().equals(brandArrayList.get(i).getBrandName())) {
//                                            productArrayList.add(product);
//                                            counter[0]++;
//                                        }
//                                    }
//                                }
//                                else if (categoryArrayList.size()>0){
//                                    for (int i = 0; i < categoryArrayList.size(); i++) {
//                                        if (product.getCategory().equals(categoryArrayList.get(i).getCategoryName())) {
//                                            productArrayList.add(product);
//                                            counter[0]++;
//                                        }
//                                    }
//                                }
//
//
//                                Log.d("ShowEventInfo:", product.toString());
//                            }
//                            if (productArrayList.size()>0){
//                                recyclerView.setVisibility(View.VISIBLE);
//                                noJokeText.setVisibility(View.GONE);
//
//                                mAdapter.notifyDataSetChanged();
//                            }
//                            else{
//                                recyclerView.setVisibility(View.GONE);
//                                noJokeText.setVisibility(View.VISIBLE);
//
//                                mAdapter.notifyDataSetChanged();
//                            }
//
//                        } else {
//                            noJokeText.setVisibility(View.VISIBLE);
//                            progressBar.setVisibility(View.GONE);
//                            Log.d(TAG, "Error getting documents: ", task.getException());
//                            Toast.makeText(c ,"Error" + task.getException() , LENGTH_SHORT).show();
//                        }
//                    }
//                });
//                progressBar.setVisibility(View.GONE);
//            }
//        }
//    }
}