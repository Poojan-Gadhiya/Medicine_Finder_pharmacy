package com.example.medicinefinderpharmacy.Activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.medicinefinderpharmacy.Apis.Api;
import com.example.medicinefinderpharmacy.Apis.ApiServices;
import com.example.medicinefinderpharmacy.Model.Result;
import com.example.medicinefinderpharmacy.R;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Signup extends AppCompatActivity {

    EditText edt_shopname, mobile_no, edt_Drug_license_No, Gst_In_No, Emailid, password, edt_Address, edt_State;
    Spinner city_spinner;
    Button btn_sign_up;
    TextView btn_back_login;
     String emailPattern;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
        init();
    }

    void init() {

        mobile_no = findViewById(R.id.mobile_no);
        city_spinner = findViewById(R.id.city_spinner);
        edt_shopname = findViewById(R.id.edt_shopname);
        btn_sign_up = findViewById(R.id.btn_sign_up);
        btn_back_login = findViewById(R.id.btn_back_login);
        edt_Drug_license_No = findViewById(R.id.edt_Drug_license_No);
        Gst_In_No = findViewById(R.id.Gst_In_No);
        Emailid = findViewById(R.id.Emailid);
        password = findViewById(R.id.password);
        edt_Address = findViewById(R.id.edt_Address);
        password = findViewById(R.id.password);
        edt_State = findViewById(R.id.edt_State);

        btn_sign_up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validate_data();
            }
        });

        btn_back_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });


    }

    void validate_data() {

        if (edt_shopname.getText().toString().equals("")) {
            edt_shopname.setError("Required");
        } else if (mobile_no.getText().toString().equals("")) {
            mobile_no.setError("Required");
        }else if (mobile_no.getText().toString().length()!=10) {
            mobile_no.setError("Enter valid Mobile no.");
        }else if (edt_Drug_license_No.getText().toString().equals("")){
            edt_Drug_license_No.setError("Required");
        }else if (edt_Drug_license_No.getText().toString().length() != 10) {
            edt_Drug_license_No.setError("Enter Valid drug license no no.");
        } else if (Gst_In_No.getText().toString().equals("")) {
            Gst_In_No.setError("Required");
        }else if (Gst_In_No.getText().toString().length()!=15) {
            Gst_In_No.setError("Enter valid GSTIN no.");
        }else if (Emailid.getText().toString().equals("")) {
            Emailid.setError("Required");
        } else if (!Emailid.getText().toString().matches(emailPattern)) {
            Emailid.setError("Enter a valid email address");
        } else if (password.getText().toString().equals("")) {
            password.setError("Required");
        } else if (edt_Address.getText().toString().equals("")) {
            edt_Address.setError("Required");
        } else if (city_spinner.getSelectedItemPosition() == 0) {
            Toast.makeText(this, "Please Select City", Toast.LENGTH_SHORT).show();
        } else if (edt_State.getText().toString().equals("")) {
            edt_State.setError("Required");
        } else {
            do_signup();
        }
    }

    void do_signup() {
        final ProgressDialog progressDialog = new ProgressDialog(Signup.this);
        progressDialog.setMessage("Please wait...");
        progressDialog.show();

        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Api.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        ApiServices apiServices = retrofit.create(ApiServices.class);

        Log.d("REQUEST", "SIGN UP : " + "edt_shopname - " + edt_shopname.getText().toString());
        Log.d("REQUEST", "SIGN UP : " + "mobile_no - " + mobile_no.getText().toString());
        Log.d("REQUEST", "SIGN UP : " + "edt_Drug_license_No - " + edt_Drug_license_No.getText().toString());
        Log.d("REQUEST", "SIGN UP : " + "Gst_In_No - " + Gst_In_No.getText().toString());
        Log.d("REQUEST", "SIGN UP : " + "Emailid - " + Emailid.getText().toString());
        Log.d("REQUEST", "SIGN UP : " + "password - " + password.getText().toString());
        Log.d("REQUEST", "SIGN UP : " + "edt_Address - " + edt_Address.getText().toString());
        Log.d("REQUEST", "SIGN UP : " + "city_spinner - " + city_spinner.getSelectedItem().toString().trim());
        Log.d("REQUEST", "SIGN UP : " + "edt_State - " + edt_State.getText().toString());

        Call<Result> call = apiServices.pharmacy_reg(edt_shopname.getText().toString(),
                Emailid.getText().toString(),
                password.getText().toString(),
                mobile_no.getText().toString(),
                edt_Drug_license_No.getText().toString(),
                Gst_In_No.getText().toString(),
                edt_Address.getText().toString(),
                city_spinner.getSelectedItem().toString().trim(),
                edt_State.getText().toString());

        call.enqueue(new Callback<Result>() {
            @Override
            public void onResponse(Call<Result> call, Response<Result> response) {
                progressDialog.dismiss();
                if (response.body() != null) {
                    Log.d("RESPONSE_MESSAGE", "SIGN UP : " + response.body().getMsg());
                    if (response.body().getSuccess()) {
                        Toast.makeText(Signup.this, response.body().getMsg(), Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(Signup.this, activity_login.class));
                        finish();
                    } else {
                        Toast.makeText(Signup.this, response.body().getMsg(), Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(Signup.this, "Somethings are Wrong", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Result> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(Signup.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}