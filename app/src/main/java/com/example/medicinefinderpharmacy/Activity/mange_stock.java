package com.example.medicinefinderpharmacy.Activity;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.medicinefinderpharmacy.Adapter.MedicineListAdapter;
import com.example.medicinefinderpharmacy.Apis.Api;
import com.example.medicinefinderpharmacy.Apis.ApiServices;
import com.example.medicinefinderpharmacy.Model.Medicine;
import com.example.medicinefinderpharmacy.Model.Result;
import com.example.medicinefinderpharmacy.Model.SharedPref;
import com.example.medicinefinderpharmacy.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class mange_stock extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener, MedicineListAdapter.DeleteMedicine, MedicineListAdapter.Updatstock {


    Dialog dialog_add_medicine;
    Dialog dailog_update_stock;
    FloatingActionButton fab;
    EditText edt_med_description, edt_med_name, edt_price, edt_ml_mg, edt_stock, edt_storeid, edt_tab;
    Button btn_Add_medicine, dailog_btn_update;
    SwipeRefreshLayout mSwipeRefreshLayout;
    SharedPref sharedPref = new SharedPref();
    EditText search_filter, dailog_edt_update_stock;
    ImageButton btn_delete_medicine;

    String Id = "";
    String Update = "";


    ArrayList<Medicine> medicineModels = new ArrayList<>();
    MedicineListAdapter medicineListAdapter;
    RecyclerView recyclerView;

    Toolbar toolbar;
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mange_stock);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        init();
        get_medicine();

    }

    void init() {
        recyclerView = findViewById(R.id.recylerview);
        medicineListAdapter = new MedicineListAdapter(medicineModels, mange_stock.this, mange_stock.this);
        recyclerView.setLayoutManager(new LinearLayoutManager(mange_stock.this));
        recyclerView.setAdapter(medicineListAdapter);
        search_filter = findViewById(R.id.search_filter);
        search_filter.setImeOptions(EditorInfo.IME_ACTION_DONE);
        recyclerView.setVisibility(View.VISIBLE);


        dialog_add_medicine = new Dialog(mange_stock.this, R.style.MyAlertDialogStyle);
        dialog_add_medicine.setContentView(R.layout.dialog_addmedicine);

        edt_med_description = dialog_add_medicine.findViewById(R.id.edt_medicine_description);
        edt_med_name = dialog_add_medicine.findViewById(R.id.edt_med_name);
        edt_price = dialog_add_medicine.findViewById(R.id.edt_price);
        edt_ml_mg = dialog_add_medicine.findViewById(R.id.edt_ml_mg);
        edt_stock = dialog_add_medicine.findViewById(R.id.edt_stock);
        btn_Add_medicine = dialog_add_medicine.findViewById(R.id.btn_Add_medicine);
        edt_tab = dialog_add_medicine.findViewById(R.id.edt_tab);

        btn_delete_medicine = findViewById(R.id.btn_delete_medicine);

        fab = findViewById(R.id.fab);

        dailog_update_stock = new Dialog(mange_stock.this, R.style.MyAlertDialogStyle);
        dailog_update_stock.setContentView(R.layout.dailog_update_stock);

        dailog_edt_update_stock = dailog_update_stock.findViewById(R.id.dailog_edt_update_stock);
        dailog_btn_update = dailog_update_stock.findViewById(R.id.dailog_btn_update);

        dailog_btn_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                update_stock();
            }
        });

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog_add_medicine.show();
            }
        });

        btn_Add_medicine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validate_data();
            }
        });


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

        search_filter.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                filter(s.toString().toUpperCase());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    void filter(String text) {
        ArrayList<Medicine> temp = new ArrayList<>();

        temp.clear();
        for (Medicine data : medicineModels) {
            if (data.getMedicine_name().toUpperCase().contains(text)) {
                temp.add(data);
                medicineListAdapter.UpdateList(temp);
                recyclerView.setVisibility(View.VISIBLE);
            }

        }

    }

    void validate_data() {

        if (edt_med_name.getText().toString().equals("")) {
            edt_med_name.setError("Required");
        } else if (edt_med_description.getText().toString().equals("")) {
            edt_med_description.setError("Required");
        } else if (edt_price.getText().toString().equals("")) {
            edt_price.setError("Required");
        } else if (edt_ml_mg.getText().toString().equals("")) {
            edt_ml_mg.setError("Required");
        } else if (edt_stock.getText().toString().equals("")) {
            edt_stock.setError("Required");
        } else if (edt_tab.getText().toString().equals("")) {
            edt_tab.setError("Required");
        } else {
            add_medicine();
        }
    }

    void add_medicine() {
        final ProgressDialog progressDialog = new ProgressDialog(mange_stock.this);
        progressDialog.setMessage("Please wait...");
        progressDialog.show();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Api.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        ApiServices apiServices = retrofit.create(ApiServices.class);

        Log.d("REQUEST", "SIGN UP : " + "edt_med_name - " + edt_med_name.getText().toString());
        Log.d("REQUEST", "SIGN UP : " + "edt_med_description - " + edt_med_description.getText().toString());
        Log.d("REQUEST", "SIGN UP : " + "edt_price - " + edt_price.getText().toString());
        Log.d("REQUEST", "SIGN UP : " + "edt_ml_mg - " + edt_ml_mg.getText().toString());
        Log.d("REQUEST", "SIGN UP : " + "edt_stock - " + edt_stock.getText().toString());
        Log.d("REQUEST", "SIGN UP : " + "edt_tab - " + edt_tab.getText().toString());
        Log.d("REQUEST", "SIGN UP : " + "edt_storeid - " + sharedPref.getId(mange_stock.this));

        Call<Result> call = apiServices.add_medicine(edt_med_name.getText().toString(),
                edt_med_description.getText().toString(),
                edt_price.getText().toString(),
                edt_tab.getText().toString(),
                edt_ml_mg.getText().toString(),
                edt_stock.getText().toString(),

                sharedPref.getId(mange_stock.this)
        );

        call.enqueue(new Callback<Result>() {

            @Override
            public void onResponse(Call<Result> call, Response<Result> response) {
                progressDialog.dismiss();
                if (response.body() != null) {
                    Log.d("RESPONSE_MESSAGE", "SIGN UP : " + response.body().getMsg());
                    if (response.body().getSuccess()) {
                        Toast.makeText(mange_stock.this, response.body().getMsg(), Toast.LENGTH_SHORT).show();
                        dialog_add_medicine.dismiss();
                        get_medicine();
                    } else {
                        Toast.makeText(mange_stock.this, response.body().getMsg(), Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(mange_stock.this, "Somethings are Wrong", Toast.LENGTH_SHORT).show();
                }
            }


            @Override
            public void onFailure(Call<Result> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(mange_stock.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    void get_medicine() {
        final ProgressDialog progressDialog = new ProgressDialog(mange_stock.this);
        progressDialog.setMessage("Please wait...");
        progressDialog.show();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Api.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        ApiServices apiServices = retrofit.create(ApiServices.class);


        Call<Result> call = apiServices.get_medicine(sharedPref.getId(mange_stock.this));

        call.enqueue(new Callback<Result>() {


            @Override
            public void onResponse(Call<Result> call, Response<Result> response) {
                progressDialog.dismiss();
                if (response.body() != null) {

                    if (response.body().getSuccess()) {
                        medicineModels.clear();
                        medicineModels.addAll(response.body().getMedicine_list());
                        medicineListAdapter.notifyDataSetChanged();
                    } else {
                        Toast.makeText(mange_stock.this, response.body().getMsg(), Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(mange_stock.this, "Somethings are Wrong", Toast.LENGTH_SHORT).show();
                }
            }


            @Override
            public void onFailure(Call<Result> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(mange_stock.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }


    public void delete_medicine(String id) {
        final ProgressDialog progressDialog = new ProgressDialog(mange_stock.this);
        progressDialog.setMessage("Please wait...");
        progressDialog.show();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Api.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        ApiServices apiServices = retrofit.create(ApiServices.class);

        Call<Result> call = apiServices.medicine_delete(id);
        call.enqueue(new Callback<Result>() {


            @Override
            public void onResponse(Call<Result> call, Response<Result> response) {
                progressDialog.dismiss();
                if (response.body() != null) {

                    if (response.body().getSuccess()) {
                        Toast.makeText(mange_stock.this, response.body().getMsg(), Toast.LENGTH_SHORT).show();
                        medicineListAdapter.notifyDataSetChanged();
                        get_medicine();
                    } else {
                        Toast.makeText(mange_stock.this, response.body().getMsg(), Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(mange_stock.this, "Somethings are Wrong", Toast.LENGTH_SHORT).show();
                }
            }


            @Override
            public void onFailure(Call<Result> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(mange_stock.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });


    }

    void update_stock() {
        final ProgressDialog progressDialog = new ProgressDialog(mange_stock.this);
        progressDialog.setMessage("Please wait...");
        progressDialog.show();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Api.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        ApiServices apiServices = retrofit.create(ApiServices.class);

        Call<Result> call = apiServices.update_medicine_stock(Update.toString(), dailog_edt_update_stock.getText().toString());

        call.enqueue(new Callback<Result>() {

            @Override
            public void onResponse(Call<Result> call, Response<Result> response) {
                progressDialog.dismiss();
                if (response.body() != null) {
                    Log.d("RESPONSE_MESSAGE", "SIGN UP : " + response.body().getMsg());
                    if (response.body().getSuccess()) {
                        Toast.makeText(mange_stock.this, response.body().getMsg(), Toast.LENGTH_SHORT).show();
                        dailog_update_stock.dismiss();
                        get_medicine();
                    } else {
                        Toast.makeText(mange_stock.this, response.body().getMsg(), Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(mange_stock.this, "Somethings are Wrong", Toast.LENGTH_SHORT).show();
                }
            }


            @Override
            public void onFailure(Call<Result> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(mange_stock.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onRefresh() {
        onResume();
        get_medicine();
        medicineListAdapter.notifyDataSetChanged();
        mSwipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void delet_medicine(String id) {
        delete_medicine(id);
    }

    @Override
    public void updte_stock(String id) {
        dailog_update_stock.show();
        Update = id;
        Log.d("REQUEST", "SIGN UP : " + Update.toString());
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        onBackPressed();
        return true;
    }

}