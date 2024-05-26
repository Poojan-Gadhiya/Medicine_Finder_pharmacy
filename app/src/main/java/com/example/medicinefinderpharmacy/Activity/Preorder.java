package com.example.medicinefinderpharmacy.Activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.medicinefinderpharmacy.Adapter.PreorderAdapter;
import com.example.medicinefinderpharmacy.Apis.Api;
import com.example.medicinefinderpharmacy.Apis.ApiServices;
import com.example.medicinefinderpharmacy.Model.PreorderModel;
import com.example.medicinefinderpharmacy.Model.Result;
import com.example.medicinefinderpharmacy.Model.SharedPref;
import com.example.medicinefinderpharmacy.R;
import com.example.medicinefinderpharmacy.databinding.ActivityPreorderBinding;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Preorder extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener,PreorderAdapter.PreOrderphoto{

    ArrayList<PreorderModel> preorderModels = new ArrayList<>();
    PreorderAdapter preorderAdapter;
    RecyclerView recyclerView;
    SwipeRefreshLayout mSwipeRefreshLayout;
    SharedPref sharedPref = new SharedPref();

    private AppBarConfiguration appBarConfiguration;
    private ActivityPreorderBinding binding;
    ImageView img_btn_accept,img_btn_reject;
    Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityPreorderBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setSupportActionBar(binding.toolbar);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        init();
        Preorderid_list();
    }

    void init() {
        recyclerView = findViewById(R.id.recylerview);
        preorderAdapter = new PreorderAdapter(preorderModels, Preorder.this,Preorder.this);
        recyclerView.setLayoutManager(new LinearLayoutManager(Preorder.this));
        recyclerView.setAdapter(preorderAdapter);
        recyclerView.setVisibility(View.VISIBLE);
        recyclerView.setVisibility(View.VISIBLE);

        img_btn_accept = findViewById(R.id.img_btn_accept);
        img_btn_reject = findViewById(R.id.img_btn_reject);


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

    void Preorderid_list() {
        final ProgressDialog progressDialog = new ProgressDialog(Preorder.this);
        progressDialog.setMessage("Please wait...");
        progressDialog.show();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Api.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        ApiServices apiServices = retrofit.create(ApiServices.class);

        Call<Result> call = apiServices.preOrder_Detail(sharedPref.getId(Preorder.this));
        Log.d("USER_ID",sharedPref.getId(Preorder.this));
        call.enqueue(new Callback<Result>() {
            @Override
            public void onResponse(Call<Result> call, Response<Result> response) {
                progressDialog.dismiss();
                if (response.body() != null) {

                    if (response.body().getSuccess()) {
                        preorderModels.clear();
                        preorderModels.addAll(response.body().getPreOrder_Detail_list());
                        preorderAdapter.notifyDataSetChanged();

                    }
                    else
                    {
                        Toast.makeText(Preorder.this, "Data not found", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(Preorder.this, "Somethings are Wrong", Toast.LENGTH_SHORT).show();
                }
                preorderAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<Result> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(Preorder.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    public void onRefresh() {
        onResume();
        Preorderid_list();
        mSwipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        onBackPressed();
        return true;
    }

    @Override
    public void Preorder_datail(String id) {
        Intent intent = new Intent(Preorder.this, View_med_photo.class);
        intent.putExtra("id", id);
        startActivity(intent);
    }

    @Override
    public void accept_preorder(String id) {
        final ProgressDialog progressDialog = new ProgressDialog(Preorder.this);
        progressDialog.setMessage("Please wait...");
        progressDialog.show();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Api.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        ApiServices apiServices = retrofit.create(ApiServices.class);

        Call<Result> call = apiServices.accept_preorder(id, String.valueOf(7));

        call.enqueue(new Callback<Result>() {

            @Override
            public void onResponse(Call<Result> call, Response<Result> response) {
                progressDialog.dismiss();
                if (response.body() != null) {
                    if (response.body().getSuccess()) {
                        Toast.makeText(Preorder.this, "Preorder accepted ", Toast.LENGTH_SHORT).show();
                        finish();
                    } else {
                        Toast.makeText(Preorder.this, response.body().getMsg(), Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(Preorder.this, "Somethings are Wrong", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Result> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(Preorder.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                startActivity(new Intent(Preorder.this, Homenavigation.class));
            }
        });
    }

    @Override
    public void reject_preorder(String id) {
        final ProgressDialog progressDialog = new ProgressDialog(Preorder.this);
        progressDialog.setMessage("Please wait...");
        progressDialog.show();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Api.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        ApiServices apiServices = retrofit.create(ApiServices.class);

        Call<Result> call = apiServices.accept_preorder(id, String.valueOf(4));

        call.enqueue(new Callback<Result>() {

            @Override
            public void onResponse(Call<Result> call, Response<Result> response) {
                progressDialog.dismiss();
                if (response.body() != null) {
                    if (response.body().getSuccess()) {
                        Toast.makeText(Preorder.this, "Preorder Rejected ", Toast.LENGTH_SHORT).show();
                        finish();
                    } else {
                        Toast.makeText(Preorder.this, response.body().getMsg(), Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(Preorder.this, "Somethings are Wrong", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Result> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(Preorder.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                startActivity(new Intent(Preorder.this, Homenavigation.class));
            }
        });
    }
}