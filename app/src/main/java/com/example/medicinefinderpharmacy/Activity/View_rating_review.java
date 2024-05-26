package com.example.medicinefinderpharmacy.Activity;

import android.app.ProgressDialog;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.medicinefinderpharmacy.Adapter.RatingListAdapter;
import com.example.medicinefinderpharmacy.Apis.Api;
import com.example.medicinefinderpharmacy.Apis.ApiServices;
import com.example.medicinefinderpharmacy.Model.RatingModel;
import com.example.medicinefinderpharmacy.Model.Result;
import com.example.medicinefinderpharmacy.Model.SharedPref;
import com.example.medicinefinderpharmacy.R;
import com.example.medicinefinderpharmacy.databinding.ActivityViewRatingReviewBinding;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class View_rating_review extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener{

    private ActivityViewRatingReviewBinding binding;

    ArrayList<RatingModel> ratingModels = new ArrayList<>();
    RatingListAdapter ratingListAdapter;
    RecyclerView recyclerView;
    SharedPref sharedPref = new SharedPref();
    SwipeRefreshLayout mSwipeRefreshLayout;
    EditText search_filter;
    Toolbar toolbar;
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityViewRatingReviewBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setSupportActionBar(binding.toolbar);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        init();
        get_rating_review();
    }
    void init() {
        recyclerView = findViewById(R.id.recylerview);
        ratingListAdapter = new RatingListAdapter(ratingModels, View_rating_review.this);
        recyclerView.setLayoutManager(new LinearLayoutManager(View_rating_review.this));
        recyclerView.setAdapter(ratingListAdapter);
        search_filter = findViewById(R.id.search_filter);
        search_filter.setImeOptions(EditorInfo.IME_ACTION_DONE);
        recyclerView.setVisibility(View.VISIBLE);

        mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipeToRefresh);

        mSwipeRefreshLayout.setColorSchemeResources(R.color.green);

        mSwipeRefreshLayout.setOnRefreshListener(this);

        mSwipeRefreshLayout.post(new Runnable() {
                                     @Override
                                     public void run() {
                                         mSwipeRefreshLayout.setRefreshing(false);
                                         //Report_list.clear();
                                         // get_reports();
                                     }
                                 }
        );

    }
    void get_rating_review() {
        final ProgressDialog progressDialog = new ProgressDialog(View_rating_review.this);
        progressDialog.setMessage("Please wait...");
        progressDialog.show();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Api.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        ApiServices apiServices = retrofit.create(ApiServices.class);

        Call<Result> call = apiServices.get_rating_review(sharedPref.getId(View_rating_review.this));

        call.enqueue(new Callback<Result>() {

            @Override
            public void onResponse(Call<Result> call, Response<Result> response) {
                progressDialog.dismiss();
                if (response.body() != null) {

                    if (response.body().getSuccess()) {
                        ratingModels.clear();
                        ratingModels.addAll(response.body().getRating_review());
                        ratingListAdapter.notifyDataSetChanged();
                    } else {
                        Toast.makeText(View_rating_review.this, response.body().getMsg(), Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(View_rating_review.this, "Somethings are Wrong", Toast.LENGTH_SHORT).show();
                }
            }


            @Override
            public void onFailure(Call<Result> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(View_rating_review.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onRefresh() {
        onResume();
        get_rating_review();
        mSwipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        onBackPressed();
        return true;
    }
}