package com.example.medicinefinderpharmacy.Model;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;


public class SharedPref {


    public String PREFS_NAME = "medi_pharmacy";

    public String id = "id";
    public String Email_id = "Email_id";
    public String Shopname = "Shopname";
    public String Mobile_Number = "Mobile_Number";
    public String Drug_license_no = "Drug_license_no";
    public String GSTIN_No = "GSTIN_No";


    public static final String LOGGED_IN_PREF = "medical_login_status";


    static SharedPreferences getPreferences(Context context) {
        return PreferenceManager.getDefaultSharedPreferences(context);
    }

    public static void setLoggedIn(Context context, boolean loggedIn) {
        SharedPreferences.Editor editor = getPreferences(context).edit();
        editor.putBoolean(LOGGED_IN_PREF, loggedIn);
        editor.apply();
//        return loggedIn;
    }

    public static boolean getLoggedStatus(Context context) {
        return getPreferences(context).getBoolean(LOGGED_IN_PREF, false);
    }



    public void set_profile(Context context, String id, String Email_id, String Shopname,
                            String Mobile_Number, String Drug_license_no, String GSTIN_No) {

        SharedPreferences sharedPref = context.getSharedPreferences(PREFS_NAME, 0);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(this.id, id);
        editor.putString(this.Email_id, Email_id);
        editor.putString(this.Shopname, Shopname);
        editor.putString(this.Mobile_Number, Mobile_Number);
        editor.putString(this.Drug_license_no, Drug_license_no);
        editor.putString(this.GSTIN_No, GSTIN_No);
        editor.apply();

    }

    public String getId(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(PREFS_NAME, 0);
        return prefs.getString(id, "");
    }

    public String getname(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(PREFS_NAME, 0);
        return prefs.getString(Shopname, "");
    }

    public String getnumber(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(PREFS_NAME, 0);
        return prefs.getString(Mobile_Number, "");
    }
    public String getGSTIN(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(PREFS_NAME, 0);
        return prefs.getString(GSTIN_No, "");
    }

    public String getDrugNO(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(PREFS_NAME, 0);
        return prefs.getString(Drug_license_no, "");
    }


    public void clear_data(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(PREFS_NAME, 0);
        SharedPreferences.Editor editor = prefs.edit();
        editor.clear();
        editor.commit();

    }

}
