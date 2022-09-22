package com.gift.gemift.Ui.LoginActivity;

import static com.gift.gemift.Utils.Constant.FIRST_NAME;
import static com.gift.gemift.Utils.Constant.GENDER;
import static com.gift.gemift.Utils.Constant.MAIL_ID;
import static com.gift.gemift.Utils.Constant.MOBILE_NUMBER;
import static com.gift.gemift.Utils.Constant.MOBILE_NUMBER_COUNTRY_CODE;
import static com.gift.gemift.Utils.Constant.MOBILE_NUMBER_SHOW;
import static com.gift.gemift.Utils.Constant.OTP_CODE;
import static com.gift.gemift.Utils.Constant.PASSWORD;
import static com.gift.gemift.Utils.Constant.SECOND_NAME;
import static com.gift.gemift.Utils.Constant.TWO_FACTOR_SESSION_ID;
import static com.gift.gemift.Utils.Constant.VONAGE_REQUEST_ID;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.KeyEvent;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.cazaea.sweetalert.SweetAlertDialog;
import com.gift.gemift.Model.LoginResponse;
import com.gift.gemift.Model.OTPSuccessResponse;
import com.gift.gemift.Model.SignUpOTPresponse;
import com.gift.gemift.Model.VonageOTPModel;
import com.gift.gemift.Network.ApiClient;
import com.gift.gemift.Network.ApiInterface;
import com.gift.gemift.Ui.SplashScreen.SplashScreen;
import com.gift.gemift.Utils.AppUtils;
import com.gift.gemift.databinding.LoginMobileBinding;
import com.google.gson.Gson;

import org.json.JSONObject;

import io.michaelrocks.libphonenumber.android.NumberParseException;
import io.michaelrocks.libphonenumber.android.PhoneNumberUtil;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Response;

public class Login_Mobile extends AppCompatActivity {
    private final CompositeDisposable compositeDisposable = new CompositeDisposable();
    int PERMISSION_ALL = 1;
    private LoginMobileBinding binding;
    private SweetAlertDialog pDialog;

    public static boolean hasPermissions(Context context, String... permissions) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && context != null && permissions != null) {
            for (String permission : permissions) {
                if (ActivityCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
                    return false;
                }
            }
        }
        return true;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = LoginMobileBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        String[] PERMISSIONS = {android.Manifest.permission.READ_EXTERNAL_STORAGE, android.Manifest.permission.CAMERA, android.Manifest.permission.ACCESS_COARSE_LOCATION, android.Manifest.permission.ACCESS_FINE_LOCATION, android.Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.RECEIVE_SMS, Manifest.permission.READ_CONTACTS};

        if (!hasPermissions(this, PERMISSIONS)) {
            ActivityCompat.requestPermissions(this, PERMISSIONS, PERMISSION_ALL);

        }




//        binding.edtMobileno.setText("8883269845");

        pDialog = new SweetAlertDialog(this, SweetAlertDialog.PROGRESS_TYPE);

        binding.btnProceed.setOnClickListener(v -> {

            if (!binding.ccp.isValidFullNumber()) {
                AppUtils.showMessage(this, "Enter Valid Mobile Number");
                binding.edtMobileno.requestFocus();
                return;
            }

            String MobileNumber = binding.edtMobileno.getText().toString();
            String number = MobileNumber.replaceAll("[^0-9]", "");
            getLoginDetailsByNumber(binding.ccp.getSelectedCountryCode(),number, binding.ccp.getSelectedCountryCode().equals("91"));

        });

        binding.ccp.registerCarrierNumberEditText(binding.edtMobileno);

        binding.mailLogin.setOnClickListener(v -> startActivity(new Intent(this, LoginScreen.class)));

        binding.btnSignup.setOnClickListener(view -> startActivity(new Intent(this, SignUpDetails.class)));
    }

    private void getLoginDetailsByNumber(String countryCode, String phoneNumber, boolean otpCheck) {
        pDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
        pDialog.setTitleText("Loading");
        pDialog.setCancelable(false);
        pDialog.show();
        ApiInterface apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        LoginResponse loginResponse = new LoginResponse();
        loginResponse.setPhone_number(phoneNumber);
        loginResponse.setCountry_code(countryCode);
        Observable<Response<LoginResponse>> loginCredantials = apiInterface.getLoginDetailsByNumber(loginResponse);
        compositeDisposable.add(loginCredantials
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map(result -> result)
                .subscribe(loginResponseResponse -> {
                    if (loginResponseResponse.isSuccessful() && (loginResponseResponse.code() == 200 || loginResponseResponse.code() == 201)) {
                        if (otpCheck) {
                            sendIndianOtp(phoneNumber,countryCode);
                        } else {
                            sendOtp(phoneNumber,countryCode);
                        }

                    } else if (loginResponseResponse.code() == 401) {
                        pDialog.dismiss();
                        AppUtils.showMessage(this, "No user found");
                    } else {
                        pDialog.dismiss();
                        try {
                            JSONObject jsonObject = new JSONObject(loginResponseResponse.errorBody().string());
                            String error = jsonObject.getString("Error");
                            Toast.makeText(this, error, Toast.LENGTH_LONG).show();
                        } catch (Exception e) {
                            Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    }

                }, throwable -> {
                    pDialog.dismiss();
                    AppUtils.showMessage(this, throwable.getMessage());
                }));

    }

    private void sendIndianOtp(String fullNumber, String countryCode) {
        String otpCode = AppUtils.generatePIN();
        ApiInterface apiInterface = ApiClient.getApiClientOTP().create(ApiInterface.class);
        Observable<Response<OTPSuccessResponse>> otpResponse = apiInterface.getIndianOTP(fullNumber, otpCode, "Signin");
        compositeDisposable.add(otpResponse
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map(result -> result)
                .subscribe(otpModelResponse -> {
                    pDialog.dismiss();
                    if (otpModelResponse.isSuccessful() && (otpModelResponse.code() == 200 || otpModelResponse.code() == 201)) {
                        Intent intent = new Intent(Login_Mobile.this, OTP_Validation.class);
                        intent.putExtra(TWO_FACTOR_SESSION_ID, otpModelResponse.body().getDetails());
                        intent.putExtra(OTP_CODE, otpCode);
                        intent.putExtra(VONAGE_REQUEST_ID, "");
                        intent.putExtra(MOBILE_NUMBER, fullNumber);
                        intent.putExtra(MOBILE_NUMBER_SHOW, binding.ccp.getFormattedFullNumber());
                        intent.putExtra(MOBILE_NUMBER_COUNTRY_CODE, countryCode);
                        startActivity(intent);
                    } else if (otpModelResponse.code() == 400) {
                        AppUtils.showMessage(Login_Mobile.this, "SMS sending to this number is denied - Contact admin");
                    }

                }, throwable -> {
                    pDialog.dismiss();
                    AppUtils.showMessage(this, throwable.getMessage());
                }));
    }

    private void sendOtp(String phoneNumber, String countryCode) {
        ApiInterface apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        VonageOTPModel vonageOTPModel = new VonageOTPModel();
        vonageOTPModel.setPhone_number(phoneNumber);
        vonageOTPModel.setCountry_code(countryCode);
        vonageOTPModel.setRequest_id(1);
        Observable<Response<SignUpOTPresponse>> otpResponse = apiInterface.getSignUpOTP(vonageOTPModel);
        compositeDisposable.add(otpResponse
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map(result -> result)
                .subscribe(otpModelResponse -> {
                    pDialog.dismiss();
                    if (otpModelResponse.isSuccessful() && (otpModelResponse.code() == 200 || otpModelResponse.code() == 201)) {
                        Intent intent = new Intent(Login_Mobile.this, OTP_Validation.class);
                        intent.putExtra(TWO_FACTOR_SESSION_ID, "");
                        intent.putExtra(OTP_CODE, "");
                        intent.putExtra(VONAGE_REQUEST_ID, otpModelResponse.body().getStatus().getVonageRequestId());
                        intent.putExtra(MOBILE_NUMBER, phoneNumber);
                        intent.putExtra(MOBILE_NUMBER_SHOW, binding.ccp.getFormattedFullNumber());
                        intent.putExtra(MOBILE_NUMBER_COUNTRY_CODE, countryCode);
                        intent.putExtra(MOBILE_NUMBER_SHOW, binding.ccp.getFormattedFullNumber());
                        intent.putExtra(MOBILE_NUMBER_COUNTRY_CODE, binding.ccp.getSelectedCountryCode());

                        startActivity(intent);
                    } else if (otpModelResponse.code() == 400) {
                        try {
                            JSONObject jsonObject = new JSONObject(otpModelResponse.errorBody().string());
                            String error = jsonObject.getString("Error");
                            Toast.makeText(this, error, Toast.LENGTH_LONG).show();
                        } catch (Exception e) {
                            Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    } else {
                        try {
                            JSONObject jsonObject = new JSONObject(otpModelResponse.errorBody().string());
                            String error = jsonObject.getString("Error");
                            Toast.makeText(this, error, Toast.LENGTH_LONG).show();
                        } catch (Exception e) {
                            Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    }

                }, throwable -> {
                    pDialog.dismiss();
                    AppUtils.showMessage(this, throwable.getMessage());
                }));
    }

/*
    private void getLoginDetailsByNumber(String phoneNumber) {
        ProgressDialog pDialog = new ProgressDialog(Login_Mobile.this);
        pDialog.setMessage("Please Wait...");
        pDialog.show();
        ApiInterface apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        LoginResponse loginResponse = new LoginResponse();
        loginResponse.setPhone_number(phoneNumber);
        Observable<Response<LoginResponse>> loginCredantials = apiInterface.getLoginDetailsByNumber(loginResponse);
        compositeDisposable.add(loginCredantials
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map(result -> result)
                .subscribe(loginResponseResponse -> {
                    pDialog.dismiss();
                    if (loginResponseResponse.isSuccessful() && (loginResponseResponse.code() == 200 || loginResponseResponse.code() == 201)) {
                        Intent intent = new Intent(Login_Mobile.this, OTP_Validation.class);
                        intent.putExtra(MOBILE_NUMBER, binding.edtMobileno.getText().toString());
                        intent.putExtra(MOBILE_NUMBER_SHOW, binding.ccp.getFormattedFullNumber());
                        startActivity(intent);
                    } else if (loginResponseResponse.code() == 401) {
                        Intent intent = new Intent(Login_Mobile.this, OTP_Validation.class);
                        intent.putExtra(MOBILE_NUMBER, binding.edtMobileno.getText().toString());
                        intent.putExtra(MOBILE_NUMBER_SHOW, binding.ccp.getFormattedFullNumber());
                        startActivity(intent);
                    } else {
                        try {
                            JSONObject jsonObject = new JSONObject(loginResponseResponse.errorBody().string());
                            String error = jsonObject.getString("Error");
                            Toast.makeText(this, error, Toast.LENGTH_LONG).show();
                        } catch (Exception e) {
                            Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    }

                }, throwable -> AppUtils.showMessage(this, throwable.getMessage())));

    }
*/

    @Override
    protected void onDestroy() {
        super.onDestroy();
        compositeDisposable.clear();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            startActivity(new Intent(this, SplashScreen.class));
        }
        return true;
    }
}
