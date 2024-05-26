package com.example.medicinefinderpharmacy.Activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.medicinefinderpharmacy.Adapter.OrderdetailAdapter;
import com.example.medicinefinderpharmacy.Apis.Api;
import com.example.medicinefinderpharmacy.Apis.ApiServices;
import com.example.medicinefinderpharmacy.Model.OrderdetailModel;
import com.example.medicinefinderpharmacy.Model.Result;
import com.example.medicinefinderpharmacy.Model.SharedPref;
import com.example.medicinefinderpharmacy.R;
import com.example.medicinefinderpharmacy.databinding.ActivityOrderDetailBinding;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Order_detail extends AppCompatActivity {

    ArrayList<OrderdetailModel> orderdetailModels = new ArrayList<>();
    OrderdetailAdapter orderdetailAdapter;
    RecyclerView recyclerView;
    SharedPref sharedPref = new SharedPref();
    SwipeRefreshLayout mSwipeRefreshLayout;

    TextView  total_mrp, Txt_Order_adress, Txt_Order_shopname, total_Order_quantity, total_order_mrp;
    TextView Txt_Orderid, Txt_payment_type, Txt_ispaid, Txt_orderid,Txt_orderdate,Txt_prescription_check;

    String orderid;
    String total_qty = "";
    String is_prescription_available;
    final int cancel_order_no=6;
    Button btn_cancel_order,btn_radyto_pickup,btn_view_prescription;
    final int ready_pickup=2;

    Toolbar toolbar;
    private AppBarConfiguration appBarConfiguration;
    private ActivityOrderDetailBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityOrderDetailBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setSupportActionBar(binding.toolbar);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        orderid = getIntent().getExtras().getString("Orderid");

        init();
        view_cart();

    }

    void init() {
        recyclerView = findViewById(R.id.recylerview);
        orderdetailAdapter = new OrderdetailAdapter(orderdetailModels, Order_detail.this, Order_detail.this);
        recyclerView.setLayoutManager(new LinearLayoutManager(Order_detail.this));
        recyclerView.setAdapter(orderdetailAdapter);


        Txt_Order_adress = findViewById(R.id.Txt_Order_adress);
        Txt_Order_shopname = findViewById(R.id.Txt_Order_shopname);
        total_Order_quantity = findViewById(R.id.total_Order_quantity);
        total_order_mrp = findViewById(R.id.total_order_mrp);
        total_mrp = findViewById(R.id.total_mrp);
        Txt_Orderid = findViewById(R.id.Txt_Orderid);
        Txt_payment_type = findViewById(R.id.Txt_payment_type);
        Txt_ispaid = findViewById(R.id.Txt_ispaid);
        Txt_orderid = findViewById(R.id.Txt_orderid);
        Txt_orderdate = findViewById(R.id.Txt_orderdate);
        btn_cancel_order = findViewById(R.id.btn_cancel_order);
        btn_radyto_pickup = findViewById(R.id.btn_radyto_pickup);
        btn_view_prescription = findViewById(R.id.btn_view_prescription);
        Txt_prescription_check = findViewById(R.id.Txt_prescription_check);


        btn_view_prescription.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(is_prescription_available != null)
                {
                    Intent intent = new Intent(Order_detail.this, View_prescription.class);
                    intent.putExtra("Orderidimage", orderid);
                    startActivity(intent);
                }
                else {
                    Txt_prescription_check.setVisibility(View.VISIBLE);
                }
            }
        });


        btn_cancel_order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Cnacel_order_pharmacy();
            }
        });

        btn_radyto_pickup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ready_to_pickup();
            }
        });



    }

    void view_cart() {
        final ProgressDialog progressDialog = new ProgressDialog(Order_detail.this);
        progressDialog.setMessage("Please wait...");
        progressDialog.show();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Api.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        ApiServices apiServices = retrofit.create(ApiServices.class);

        Call<Result> call = apiServices.Order_Detail_list(sharedPref.getId(Order_detail.this), orderid);

        call.enqueue(new Callback<Result>() {
            @Override
            public void onResponse(Call<Result> call, Response<Result> response) {
                progressDialog.dismiss();
                orderdetailModels.clear();
                if (response.body() != null) {
//                    Log.d("ORDER_DATA", response.body().getOrder_Detail_list().get(0).getOrder_id());
                    if (response.body().getSuccess()) {
                        
                        orderdetailModels.addAll(response.body().getOrder_Detail_list());
                        orderdetailAdapter.notifyDataSetChanged();
                        Txt_Orderid.setText(response.body().getOrder_Detail_list().get(0).getOrder_id());
                        Txt_orderdate.setText(response.body().getOrder_Detail_list().get(0).getOrder_date());
//                        Txt_Order_adress.setText("Pickup Address:-" + response.body().getOrder_Detail_list().get(0).getAddress());
                        Txt_payment_type.setText(response.body().getOrder_Detail_list().get(0).getPayment_type());
                        Txt_ispaid.setText(response.body().getOrder_Detail_list().get(0).getIs_Paid());
                        Txt_orderid.setText(response.body().getOrder_Detail_list().get(0).getOrder_id());
                        total_order_mrp.setText(response.body().getOrder_Detail_list().get(0).getOrder_total());
                        is_prescription_available=response.body().getOrder_Detail_list().get(0).getPrescription_photo();
                        for (int i = 0; i < orderdetailAdapter.getItemCount(); i++) {
                            total_qty = String.valueOf(orderdetailAdapter.getItemCount());
                        }
                        total_Order_quantity.setText(total_qty);
                    }
                } else {
                    Toast.makeText(Order_detail.this, "Somethings are Wrong", Toast.LENGTH_SHORT).show();
                }
                orderdetailAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<Result> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(Order_detail.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    void Cnacel_order_pharmacy()
    {
        final ProgressDialog progressDialog = new ProgressDialog(Order_detail.this);
        progressDialog.setMessage("Please wait...");
        progressDialog.show();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Api.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        ApiServices apiServices = retrofit.create(ApiServices.class);

        Call<Result> call = apiServices.cancel_order(String.valueOf(cancel_order_no),orderid);

        call.enqueue(new Callback<Result>() {
            @Override
            public void onResponse(Call<Result> call, Response<Result> response) {
                progressDialog.dismiss();
                if (response.body() != null) {
                    if (response.body().getSuccess()) {
                        Toast.makeText(Order_detail.this, response.body().getMsg(), Toast.LENGTH_SHORT).show();
                        finish();
                    } else {
                        Toast.makeText(Order_detail.this, response.body().getMsg(), Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(Order_detail.this, "Somethings are Wrong", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Result> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(Order_detail.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                startActivity(new Intent(Order_detail.this, Homenavigation.class));
            }
        });
    }

    void ready_to_pickup()
    {
        final ProgressDialog progressDialog = new ProgressDialog(Order_detail.this);
        progressDialog.setMessage("Please wait...");
        progressDialog.show();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Api.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        ApiServices apiServices = retrofit.create(ApiServices.class);

        Call<Result> call = apiServices.cancel_order(String.valueOf(ready_pickup),orderid);

        call.enqueue(new Callback<Result>() {

            @Override
            public void onResponse(Call<Result> call, Response<Result> response) {
                progressDialog.dismiss();
                if (response.body() != null) {
                    if (response.body().getSuccess()) {
                        Toast.makeText(Order_detail.this, "Message sent to patient ", Toast.LENGTH_SHORT).show();
                        finish();
                    } else {
                        Toast.makeText(Order_detail.this, response.body().getMsg(), Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(Order_detail.this, "Somethings are Wrong", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Result> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(Order_detail.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                startActivity(new Intent(Order_detail.this, Homenavigation.class));
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        onBackPressed();
        return true;
    }
}