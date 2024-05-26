package com.example.medicinefinderpharmacy.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;

import com.example.medicinefinderpharmacy.R;

public class home extends AppCompatActivity {

    RelativeLayout dashboard_manage_stock,dashboard_rating_review;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        init();
    }

    void init()
    {
        dashboard_manage_stock = findViewById(R.id.dashboard_manage_stock);


        //this class is not used instead of use homenavigation class

        dashboard_manage_stock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(home.this,mange_stock.class));
            }
        });





    }
}