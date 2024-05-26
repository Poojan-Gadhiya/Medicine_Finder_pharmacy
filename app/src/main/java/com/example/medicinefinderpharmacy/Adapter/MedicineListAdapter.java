package com.example.medicinefinderpharmacy.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.medicinefinderpharmacy.Activity.mange_stock;
import com.example.medicinefinderpharmacy.Model.Medicine;
import com.example.medicinefinderpharmacy.R;

import java.util.ArrayList;

public class MedicineListAdapter extends RecyclerView.Adapter<MedicineListAdapter.ViewHolder> {
    ArrayList<Medicine> mr_doctor_models;
    Medicine model;
    boolean open_details = false;
    Context context;
    DeleteMedicine deleteMedicine;
    mange_stock mange_stock;
    Updatstock updatstock;

    String id;

    public MedicineListAdapter(ArrayList<Medicine> mr_doctor_models, Context context , mange_stock mange_stock) {
        this.mr_doctor_models = mr_doctor_models;
        this.context = context;
       this.mange_stock=mange_stock;
        this.deleteMedicine=(DeleteMedicine) mange_stock;
        this.updatstock=(Updatstock) mange_stock;


    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem = layoutInflater.inflate(R.layout.item_medicine, parent, false);
        ViewHolder viewHolder = new ViewHolder(listItem);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        model = mr_doctor_models.get(position);
        holder.Medicine_name.setText(model.getMedicine_name());
        holder.txt_med_description.setText(model.getMedicine_description());
        holder.txt_Med_price.setText("price:-"+model.getPrice());
        holder.txt_Med_ml_mg.setText("Ml/Mg:-"+model.getMed_mg_ml());
        holder.txt_Med_stock.setText("stock(Strip):-"+model.getMed_stock());


//        int count_done = Integer.parseInt(model.getTotal_visit()) - Integer.parseInt(model.getRemain_visit());
//        holder.counts.setText(count_done + " / " + model.getTotal_visit() + " Completed");

    }

    @Override
    public int getItemCount() {
        return mr_doctor_models.size();
    }

    public interface DeleteMedicine {
        public void delet_medicine(String id);
    }

    public interface Updatstock{
        public void updte_stock(String id);
    }





    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView Medicine_name, txt_med_description,txt_Med_price,txt_Med_ml_mg,txt_Med_stock, show_details, counts;
        Button btn_update;
        ImageView visit_done, visit_cancel;
        GridLayout gridLayout;
        LinearLayout details, mr_details;
        CardView mr_dr_report_card;
        ImageButton btn_delete_medicine;

        public ViewHolder(View list) {
            super(list);
//            gridLayout = list.findViewById(R.id.staff_rec_design);
            Medicine_name = list.findViewById(R.id.Medicine_name);
            txt_med_description = list.findViewById(R.id.txt_med_description);
            txt_Med_price = list.findViewById(R.id.txt_Med_price);
            txt_Med_ml_mg = list.findViewById(R.id.txt_Med_ml_mg);
            txt_Med_stock = list.findViewById(R.id.txt_Med_stock);


            counts = list.findViewById(R.id.counts);

            visit_done = list.findViewById(R.id.visit_done);
            visit_cancel = list.findViewById(R.id.visit_cancel);
            btn_delete_medicine = list.findViewById(R.id.btn_delete_medicine);
            btn_update = list.findViewById(R.id.btn_update);

            btn_delete_medicine.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    deleteMedicine.delet_medicine(mr_doctor_models.get(getAdapterPosition()).getId());
                }
            });

            btn_update.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    updatstock.updte_stock(mr_doctor_models.get(getAdapterPosition()).getId());
                }
            });




        }
    }


    public void UpdateList(ArrayList<Medicine> list) {
        mr_doctor_models = list;
        notifyDataSetChanged();
    }

}
