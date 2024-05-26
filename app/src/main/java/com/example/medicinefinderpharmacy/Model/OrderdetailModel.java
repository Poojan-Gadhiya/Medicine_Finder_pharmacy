package com.example.medicinefinderpharmacy.Model;

import com.google.gson.annotations.SerializedName;

public class OrderdetailModel {


    @SerializedName("id")
    private int id;

    @SerializedName("store_id")
    private String store_id;

    @SerializedName("medicine_name")
    private String medicine_name;

    @SerializedName("medicine_qty")
    private String medicine_qty;

    @SerializedName("price")
    private String price;

    @SerializedName("Order_id")
    private String Order_id;

    @SerializedName("Order_stage")
    private String Order_stage;

    @SerializedName("Username")
    private String Username;

    @SerializedName("payment_type")
    private String payment_type;

    @SerializedName("is_Paid")
    private String is_Paid;

    @SerializedName("Order_date")
    private String Order_date;

    @SerializedName("Order_total")
    private String Order_total;

    @SerializedName("prescription_photo")
    private String prescription_photo;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getStore_id() {
        return store_id;
    }

    public void setStore_id(String store_id) {
        this.store_id = store_id;
    }

    public String getMedicine_name() {
        return medicine_name;
    }

    public void setMedicine_name(String medicine_name) {
        this.medicine_name = medicine_name;
    }

    public String getMedicine_qty() {
        return medicine_qty;
    }

    public void setMedicine_qty(String medicine_qty) {
        this.medicine_qty = medicine_qty;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getOrder_id() {
        return Order_id;
    }

    public void setOrder_id(String order_id) {
        Order_id = order_id;
    }

    public String getOrder_stage() {
        return Order_stage;
    }

    public void setOrder_stage(String order_stage) {
        Order_stage = order_stage;
    }

    public String getUsername() {
        return Username;
    }

    public void setUsername(String username) {
        Username = username;
    }

    public String getPayment_type() {
        return payment_type;
    }

    public void setPayment_type(String payment_type) {
        this.payment_type = payment_type;
    }

    public String getIs_Paid() {
        return is_Paid;
    }

    public void setIs_Paid(String is_Paid) {
        this.is_Paid = is_Paid;
    }

    public String getOrder_date() {
        return Order_date;
    }

    public void setOrder_date(String order_date) {
        Order_date = order_date;
    }

    public String getOrder_total() {
        return Order_total;
    }

    public void setOrder_total(String order_total) {
        Order_total = order_total;
    }

    public String getPrescription_photo() { return prescription_photo; }

    public void setPrescription_photo(String prescription_photo) { this.prescription_photo = prescription_photo; }
}
