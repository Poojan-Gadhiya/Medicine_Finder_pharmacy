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
import com.example.medicinefinderpharmacy.R;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class View_med_photo extends AppCompatActivity {

    ImageView img_med_photo;
    TextView txt_No_med_photo;
    String id,med_photo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_med_photo);
        id = getIntent().getExtras().getString("id");
        init();
        viewmedphoto();
    }

    void init()
    {
        img_med_photo=findViewById(R.id.img_med_photo);
        txt_No_med_photo=findViewById(R.id.txt_No_med_photo);
//        Toast.makeText(View_med_photo.this, id , Toast.LENGTH_SHORT).show();
    }

    void viewmedphoto()
    {
        final ProgressDialog progressDialog = new ProgressDialog(View_med_photo.this);
        progressDialog.setMessage("Please wait...");
        progressDialog.show();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Api.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        ApiServices apiServices = retrofit.create(ApiServices.class);

        Call<Result> call = apiServices.View_med_photos(id);

        call.enqueue(new Callback<Result>() {
            @Override
            public void onResponse(Call<Result> call, Response<Result> response) {
                progressDialog.dismiss();
                if (response.body() != null) {

                    if (response.body().getSuccess()) {

                        med_photo=response.body().getMed_Preorder_photo().get(0).getMed_photo();

                        if(med_photo != "") {
                            byte[] decodedString = Base64.decode(med_photo, Base64.DEFAULT);
                            Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
                            img_med_photo.setImageBitmap(decodedByte);
                            img_med_photo.setVisibility(View.VISIBLE);
                        }
                        else
                        {
                            txt_No_med_photo.setVisibility(View.VISIBLE);
                        }

                    } else {
                        Toast.makeText(View_med_photo.this, response.body().getMsg(), Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(View_med_photo.this, "Somethings are Wrong", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Result> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(View_med_photo.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}