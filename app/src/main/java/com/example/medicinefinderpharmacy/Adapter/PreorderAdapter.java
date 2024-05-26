package com.example.medicinefinderpharmacy.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.medicinefinderpharmacy.Activity.Preorder;
import com.example.medicinefinderpharmacy.Model.PreorderModel;
import com.example.medicinefinderpharmacy.R;

import java.util.ArrayList;

public class PreorderAdapter extends RecyclerView.Adapter<PreorderAdapter.ViewHolder> {

    ArrayList<PreorderModel> preorderModels;
    PreorderModel model;
    Context context;
    Preorder preorder;
    PreOrderphoto preOrderphoto;

    public PreorderAdapter(ArrayList<PreorderModel> preorderModels, Context context,Preorder preorder) {
        this.preorderModels = preorderModels;
        this.context = context;
        this.preorder = preorder;
        this.preOrderphoto = (PreOrderphoto) preorder;

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem = layoutInflater.inflate(R.layout.item_preorder, parent, false);
        PreorderAdapter.ViewHolder viewHolder = new PreorderAdapter.ViewHolder(listItem);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull PreorderAdapter.ViewHolder holder, int position) {
        model = preorderModels.get(position);
        holder.Txt_Patient_name.setText("Patient name:-"+model.getUsername());
        holder.Txt_medicine.setText( "Medicine:-"+model.getMedicine_name());
        holder.Txt_med_qty.setText("Qty:-"+model.getMed_qty());

//        holder.img_btn_accept.setOnClickListener;
    }

    @Override
    public int getItemCount() {
        return preorderModels.size();
    }


    public interface PreOrderphoto {
        public void Preorder_datail(String id);
        public void accept_preorder(String id);
        public void reject_preorder(String id);

    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView Txt_medicine, Txt_Patient_name, Txt_med_qty,Txt_medicine_photo;
        ImageView img_btn_accept,img_btn_reject;
        public ViewHolder(View list) {
            super(list);
            Txt_medicine = list.findViewById(R.id.Txt_medicine);
            Txt_Patient_name = list.findViewById(R.id.Txt_Patient_name);
            Txt_med_qty = list.findViewById(R.id.Txt_med_qty);
            Txt_medicine_photo = list.findViewById(R.id.Txt_medicine_photo);

            img_btn_accept = list.findViewById(R.id.img_btn_accept);
            img_btn_reject = list.findViewById(R.id.img_btn_reject);

            Txt_medicine_photo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    preOrderphoto.Preorder_datail(preorderModels.get(getAdapterPosition()).getId());
                }
            });

            img_btn_accept.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    preOrderphoto.accept_preorder(preorderModels.get(getAdapterPosition()).getId());
                    img_btn_accept.setVisibility(View.GONE);
                }
            });

            img_btn_reject.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    preOrderphoto.reject_preorder(preorderModels.get(getAdapterPosition()).getId());
                }
            });

        }

        public void UpdateList(ArrayList<PreorderModel> list) {
            preorderModels = list;
            notifyDataSetChanged();
        }

    }


}
