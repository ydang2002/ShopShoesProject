package com.nhuy.shopshoesproject.view.Activity.Customer;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;


import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.nhuy.shopshoesproject.R;
import com.nhuy.shopshoesproject.models.OrderModel;
import com.nhuy.shopshoesproject.view.Adapter.OrderAdapter;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FragmentOrder#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentOrder extends Fragment  {

    private OrderAdapter mAdapter;
    private RecyclerView recyclerView;
    private ArrayList<OrderModel> orderArrayList;
    private TextView noJokeText;
    DatabaseReference myRootRef;
    private ProgressBar progressBar;
    FragmentActivity c;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public FragmentOrder() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FragmentOrder.
     */
    // TODO: Rename and change types and number of parameters
    public static FragmentOrder newInstance(String param1, String param2) {
        FragmentOrder fragment = new FragmentOrder();
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
        View view = inflater.inflate(R.layout.fragment_order, container, false);;
        orderArrayList =new ArrayList<OrderModel>();
        recyclerView =view.findViewById(R.id.order_list);
        progressBar = view.findViewById(R.id.spin_progress_bar_order);
        noJokeText = view.findViewById(R.id.no_order);
        myRootRef = FirebaseDatabase.getInstance().getReference();
        c = getActivity();
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(c,DividerItemDecoration.VERTICAL);
        recyclerView.addItemDecoration(dividerItemDecoration);
        mAdapter = new OrderAdapter(orderArrayList,c, false);
        recyclerView.setLayoutManager(new LinearLayoutManager(c.getApplicationContext()));
        recyclerView.setNestedScrollingEnabled(false);
        recyclerView.setAdapter(mAdapter);
        mAdapter.notifyDataSetChanged();
        getDataFromFirebase();
        //Toast.makeText(getActivity(),orderArrayList.size()+" size", Toast.LENGTH_LONG).show();
        return view;
    }
    public void getDataFromFirebase() {

        progressBar.setVisibility(View.VISIBLE);
        String currentUserId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = database.getReference("Order").child(currentUserId);
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                orderArrayList.clear();
                for (DataSnapshot post : snapshot.getChildren()){
                    OrderModel order = post.getValue(OrderModel.class);
                    orderArrayList.add(order);
                }
                mAdapter.notifyDataSetChanged();
                setData();
                progressBar.setVisibility(View.GONE);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                noJokeText.setVisibility(View.VISIBLE);
                progressBar.setVisibility(View.GONE);
                Toast.makeText(c,"Error", Toast.LENGTH_LONG).show();

            }
        });
    }

    private void setData() {
        if(orderArrayList.size()>0){
            recyclerView.setVisibility(View.VISIBLE);
            noJokeText.setVisibility(View.GONE);
        }
        else{
            recyclerView.setVisibility(View.GONE);
            noJokeText.setVisibility(View.VISIBLE);
            mAdapter.notifyDataSetChanged();
        }
    }

}