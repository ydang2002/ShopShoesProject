package com.nhuy.shopshoesproject.controller.Customer;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.nhuy.shopshoesproject.models.OrderModel;
import com.nhuy.shopshoesproject.models.Product;
import com.nhuy.shopshoesproject.view.constants.Constants;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;

public class CheckOutController {

    private Context context;

    public CheckOutController(Activity Context) {
        context = Context;
    }

    public void showMessage(String message) {
        Toast.makeText(context.getApplicationContext(), message, Toast.LENGTH_SHORT).show();
    }

    public void updateOrderToFirebase(Context context1, String street, String comments, ProgressDialog dialog, OrderModel order, ArrayList<Product> productArrayList) {
        dialog.show(context1, "Please Wait..", "Submitting order..");

        DatabaseReference root = FirebaseDatabase.getInstance().getReference().child(Constants.ORDER);
        String key = root.push().getKey();
        order.setId(key);
        String currentDateTimeString = DateFormat.getDateTimeInstance().format(new Date());
        order.setDateOfOrder(currentDateTimeString);
        order.setTotalPrice(order.getTotalPrice() + 10);
        order.setAddress(street);
        order.setComments(comments);
        //Y thêm userId
        String currentUserId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        order.setIdUser(currentUserId);

        root.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child(key).setValue(order).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                order.getCartProductList().clear();
                order.setTotalPrice(0);
                productArrayList.clear();
                String currentUserId = FirebaseAuth.getInstance().getCurrentUser().getUid();
                DatabaseReference myRootRef = FirebaseDatabase.getInstance().getReference();
                myRootRef.child(Constants.CART).child(currentUserId).setValue(null);
                dialog.dismiss();
                showMessage("Thanh toán thành công");
            }
        });
    }
}
