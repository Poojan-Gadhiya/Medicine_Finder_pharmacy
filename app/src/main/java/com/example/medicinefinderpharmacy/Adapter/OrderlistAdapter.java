package com.example.medicinefinderpharmacy.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.medicinefinderpharmacy.Model.OrderidlistModel;
import com.example.medicinefinderpharmacy.Activity.Order_list;
import com.example.medicinefinderpharmacy.R;

import java.util.ArrayList;

public class OrderlistAdapter extends RecyclerView.Adapter<OrderlistAdapter.ViewHolder> {

    ArrayList<OrderidlistModel> orderidlistModels;
    OrderidlistModel model;
    Context context;
    Order_list order_list;
    Orderdetail orderdetail;

    public OrderlistAdapter(ArrayList<OrderidlistModel> orderidlistModels, Context context, Order_list order_list) {
        this.orderidlistModels = orderidlistModels;
        this.context = context;
        this.order_list = order_list;
        this.orderdetail = (Orderdetail) order_list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem = layoutInflater.inflate(R.layout.item_orderidlist, parent, false);
        OrderlistAdapter.ViewHolder viewHolder = new OrderlistAdapter.ViewHolder(listItem);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull OrderlistAdapter.ViewHolder holder, int position) {
        model = orderidlistModels.get(position);
        holder.Txt_Orderid.setText(model.getOrder_id());
        holder.Txt_order_price.setText("\u20B9" + model.getOrder_total());
        holder.Txr_orderdate.setText(model.getOrder_date());
        holder.Txt_Patient_name.setText("Patient name:"+model.getUsername());
        holder.Txt_orderstage.setText(model.getOrder_stage());
    }

    @Override
    public int getItemCount() {
        return orderidlistModels.size();
    }

    public interface Orderdetail {
        public void order_datail(String orderid);

    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView Txt_Orderid, Txr_orderdate, Txt_orderstage, Txt_order_price, Txt_order_detail,Txt_Patient_name;
        ImageView img_btn_accept,img_btn_reject;
        public ViewHolder(View list) {
            super(list);
            Txt_Orderid = list.findViewById(R.id.Txt_Orderid);
            Txt_Patient_name = list.findViewById(R.id.Txt_Patient_name);
            Txr_orderdate = list.findViewById(R.id.Txr_orderdate);
            Txt_order_price = list.findViewById(R.id.Txt_order_price);
            Txt_orderstage = list.findViewById(R.id.Txt_orderstage);
            Txt_order_detail = list.findViewById(R.id.Txt_order_detail);
            img_btn_accept = list.findViewById(R.id.img_btn_accept);
            img_btn_reject = list.findViewById(R.id.img_btn_reject);

            Txt_order_detail.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    orderdetail.order_datail(orderidlistModels.get(getAdapterPosition()).getOrder_id());
                }
            });
        }

        public void UpdateList(ArrayList<OrderidlistModel> list) {
            orderidlistModels = list;
            notifyDataSetChanged();
        }

    }
}
