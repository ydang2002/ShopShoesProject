package com.nhuy.shopshoesproject.controller.Admin;

import static android.widget.Toast.LENGTH_SHORT;

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
import com.google.firebase.firestore.FirebaseFirestore;
import com.nhuy.shopshoesproject.models.OrderModel;
import com.nhuy.shopshoesproject.models.Product;
import com.nhuy.shopshoesproject.view.Adapter.OrderProductDetailAdapter;
import com.nhuy.shopshoesproject.view.constants.Constants;

import java.util.ArrayList;

public class CustomerOrderDetailController {
    private Context context;
    private FirebaseDatabase database;
    private ArrayList<Product> productArrayList;
    private OrderProductDetailAdapter mAdapter;

    public CustomerOrderDetailController(Activity Context) {
        context = Context;
        database = FirebaseDatabase.getInstance();
        productArrayList = new ArrayList<>();
    }

    public void showMessage(String message) {
        Toast.makeText(context.getApplicationContext(), message, Toast.LENGTH_SHORT).show();
    }

    public void getOrderFromFirebase(String ID, TextView orderID, TextView comment, TextView address, TextView orderPrice, TextView orderQuantity, TextView orderDate, TextView orderstatus, RecyclerView recyclerView, Activity activity, String idCustomer) {

        DatabaseReference databaseReference = database.getReference(Constants.ORDER).child(idCustomer).child(ID);
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

    public void confirmStatus(String idCustomer, String ID) {
        DatabaseReference databaseReferenceBill = database.getReference(Constants.BILL).child(idCustomer).child(ID);
        DatabaseReference databaseReference = database.getReference(Constants.ORDER).child(idCustomer).child(ID);
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                OrderModel order = snapshot.getValue(OrderModel.class);
                for (int i = 0; i < order.getProductArrayList().size(); i++) {
                    order.getProductArrayList().get(i).setSold(order.getProductArrayList().get(i).getSold() + order.getProductArrayList().get(i).getQuantityInCart());
                    order.getProductArrayList().get(i).setStock(order.getProductArrayList().get(i).getStock() - order.getProductArrayList().get(i).getQuantityInCart());

                    FirebaseFirestore db;
                    db = FirebaseFirestore.getInstance();
                    db.collection(Constants.PRODUCTS).document(order.getProductArrayList().get(i).getProductId())
                            .update(
                                    "name", order.getProductArrayList().get(i).getName(),
                                    "category", order.getProductArrayList().get(i).getCategory(),
                                    "brand", order.getProductArrayList().get(i).getBrand(),
                                    "sizeType", order.getProductArrayList().get(i).getSizeType(),
                                    "size", order.getProductArrayList().get(i).getSize(),
                                    "price", order.getProductArrayList().get(i).getPrice(),
                                    "color", order.getProductArrayList().get(i).getColor(),
                                    "stock", order.getProductArrayList().get(i).getStock(),
                                    "sold", order.getProductArrayList().get(i).getSold(),
                                    "description", order.getProductArrayList().get(i).getDescription(),
                                    "photoUrl", order.getProductArrayList().get(i).getPhotoUrl()
                            )
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void unused) {
                                    showMessage("Đang xử lý ...");
                                }
                            });

                }
                order.setStatus("Đơn đã xác nhận");
                databaseReference.setValue(null).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        databaseReferenceBill.setValue(order).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                                showMessage("Xác nhận đơn hàng thành công");
                                ((Activity) context).finish();
                            }
                        });
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        showMessage("Xác nhận đơn hàng không thành công");
                    }
                });

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}
