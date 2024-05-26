package com.example.medicinefinderpharmacy.Model;

import com.google.gson.annotations.SerializedName;

public class OrderidlistModel {

    @SerializedName("store_id")
    private String store_id;

    @SerializedName("Order_total")
    private String Order_total;

    @SerializedName("Order_id")
    private String Order_id;

    @SerializedName("Order_date")
    private String Order_date;

    @SerializedName("Username")
    private String Username;

    @SerializedName("Order_stage")
    private String Order_stage;

    public String getStore_id() {
        return store_id;
    }

    public void setStore_id(String store_id) {
        this.store_id = store_id;
    }

    public String getOrder_total() {
        return Order_total;
    }

    public void setOrder_total(String order_total) {
        Order_total = order_total;
    }

    public String getOrder_id() {
        return Order_id;
    }

    public void setOrder_id(String order_id) {
        Order_id = order_id;
    }

    public String getOrder_date() {
        return Order_date;
    }

    public void setOrder_date(String order_date) {
        Order_date = order_date;
    }

    public String getUsername() {
        return Username;
    }

    public void setUsername(String username) {
        Username = username;
    }

    public String getOrder_stage() {
        return Order_stage;
    }

    public void setOrder_stage(String order_stage) {
        Order_stage = order_stage;
    }
}
