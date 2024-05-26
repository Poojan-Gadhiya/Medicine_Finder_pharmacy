package com.example.medicinefinderpharmacy.Apis;


import com.example.medicinefinderpharmacy.Model.Result;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface ApiServices {

    @FormUrlEncoded
    @POST("pharmacy_login.php")
    Call<Result> Login(
            @Field("Email_id") String Email_id,
            @Field("password") String password
    );


    @FormUrlEncoded
    @POST("pharmacy_registration.php")
    Call<Result> pharmacy_reg(
            @Field("Shopname") String Shopname,
            @Field("Email_id") String Email_id,
            @Field("Password") String Password,
            @Field("Mobile_Number") String Mobile_Number,
            @Field("Drug_license_no") String Drug_license_no,
            @Field("GSTIN_No") String GSTIN_No,
            @Field("Address") String Address,
            @Field("City") String City,
            @Field("State") String State
    );

    @FormUrlEncoded
    @POST("medicine_insert.php")
    Call<Result> add_medicine(
            @Field("medicine_name") String medicine_name,
            @Field("medicine_description") String medicine_description,
            @Field("price") String price,
            @Field("med_tabs") String med_tabs,
            @Field("med_mg_ml") String med_mg_ml,
            @Field("med_stock") String med_stock,
            @Field("store_id") String store_id
    );


    @FormUrlEncoded
    @POST("get_medicine_by_pharmacy.php")
    Call<Result> get_medicine(
            @Field("store_id") String store_id
    );

    @FormUrlEncoded
    @POST("Pharmacy_profile.php")
    Call<Result> get_profile(
            @Field("id") String id
    );

    @FormUrlEncoded
    @POST("Edit_Pharmacy_profile.php")
    Call<Result> edit_profile(
            @Field("id") String id,
            @Field("Shopname") String Shopname,
            @Field("Email_id") String Email_id,
            @Field("Mobile_Number") String Mobile_Number,
            @Field("Drug_license_no") String Drug_license_no,
            @Field("GSTIN_No") String GSTIN_No,
            @Field("Address") String Address,
            @Field("City") String City,
            @Field("State") String State
    );

    @FormUrlEncoded
    @POST("get_rating_review_by_pharmacy.php")
    Call<Result> get_rating_review(
            @Field("Chemist_id") String Chemist_id
    );

    @FormUrlEncoded
    @POST("medicine_delete.php")
    Call<Result> medicine_delete(
            @Field("id") String id
    );

    @FormUrlEncoded
    @POST("update_stock.php")
    Call<Result> update_medicine_stock(
            @Field("id") String id,
            @Field("med_stock") String med_stock
    );

    @FormUrlEncoded
    @POST("get_orderid_by_pharmacy.php")
    Call<Result> Order_id_list(
            @Field("store_id") String store_id
    );

    @FormUrlEncoded
    @POST("get_order_by_pharmacy.php")
    Call<Result> Order_Detail_list(
            @Field("store_id") String store_id,
            @Field("Order_id") String Order_id
    );

    @FormUrlEncoded
    @POST("get_preorder_by_pharmacy.php")
    Call<Result> preOrder_Detail(
            @Field("store_id") String store_id
    );

    @FormUrlEncoded
    @POST("chnage_order_statues.php")
    Call<Result> cancel_order(
            @Field("Oeder_status") String store_id,
            @Field("Order_id") String user_id
    );

    @FormUrlEncoded
    @POST("get_prescription_by_pharmacy.php")
    Call<Result> View_prescription(
            @Field("store_id") String store_id,
            @Field("Order_id") String Order_id
    );

    @FormUrlEncoded
    @POST("get_Preorder_medicine_photo.php")
    Call<Result> View_med_photos(
            @Field("id") String id
    );

    @FormUrlEncoded
    @POST("change_preorder_statues.php")
    Call<Result> accept_preorder(
            @Field("id") String id,
            @Field("Order_status") String Order_status
    );

}
