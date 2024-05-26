package com.example.medicinefinderpharmacy.Model;

import com.google.gson.annotations.SerializedName;

public class ViewprescriptionModel {

    @SerializedName("id")
    private String id;

    @SerializedName("prescription_photo")
    private String prescription_photo;

    public String getId() { return id; }

    public void setId(String id) { this.id = id; }

    public String getPrescription_photo() { return prescription_photo; }

    public void setPrescription_photo(String prescription_photo) { this.prescription_photo = prescription_photo; }
}
