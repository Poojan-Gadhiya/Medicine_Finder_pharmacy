package com.example.medicinefinderpharmacy.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridLayout;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.medicinefinderpharmacy.Model.RatingModel;
import com.example.medicinefinderpharmacy.R;

import java.util.ArrayList;

public class RatingListAdapter extends RecyclerView.Adapter<RatingListAdapter.ViewHolder> {
    ArrayList<RatingModel> rating_models;
        RatingModel model;
    boolean open_details = false;
    Context context;

    public RatingListAdapter(ArrayList<RatingModel> rating_models, Context context) {
        this.rating_models = rating_models;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem = layoutInflater.inflate(R.layout.item_rating_review, parent, false);
            RatingListAdapter.ViewHolder viewHolder = new RatingListAdapter.ViewHolder(listItem);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RatingListAdapter.ViewHolder holder, int position) {
        model = rating_models.get(position);
        holder.rbStars.setRating(Float.parseFloat(model.getRating_star()));
        holder.txt_ratings.setText("Ratings" + model.getRating_star());
        holder.txt_reviews.setText("Reviews:-" + model.getReview());
//        int count_done = Integer.parseInt(model.getTotal_visit()) - Integer.parseInt(model.getRemain_visit());
//        holder.counts.setText(count_done + " / " + model.getTotal_visit() + " Completed");

    }

    @Override
    public int getItemCount() {
        return rating_models.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        RatingBar rbStars;

        TextView txt_ratings, txt_reviews, counts;
        GridLayout gridLayout;
        LinearLayout details, mr_details;
        CardView mr_dr_report_card;

        public ViewHolder(View list) {
            super(list);
//            gridLayout = list.findViewById(R.id.staff_rec_design);
            rbStars = list.findViewById(R.id.rbStars);
            rbStars.setEnabled(false);
            txt_ratings = list.findViewById(R.id.txt_ratings);
            txt_reviews = list.findViewById(R.id.txt_reviews);
            counts = list.findViewById(R.id.counts);
        }

        public void UpdateList(ArrayList<RatingModel> list) {
            rating_models = list;
            notifyDataSetChanged();
        }
    }
}
