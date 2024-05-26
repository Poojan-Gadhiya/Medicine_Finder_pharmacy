package com.example.medicinefinderpharmacy.Model;

import com.google.gson.annotations.SerializedName;

public class ViewMedicinePhotoModel {

    @SerializedName("id")
    private String id;

    @SerializedName("med_photo")
    private String med_photo;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMed_photo() {
        return med_photo;
    }

    public void setMed_photo(String med_photo) {
        this.med_photo = med_photo;
    }
}
