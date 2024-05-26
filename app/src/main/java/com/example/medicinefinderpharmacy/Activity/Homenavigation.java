package com.example.medicinefinderpharmacy.Activity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.viewpager.widget.ViewPager;


import com.example.medicinefinderpharmacy.Model.ProfileModel;
import com.example.medicinefinderpharmacy.Model.SharedPref;
import com.example.medicinefinderpharmacy.R;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;

public class Homenavigation extends AppCompatActivity {

    LinearLayout home_task_layout, home_attandence_layout, nav_attandence, nav_task, nav_expense, nav_chemist, nav_doctor,
            nav_analysis, nav_reports,rating;
    ImageView home_arow;

    String TAG = "Homenavigation..";
    SharedPref sharedPref = new SharedPref();
    TextView mr_name, mr_number;

    TabLayout tabLayout;
    ViewPager viewPager;
    DrawerLayout drawer;
    LinearLayout logout_button,nav_profile;
    LinearLayout profile;
    ArrayList<ProfileModel> profile_data = new ArrayList<>();
    TextView status;

    Dialog adv_dialog;

    ImageView adv_image;
    TextView adv_message;

    RelativeLayout dashboard_manage_stock,dashboard_rating_review,dashboard_Order,dashboard_preorder;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homenavigation);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            getWindow().setStatusBarColor(getResources().getColor(R.color.btn_color, this.getTheme()));
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(getResources().getColor(R.color.btn_color));
        }
        init();
        onclick();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }


    public void init() {

        dashboard_manage_stock = findViewById(R.id.dashboard_manage_stock);


        dashboard_manage_stock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Homenavigation.this,mange_stock.class));
            }
        });

        dashboard_rating_review=findViewById(R.id.dashboard_rating_review);
        dashboard_rating_review.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Homenavigation.this,View_rating_review.class));
            }
        });

        dashboard_Order=findViewById(R.id.dashboard_Order);
        dashboard_Order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Homenavigation.this, Order_list.class));
            }
        });

        dashboard_preorder=findViewById(R.id.dashboard_preorder);
        dashboard_preorder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Homenavigation.this, Preorder.class));
            }
        });


//        adv_dialog = new Dialog(Homenavigation.this, R.style.MyAlertDialogStyle);
//        adv_dialog.setContentView(R.layout.dialog_adverticement);
//
//        adv_image = adv_dialog.findViewById(R.id.adv_image);
//        adv_message = adv_dialog.findViewById(R.id.adv_text);

        status = findViewById(R.id.status);

        mr_name = findViewById(R.id.mr_name);
        mr_number = findViewById(R.id.mr_number);

        mr_name.setText(sharedPref.getname(Homenavigation.this));
        mr_number.setText(sharedPref.getnumber(Homenavigation.this));

        home_task_layout = findViewById(R.id.home_task_layout);
//        home_attandence_layout = findViewById(R.id.home_attandence_layout);
//        home_arow = findViewById(R.id.home_arrow);

//        nav_attandence = findViewById(R.id.nav_attandence);
//        nav_task = findViewById(R.id.nav_task);
//        nav_expense = findViewById(R.id.nav_expense);
//        nav_chemist = findViewById(R.id.nav_chemist);
//        nav_doctor = findViewById(R.id.nav_doctor);
//        nav_analysis = findViewById(R.id.nav_analysis);
//        nav_reports = findViewById(R.id.nav_reports);



        tabLayout = findViewById(R.id.tabLayout);
        viewPager = findViewById(R.id.viewPager);


        logout_button = findViewById(R.id.logout_button);
        logout_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(Homenavigation.this,R.style.MyAlertDialogStyle)
                        .setMessage("Do you want to logout?") .setTitle("Logout") .setIcon(R.drawable.logo_1)

                        // Specifying a listener allows you to take an action before dismissing the dialog.
                        // The dialog is automatically dismissed when a dialog button is clicked.
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                SharedPref.setLoggedIn(Homenavigation.this, false);
                                sharedPref.clear_data(Homenavigation.this);
                                startActivity(new Intent(Homenavigation.this, activity_login.class));
                                finish();
                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                //  Action for 'NO' Button
                                dialog.dismiss();
                            }
                        })

                        .show();
            }
        });

        nav_profile=findViewById(R.id.nav_profile);
        nav_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i =new Intent(Homenavigation.this,Profile.class);
                startActivity(i);
            }
        });

    }

    public void onclick() {


    }

    public void closeDrawer() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
//        get_profile();
    }
}
