package com.example.medicinefinderpharmacy.Model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class Result {


    @SerializedName("success")
    private Boolean success;

    @SerializedName("msg")
    private String msg;


    @SerializedName("profile_data")
    private ProfileModel profile_data;


    @SerializedName("medicine_list")
    private ArrayList<Medicine> medicine_list;

    @SerializedName("profile")
    private ArrayList<ProfileModel> profile;

    @SerializedName("Rating_review")
    private ArrayList<RatingModel>Rating_review;

    @SerializedName("Orderid_list")
    private ArrayList<OrderidlistModel>Orderid_list;

    @SerializedName("Order_Detail_list")
    private ArrayList<OrderdetailModel> Order_Detail_list;

    @SerializedName("preOrder_Detail_list")
    private ArrayList<PreorderModel> preOrder_Detail_list;

    @SerializedName("Order_Precription")
    private ArrayList<ViewprescriptionModel> Order_Precription;

    @SerializedName("med_Preorder_photo")
    private ArrayList<ViewMedicinePhotoModel> med_Preorder_photo;


    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public ProfileModel getProfile_data() { return profile_data; }

    public void setProfile_data(ProfileModel profile_data) { this.profile_data = profile_data; }

    public ArrayList<Medicine> getMedicine_list() {
        return medicine_list;
    }

    public ArrayList<ProfileModel> getProfile() {
        return profile;
    }

    public void setProfile(ArrayList<ProfileModel> profile) {
        this.profile = profile;
    }

    public void setMedicine_list(ArrayList<Medicine> medicine_list) { this.medicine_list = medicine_list; }

    public ArrayList<RatingModel> getRating_review() {
        return Rating_review; }

    public void setRating_review(ArrayList<RatingModel> rating_review) { Rating_review = rating_review; }

    public ArrayList<OrderidlistModel> getOrderid_list() {
        return Orderid_list;
    }

    public void setOrderid_list(ArrayList<OrderidlistModel> orderid_list) {
        Orderid_list = orderid_list;

    }

    public ArrayList<OrderdetailModel> getOrder_Detail_list() {
        return Order_Detail_list;
    }

    public void setOrder_Detail_list(ArrayList<OrderdetailModel> order_Detail_list) {
        Order_Detail_list = order_Detail_list;
    }

    public ArrayList<PreorderModel> getPreOrder_Detail_list() {
        return preOrder_Detail_list;
    }

    public void setPreOrder_Detail_list(ArrayList<PreorderModel> preOrder_Detail_list) {
        this.preOrder_Detail_list = preOrder_Detail_list;
    }

    public ArrayList<ViewprescriptionModel> getOrder_Precription() {
        return Order_Precription;
    }

    public void setOrder_Precription(ArrayList<ViewprescriptionModel> order_Precription) {
        Order_Precription = order_Precription;
    }

    public ArrayList<ViewMedicinePhotoModel> getMed_Preorder_photo() {
        return med_Preorder_photo;
    }

    public void setMed_Preorder_photo(ArrayList<ViewMedicinePhotoModel> med_Preorder_photo) {
        this.med_Preorder_photo = med_Preorder_photo;
    }
}
