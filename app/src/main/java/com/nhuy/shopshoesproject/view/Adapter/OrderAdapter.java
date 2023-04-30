package com.nhuy.shopshoesproject.view.Adapter;


import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;


import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.nhuy.shopshoesproject.R;
import com.nhuy.shopshoesproject.models.OrderModel;
import com.nhuy.shopshoesproject.view.Activity.Admin.Order.CustomerOrderDetailActivity;
import com.nhuy.shopshoesproject.view.Activity.Customer.BillDetailActivity;
import com.nhuy.shopshoesproject.view.Activity.Customer.OrderDetailActivity;

import java.util.List;

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.MyViewHolder> {
    List<OrderModel> orderList;
    Activity context;
    boolean isAdmin;

    public OrderAdapter(List<OrderModel> orderList, Activity context, boolean isAdmin) {
        this.orderList = orderList;
        this.context = context;
        this.isAdmin = isAdmin;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.single_order_item, parent, false);
        return new OrderAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        OrderModel order = orderList.get(position);
//        if(order.getProductArrayList().get(0).getPhotoUrl()!=null){
//            if(!order.getProductArrayList().get(0).getPhotoUrl().equals("")){
//                holder.orderImg.setVisibility(View.VISIBLE);
//                Picasso.get().load(order.getProductArrayList().get(0).getPhotoUrl()).placeholder(R.drawable.no_background_icon).into(holder.orderImg);
//            }
//        }
        holder.quality.setText("Số lượng sản phẩm "+order.getProductArrayList().size());
        if (order.getStatus().equals("Đơn đã xác nhận")) holder.name.setTextColor(Color.GREEN);
        holder.name.setText(order.getStatus());
        holder.date.setText(order.getDateOfOrder());
        holder.userId.setText("Mã Khách hàng "+order.getIdUser());
        holder.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isAdmin) {
                    if (order.getStatus().equals("Đơn đã xác nhận")){
                        Intent intent = new Intent(context, BillDetailActivity.class);
                        intent.putExtra("orderID", order.getId());
                        intent.putExtra("order",order);
                        intent.putExtra("isAdmin",true);
                        intent.putExtra("idCustomer", order.getIdUser());
                        context.startActivity(intent);
                    }
                    else {
                        Intent intent = new Intent(context, CustomerOrderDetailActivity.class);
                        intent.putExtra("id", order.getId());
//                    Log.d("showId", order.getId());
                        intent.putExtra("order", order);
                        intent.putExtra("idCustomer", order.getIdUser());
                        context.startActivity(intent);
                    }
                }
                else {
                    Intent intent;
                    if (order.getStatus().equals("Đơn đã xác nhận")) {
                        intent = new Intent(context, BillDetailActivity.class);
                        intent.putExtra("isAdmin",false);
                    }
                    else {
                        intent = new Intent(context, OrderDetailActivity.class);
                    }
                    intent.putExtra("orderID", order.getId());
                    intent.putExtra("order",order);
                    context.startActivity(intent);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return orderList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        LinearLayout layout;
        ImageView orderImg;
        TextView name,price,date,quality, userId;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            layout = itemView.findViewById(R.id.order_layout);
            orderImg = itemView.findViewById(R.id.order_img);
            name = itemView.findViewById(R.id.order_name);
            userId = itemView.findViewById(R.id.order_idCutomer);
            date = itemView.findViewById(R.id.order_date);
            quality = itemView.findViewById(R.id.order_quantity);

        }
    }
}
