package com.nhuy.shopshoesproject.controller.Customer;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.nhuy.shopshoesproject.models.OrderModel;
import com.nhuy.shopshoesproject.models.Product;
import com.nhuy.shopshoesproject.view.Activity.Customer.OrderDetailActivity;
import com.nhuy.shopshoesproject.view.Adapter.OrderProductDetailAdapter;
import com.nhuy.shopshoesproject.view.constants.Constants;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class OrderDetailController {
    private Context context;
    private FirebaseDatabase database;
    private String currentUserId;
    private ArrayList<Product> productArrayList;
    private OrderProductDetailAdapter mAdapter;

    public OrderDetailController(Activity Context) {
        context = Context;
        database = FirebaseDatabase.getInstance();
        currentUserId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        productArrayList = new ArrayList<>();
    }

    public void showMessage(String message) {
        Toast.makeText(context.getApplicationContext(), message, Toast.LENGTH_SHORT).show();
    }

    public void getOrderFromFirebase(String ID, TextView orderID, TextView comment, TextView address, TextView orderPrice, TextView orderQuantity, TextView orderDate, TextView orderstatus, RecyclerView recyclerView, Activity activity) {

        DatabaseReference databaseReference = database.getReference(Constants.ORDER).child(currentUserId).child(ID);
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    OrderModel order = new OrderModel();
                    order = snapshot.getValue(OrderModel.class);

                    productArrayList = (ArrayList<Product>) order.getProductArrayList().clone();
                    Log.d("productArrayListLOG", String.valueOf(productArrayList));
                    orderID.setText(order.getId());
                    orderstatus.setText(order.getStatus());
                    orderDate.setText(order.getDateOfOrder());
                    orderQuantity.setText(String.valueOf(order.getProductArrayList().size()));
                    orderPrice.setText(String.valueOf((int) order.getTotalPrice()) + " VND");
                    Log.d("Tien", String.valueOf((int) order.getTotalPrice()));
                    address.setText(order.getAddress());
                    comment.setText(order.getComments());

                    DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(activity, DividerItemDecoration.VERTICAL);
                    recyclerView.addItemDecoration(dividerItemDecoration);
                    mAdapter = new OrderProductDetailAdapter(productArrayList, activity);
                    recyclerView.setNestedScrollingEnabled(false);
                    recyclerView.setLayoutManager(new LinearLayoutManager(activity));
                    recyclerView.setAdapter(mAdapter);
                    mAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                showMessage("Lỗi");
            }
        });

    }

    public void cancelOrder(String ID) {
        DatabaseReference databaseReference = database.getReference(Constants.ORDER);
        databaseReference.child(currentUserId).child(ID).setValue(null).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                showMessage("Hủy đơn hàng thành công");
                mAdapter.notifyDataSetChanged();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                showMessage("Hủy đơn không thành công");
            }
        });
    }
}
