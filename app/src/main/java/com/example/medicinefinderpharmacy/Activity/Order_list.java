package com.example.medicinefinderpharmacy.Activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.medicinefinderpharmacy.Adapter.OrderlistAdapter;
import com.example.medicinefinderpharmacy.Apis.Api;
import com.example.medicinefinderpharmacy.Apis.ApiServices;
import com.example.medicinefinderpharmacy.Model.OrderidlistModel;
import com.example.medicinefinderpharmacy.Model.Result;
import com.example.medicinefinderpharmacy.Model.SharedPref;
import com.example.medicinefinderpharmacy.R;
import com.example.medicinefinderpharmacy.databinding.ActivityOrderListBinding;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Order_list extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener, OrderlistAdapter.Orderdetail {


    ArrayList<OrderidlistModel> orderidlistModels = new ArrayList<>();
    OrderlistAdapter orderlistAdapter;
    RecyclerView recyclerView;
    SwipeRefreshLayout mSwipeRefreshLayout;
    SharedPref sharedPref = new SharedPref();

    private AppBarConfiguration appBarConfiguration;
    private ActivityOrderListBinding binding;
    Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityOrderListBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setSupportActionBar(binding.toolbar);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        init();
        orderidlist();
    }


    void init() {
        recyclerView = findViewById(R.id.recylerview);
        orderlistAdapter = new OrderlistAdapter(orderidlistModels, Order_list.this, Order_list.this);
        recyclerView.setLayoutManager(new LinearLayoutManager(Order_list.this));
            recyclerView.setAdapter(orderlistAdapter);
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

    void orderidlist() {
        final ProgressDialog progressDialog = new ProgressDialog(Order_list.this);
        progressDialog.setMessage("Please wait...");
        progressDialog.show();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Api.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        ApiServices apiServices = retrofit.create(ApiServices.class);

        Call<Result> call = apiServices.Order_id_list(sharedPref.getId(Order_list.this));

        call.enqueue(new Callback<Result>() {
            @Override
            public void onResponse(Call<Result> call, Response<Result> response) {
                progressDialog.dismiss();
                orderidlistModels.clear();
                if (response.body() != null) {

                    if (response.body().getSuccess()) {

                        orderidlistModels.addAll(response.body().getOrderid_list());
//                        Txt_Orderid.setText(response.body().getOrderid_list().get(0).getOrder_id());
//                        Txr_orderdate.setText(response.body().getOrderid_list().get(0).getOrder_date());
//                        Txt_order_price.setText(response.body().getOrderid_list().get(0).getOrder_total());
//                        Txt_orderstage.setText(response.body().getOrderid_list().get(0).getOrder_stage());
                    }
                } else {
                    Toast.makeText(Order_list.this, "Somethings are Wrong", Toast.LENGTH_SHORT).show();
                }
                orderlistAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<Result> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(Order_list.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });



    }

    public void onRefresh() {
        onResume();
        orderidlist();
        mSwipeRefreshLayout.setRefreshing(false);
    }


    @Override
    public void order_datail(String orderid) {
        Log.d("order_id", "" + orderid);
        Intent intent = new Intent(Order_list.this, Order_detail.class);
        intent.putExtra("Orderid", orderid);
        Log.d("order_id", "" + orderid);
        startActivity(intent);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        onBackPressed();
        return true;
    }




}