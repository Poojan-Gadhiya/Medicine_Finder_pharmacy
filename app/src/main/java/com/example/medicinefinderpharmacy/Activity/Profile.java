package com.example.medicinefinderpharmacy.Activity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.navigation.ui.AppBarConfiguration;

import com.example.medicinefinderpharmacy.Apis.Api;
import com.example.medicinefinderpharmacy.Apis.ApiServices;
import com.example.medicinefinderpharmacy.Model.Result;
import com.example.medicinefinderpharmacy.Model.SharedPref;
import com.example.medicinefinderpharmacy.R;
import com.example.medicinefinderpharmacy.databinding.ActivityProfileBinding;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Profile extends AppCompatActivity {

    EditText profile_edt_shopname, profile_mobile_no, profile_edt_Drug_license_No, profile_Gst_In_No, profile_Emailid,
            profile_edt_Address, profile_edt_city, profile_edt_State;
    Button btn_save_profile;
    SharedPref sharedPref = new SharedPref();
    private AppBarConfiguration appBarConfiguration;
    private ActivityProfileBinding binding;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityProfileBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setSupportActionBar(binding.toolbar);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        init();
        getprofile();

    }

    void init() {

        profile_edt_shopname = findViewById(R.id.profile_edt_shopname);
        profile_mobile_no = findViewById(R.id.profile_mobile_no);
        profile_edt_Drug_license_No = findViewById(R.id.profile_edt_Drug_license_No);
        profile_Gst_In_No = findViewById(R.id.profile_Gst_In_No);
        profile_Emailid = findViewById(R.id.profile_Emailid);
        profile_edt_Address = findViewById(R.id.profile_edt_Address);
        profile_edt_city = findViewById(R.id.profile_edt_city);
        profile_edt_State = findViewById(R.id.profile_edt_State);
        btn_save_profile=findViewById(R.id.btn_save_profile);

        btn_save_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                edit_pharmacy_profile();
            }
        });

    }

    void getprofile() {
        final ProgressDialog progressDialog = new ProgressDialog(Profile.this);
        progressDialog.setMessage("Please wait...");
        progressDialog.show();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Api.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ApiServices apiServices = retrofit.create(ApiServices.class);

        Call<Result> call = apiServices.get_profile(sharedPref.getId(Profile.this));

        call.enqueue(new Callback<Result>() {
            @Override
            public void onResponse(Call<Result> call, Response<Result> response) {
                progressDialog.dismiss();
                if (response.body() != null) {

                    if (response.body().getSuccess()) {
                        profile_edt_shopname.setText(response.body().getProfile().get(0).getShopname());
                        profile_Emailid.setText(response.body().getProfile().get(0).getEmail_id());
                        profile_Emailid.setEnabled(false);
                        profile_mobile_no.setText(response.body().getProfile().get(0).getMobile_Number());
                        profile_edt_Drug_license_No.setText(response.body().getProfile().get(0).getDrug_license_no());
                        profile_edt_Drug_license_No.setEnabled(false);
                        profile_Gst_In_No.setText(response.body().getProfile().get(0).getGSTIN_No());
                        profile_Gst_In_No.setEnabled(false);
                        profile_edt_Address.setText(response.body().getProfile().get(0).getAddress());
                        profile_edt_city.setText(response.body().getProfile().get(0).getCity());
                        profile_edt_State.setText(response.body().getProfile().get(0).getState());
                    } else {
                        Toast.makeText(Profile.this, response.body().getMsg(), Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(Profile.this, "Somethings are Wrong", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Result> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(Profile.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });


    }

    void edit_pharmacy_profile() {
        final ProgressDialog progressDialog = new ProgressDialog(Profile.this);
        progressDialog.setMessage("Please wait...");
        progressDialog.show();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Api.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        ApiServices apiServices = retrofit.create(ApiServices.class);
        Log.d("REQUEST", "SIGN UP : " + "mobile_no - " + profile_edt_shopname.getText().toString());

        Call<Result> call = apiServices.edit_profile(sharedPref.getId(Profile.this),
                profile_edt_shopname.getText().toString(),
                profile_Emailid.getText().toString(),
                profile_mobile_no.getText().toString(),
                profile_edt_Drug_license_No.getText().toString(),
                profile_Gst_In_No.getText().toString(),
                profile_edt_Address.getText().toString(),
                profile_edt_city.getText().toString(),
                profile_edt_State.getText().toString());

        call.enqueue(new Callback<Result>() {
            @Override
            public void onResponse(Call<Result> call, Response<Result> response) {
                progressDialog.dismiss();
                if (response.body() != null) {
                    Log.d("RESPONSE_MESSAGE", "SIGN UP : " + response.body().getMsg());
                    if (response.body().getSuccess()) {
                        Toast.makeText(Profile.this, response.body().getMsg(), Toast.LENGTH_SHORT).show();
                        finish();
                    } else {
                        Toast.makeText(Profile.this, response.body().getMsg(), Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(Profile.this, "Somethings are Wrong", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Result> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(Profile.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        onBackPressed();
        return true;
    }
}