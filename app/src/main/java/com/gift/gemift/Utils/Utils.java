package com.gift.gemift.Utils;

import android.app.Activity;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.cazaea.sweetalert.SweetAlertDialog;

import io.michaelrocks.libphonenumber.android.NumberParseException;
import io.michaelrocks.libphonenumber.android.PhoneNumberUtil;

public class Utils {
    static SweetAlertDialog pDialog = null;


    public static void hideKeyboard(Activity activity) {
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        //Find the currently focused view, so we can grab the correct window token from it.
        View view = activity.getCurrentFocus();
        //If no view currently has focus, create a new one, just so we can grab a window token from it
        if (view == null) {
            view = new View(activity);
        }
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    public static void requestPermission(Activity activity, String permission) {
        if (ContextCompat.checkSelfPermission(activity, permission)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(activity, new String[]{permission}, 0);
        }
    }


    public static void showProgressBar(Activity activity){


        if(pDialog != null){
            pDialog.dismiss();
            pDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
            pDialog.setTitleText("Loading");
            pDialog.setCancelable(false);
            pDialog.show();
        }else {
            pDialog = new SweetAlertDialog(activity);
            pDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
            pDialog.setTitleText("Loading");
            pDialog.setCancelable(false);
            pDialog.show();
        }
    }


    public static  void hideProgressBar(Activity activity){
        if(pDialog !=null){
            pDialog.dismiss();
        }
    }


    public static String getCountryCode(String number, Activity activity) throws NumberParseException {
        PhoneNumberUtil phoneNumberUtil = PhoneNumberUtil.createInstance(activity);
        return ""+phoneNumberUtil.parse(number, null).getCountryCode();
    }

    public static String removeCountryCode(String number, Activity activity) throws NumberParseException {
        PhoneNumberUtil phoneNumberUtil = PhoneNumberUtil.createInstance(activity);
        return ""+phoneNumberUtil.parse(number, null).getNationalNumber();
    }
}
