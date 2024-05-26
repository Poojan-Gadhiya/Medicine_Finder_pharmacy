 package com.example.medicinefinderpharmacy.Activity;

 import android.app.Dialog;
 import android.app.ProgressDialog;
 import android.content.Context;
 import android.content.Intent;
 import android.os.Bundle;
 import android.view.View;
 import android.widget.ArrayAdapter;
 import android.widget.Button;
 import android.widget.EditText;
 import android.widget.ListView;
 import android.widget.TextView;
 import android.widget.Toast;

 import androidx.appcompat.app.AppCompatActivity;

 import com.example.medicinefinderpharmacy.Apis.Api;
 import com.example.medicinefinderpharmacy.Apis.ApiServices;
 import com.example.medicinefinderpharmacy.Model.Result;
 import com.example.medicinefinderpharmacy.Model.SharedPref;
 import com.example.medicinefinderpharmacy.R;

 import java.util.ArrayList;

 import retrofit2.Call;
 import retrofit2.Callback;
 import retrofit2.Response;
 import retrofit2.Retrofit;
 import retrofit2.converter.gson.GsonConverterFactory;

public class activity_login extends AppCompatActivity {

    TextView txt_create_account, txt_forgot_password;
    ListView list_item;
    ArrayList<String> city_name = new ArrayList<>();
    ArrayAdapter adapter;
    EditText edt_email_id, password;
    Button Btn_login;
    String emailPattern;
    Dialog forgot_dialog;

    SharedPref sharedPref = new SharedPref();
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
        init();
        check_login();
    }

    void check_login() {
        if (SharedPref.getLoggedStatus(context)) {
            startActivity(new Intent(context, Homenavigation.class));
            finish();
        }
    }

    void init() {
        context = activity_login.this;
        forgot_dialog = new Dialog(activity_login.this, R.style.MyAlertDialogStyle);
        forgot_dialog.setContentView(R.layout.dialog_forgot_password);


        txt_create_account = findViewById(R.id.txt_create_account);
        txt_forgot_password = findViewById(R.id.txt_forgot_password);
        edt_email_id = findViewById(R.id.edt_email_id);
        password = findViewById(R.id.password);
        Btn_login = findViewById(R.id.Btn_login);


        adapter = new ArrayAdapter(activity_login.this, android.R.layout.simple_list_item_1, city_name);

        txt_create_account.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(activity_login.this, Signup.class));
            }
        });

        txt_forgot_password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                forgot_dialog.show();
            }
        });

        Btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validate_data();
            }
        });


    }

    void validate_data() {
        if (edt_email_id.getText().toString().equals("")) {
            edt_email_id.setError("Required filled");
        }
        else if (!edt_email_id.getText().toString().matches(emailPattern)) {
            edt_email_id.setError("Enter valid email address ");
        }
        else if (password.getText().toString().equals("")) {
            password.setError("Required filled");
        } else {
            do_login();
        }

    }

    void do_login() {

        final ProgressDialog progressDialog = new ProgressDialog(activity_login.this);
        progressDialog.setMessage("Please wait...");
        progressDialog.show();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Api.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ApiServices apiServices = retrofit.create(ApiServices.class);

        Call<Result> call = apiServices.Login(edt_email_id.getText().toString(), password.getText().toString());

        call.enqueue(new Callback<Result>() {
            @Override
            public void onResponse(Call<Result> call, Response<Result> response) {
                progressDialog.dismiss();
                if (response.body() != null) {
                    if (response.body().getSuccess()) {
                        Toast.makeText(activity_login.this, response.body().getMsg(), Toast.LENGTH_SHORT).show();
                        sharedPref.set_profile(context, response.body().getProfile_data().getId(), response.body().getProfile_data().getEmail_id(), response.body().getProfile_data().getShopname(),
                                response.body().getProfile_data().getMobile_Number(), response.body().getProfile_data().getDrug_license_no(), response.body().getProfile_data().getGSTIN_No());
                        SharedPref.setLoggedIn(context, true);
                        startActivity(new Intent(context, Homenavigation.class));
                        finish();
                    } else {
                        Toast.makeText(activity_login.this, response.body().getMsg(), Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(activity_login.this, "Server not responding", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Result> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(activity_login.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}