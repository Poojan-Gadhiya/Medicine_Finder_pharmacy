package com.example.medicinefinderpharmacy.Model;

import com.google.gson.annotations.SerializedName;

public class ProfileModel {

    @SerializedName("id")
    private String id;

    @SerializedName("Shopname")
    private String Shopname;

    @SerializedName("Email_id")
    private String Email_id;

    @SerializedName("Password")
    private String Password;

    @SerializedName("Mobile_Number")
    private String Mobile_Number;

    @SerializedName("Drug_license_no")
    private String Drug_license_no;

    @SerializedName("GSTIN_No")
    private String GSTIN_No;

    @SerializedName("Address")
    private String Address;

    @SerializedName("City")
    private String City;


    @SerializedName("State")
    private String State;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getShopname() {
        return Shopname;
    }

    public void setShopname(String shopname) {
        Shopname = shopname;
    }

    public String getEmail_id() {
        return Email_id;
    }

    public void setEmail_id(String email_id) {
        Email_id = email_id;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }

    public String getMobile_Number() {
        return Mobile_Number;
    }

    public void setMobile_Number(String mobile_Number) {
        Mobile_Number = mobile_Number;
    }

    public String getDrug_license_no() {
        return Drug_license_no;
    }

    public void setDrug_license_no(String drug_license_no) {
        Drug_license_no = drug_license_no;
    }

    public String getGSTIN_No() {
        return GSTIN_No;
    }

    public void setGSTIN_No(String GSTIN_No) {
        this.GSTIN_No = GSTIN_No;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }

    public String getCity() {
        return City;
    }

    public void setCity(String city) {
        City = city;
    }

    public String getState() {
        return State;
    }

    public void setState(String state) {
        State = state;
    }
}
