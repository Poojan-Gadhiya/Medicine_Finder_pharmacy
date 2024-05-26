package com.example.medicinefinderpharmacy.Model;

import com.google.gson.annotations.SerializedName;

public class PreorderModel {

    @SerializedName("id")
    private String id;

    @SerializedName("Username")
    private String Username;

    @SerializedName("medicine_name")
    private String medicine_name;

    @SerializedName("med_qty")
    private String med_qty;

    @SerializedName("med_photo")
    private String med_photo;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return Username;
    }

    public void setUsername(String username) {
        Username = username;
    }

    public String getMedicine_name() {
        return medicine_name;
    }

    public void setMedicine_name(String medicine_name) {
        this.medicine_name = medicine_name;
    }

    public String getMed_qty() {
        return med_qty;
    }

    public void setMed_qty(String med_qty) {
        this.med_qty = med_qty;
    }

    public String getMed_photo() {
        return med_photo;
    }

    public void setMed_photo(String med_photo) {
        this.med_photo = med_photo;
    }
}
