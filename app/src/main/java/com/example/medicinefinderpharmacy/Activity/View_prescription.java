package com.example.medicinefinderpharmacy.Activity;

import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.medicinefinderpharmacy.Apis.Api;
import com.example.medicinefinderpharmacy.Apis.ApiServices;
import com.example.medicinefinderpharmacy.Model.Result;
import com.example.medicinefinderpharmacy.Model.SharedPref;
import com.example.medicinefinderpharmacy.R;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class View_prescription extends AppCompatActivity {

    String orderid;
    String Prescription_photo;
    TextView txt_No_prescription;
    ImageView img_view_prescription;
    SharedPref sharedPref = new SharedPref();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_prescription);

        orderid = getIntent().getExtras().getString("Orderidimage");
        init();
        viewprescription();
    }


    void init(){
        img_view_prescription=findViewById(R.id.img_view_prescription);
        txt_No_prescription=findViewById(R.id.txt_No_prescription);
//        Toast.makeText(View_prescription.this, orderid, Toast.LENGTH_SHORT).show();
    }

    void viewprescription()
    {
        final ProgressDialog progressDialog = new ProgressDialog(View_prescription.this);
        progressDialog.setMessage("Please wait...");
        progressDialog.show();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Api.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        ApiServices apiServices = retrofit.create(ApiServices.class);

        Call<Result> call = apiServices.View_prescription(sharedPref.getId(View_prescription.this),orderid);

        call.enqueue(new Callback<Result>() {
            @Override
            public void onResponse(Call<Result> call, Response<Result> response) {
                progressDialog.dismiss();
                if (response.body() != null) {

                    if (response.body().getSuccess()) {
                        Prescription_photo=response.body().getOrder_Precription().get(0).getPrescription_photo();

                        if(Prescription_photo != null) {
                            byte[] decodedString = Base64.decode(Prescription_photo, Base64.DEFAULT);
                            Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
                            img_view_prescription.setImageBitmap(decodedByte);
                            img_view_prescription.setVisibility(View.VISIBLE);
                        }
                        else
                        {
                            txt_No_prescription.setVisibility(View.VISIBLE);
                        }

                    } else {
                        Toast.makeText(View_prescription.this, response.body().getMsg(), Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(View_prescription.this, "Somethings are Wrong", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Result> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(View_prescription.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}