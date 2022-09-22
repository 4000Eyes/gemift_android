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

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.gift.gemift.Model.OTPSuccessResponse;
import com.gift.gemift.Model.SignUpOTPresponse;
import com.gift.gemift.Model.SignUpResponse;
import com.gift.gemift.Model.VonageOTPModel;
import com.gift.gemift.Network.ApiClient;
import com.gift.gemift.Network.ApiInterface;
import com.gift.gemift.Storage.DB.ViewModel.FriendListViewModel;
import com.gift.gemift.Utils.AppUtils;
import com.gift.gemift.databinding.SignupDetailsBinding;
import com.google.gson.Gson;

import org.json.JSONObject;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;
import retrofit2.Response;

public class SignUpDetails extends AppCompatActivity {
    private final CompositeDisposable compositeDisposable = new CompositeDisposable();
    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    private SignupDetailsBinding binding;
    private FriendListViewModel friendListViewModel;
    private String gender = "", MobileNumber;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = SignupDetailsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        friendListViewModel = new ViewModelProvider(this).get(FriendListViewModel.class);
        binding.ccp.registerCarrierNumberEditText(binding.edtMobileno);

        binding.radioGenderGroup.setOnCheckedChangeListener((group, checkedId) -> {
            if (binding.radioMale.isChecked()) {
                gender = "M";
            } else {
                gender = "F";
            }
        });

        LoadUi();

        binding.btnProceed.setOnClickListener(v -> {

            if (binding.edtFirstName.getText().toString().isEmpty()) {
                AppUtils.showMessage(this, "Enter First Name");
                binding.edtFirstName.requestFocus();
            } else if (binding.edtLastName.getText().toString().isEmpty()) {
                AppUtils.showMessage(this, "Enter Last Name");
                binding.edtLastName.requestFocus();
            } else if (binding.edtMobileno.getText().toString().isEmpty()) {
                AppUtils.showMessage(this, "Enter Mobile Number");
                binding.edtMobileno.requestFocus();
            } else if (!binding.ccp.isValidFullNumber()) {
                AppUtils.showMessage(this, "Enter Valid Mobile Number");
                binding.edtMobileno.requestFocus();
            } else if (gender.isEmpty()) {
                AppUtils.showMessage(this, "Select Gender");
            } else if (binding.edtEmail.getText().toString().isEmpty()) {
                AppUtils.showMessage(this, "Enter Mail Id");
                binding.edtEmail.requestFocus();
            } else if (!binding.edtEmail.getText().toString().matches(emailPattern)) {
                AppUtils.showMessage(this, "Enter Valid Mail Id");
                binding.edtEmail.requestFocus();
            } else if (binding.edtPassword.getText().toString().isEmpty()) {
                AppUtils.showMessage(this, "Enter Password");
                binding.edtPassword.requestFocus();
            } else {
//                insertSingUpDetails();
                if (binding.ccp.getSelectedCountryCode().equals("91")) {

                    String MobileNumber = binding.edtMobileno.getText().toString();
                    String number = MobileNumber.replaceAll("[^0-9]", "");
                    sendIndianOtp(number, binding.ccp.getSelectedCountryCode());
                } else {
                    String MobileNumber = binding.edtMobileno.getText().toString();
                    String number = MobileNumber.replaceAll("[^0-9]", "");
                    sendOtp(number, binding.ccp.getSelectedCountryCode());
                }
            }
        });

    }

    private void LoadUi() {
        Intent dataIntent = getIntent();
//        MobileNumber = dataIntent.getStringExtra(MOBILE_NUMBER);
//        binding.edtMobileno.setText(MobileNumber);
    }

    private void insertSingUpDetails() {
        ProgressDialog pDialog = new ProgressDialog(SignUpDetails.this);
        pDialog.setMessage("Please Wait...");
        pDialog.show();
        ApiInterface apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        SignUpResponse signUpResponse = new SignUpResponse(binding.edtEmail.getText().toString(), 0, binding.edtPassword.getText().toString(), binding.ccp.getFullNumber(), gender, binding.edtFirstName.getText().toString(), binding.edtLastName.getText().toString(), "", "", binding.ccp.getSelectedCountryName(),"image","");
        Observable<Response<ResponseBody>> loginCredantials = apiInterface.insertSingUpDetails(signUpResponse);
        compositeDisposable.add(loginCredantials
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map(result -> result)
                .subscribe(loginResponseResponse -> {
                    pDialog.dismiss();
                    if (loginResponseResponse.isSuccessful() && (loginResponseResponse.code() == 200 || loginResponseResponse.code() == 201)) {
//                        String data = loginResponseResponse.body().getData();
//                        SignUpResponse gson = new Gson().fromJson(data, SignUpResponse.class);
//                        AppPreferences appPreferences = new AppPreferences(this);
//                        appPreferences.setUserId(loginResponseResponse.body().getData().getUser_id());
//                        startActivity(new Intent(this, NewUserWelcome.class));

                    } else {
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

    private void sendIndianOtp(String mobileNumber, String CountryCode) {
        ProgressDialog pDialog = new ProgressDialog(SignUpDetails.this);
        pDialog.setMessage("Please Wait...");
        pDialog.show();
        String otpCode = AppUtils.generatePIN();
        ApiInterface apiInterface = ApiClient.getApiClientOTP().create(ApiInterface.class);
        Observable<Response<OTPSuccessResponse>> otpResponse = apiInterface.getIndianOTP(mobileNumber, otpCode, "Signin");
        compositeDisposable.add(otpResponse
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map(result -> result)
                .subscribe(otpModelResponse -> {
                    pDialog.dismiss();
                    if (otpModelResponse.isSuccessful() && (otpModelResponse.code() == 200 || otpModelResponse.code() == 201)) {
                        Intent intent = new Intent(SignUpDetails.this, OTP_ValidationSingUp.class);
                        intent.putExtra(TWO_FACTOR_SESSION_ID, otpModelResponse.body().getDetails());
                        intent.putExtra(OTP_CODE, otpCode);
                        intent.putExtra(VONAGE_REQUEST_ID, "");
                        intent.putExtra(MOBILE_NUMBER, mobileNumber);
                        intent.putExtra(MOBILE_NUMBER_SHOW, binding.ccp.getFormattedFullNumber());
                        intent.putExtra(MOBILE_NUMBER_COUNTRY_CODE, CountryCode);

                        intent.putExtra(MAIL_ID, binding.edtEmail.getText().toString());
                        intent.putExtra(PASSWORD, binding.edtPassword.getText().toString());
                        intent.putExtra(GENDER, gender);
                        intent.putExtra(FIRST_NAME, binding.edtFirstName.getText().toString());
                        intent.putExtra(SECOND_NAME, binding.edtLastName.getText().toString());
                        startActivity(intent);
                    } else if (otpModelResponse.code() == 400) {
                        AppUtils.showMessage(SignUpDetails.this, "SMS sending to this number is denied - Contact admin");

                    }

                }, throwable -> AppUtils.showMessage(this, throwable.getMessage())));
    }

    private void sendOtp(String phoneNumber, String selectedCountryCodeWithPlus) {
        String otpCode = AppUtils.generatePIN();

        ProgressDialog pDialog = new ProgressDialog(SignUpDetails.this);
        pDialog.setMessage("Please Wait...");
        pDialog.show();
        ApiInterface apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        VonageOTPModel vonageOTPModel = new VonageOTPModel();
        vonageOTPModel.setPhone_number(phoneNumber);
        vonageOTPModel.setCountry_code(selectedCountryCodeWithPlus);
        vonageOTPModel.setRequest_id(1);
        Observable<Response<SignUpOTPresponse>> otpResponse = apiInterface.getSignUpOTP(vonageOTPModel);
        compositeDisposable.add(otpResponse
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map(result -> result)
                .subscribe(otpModelResponse -> {
                    pDialog.dismiss();
                    if (otpModelResponse.isSuccessful() && (otpModelResponse.code() == 200 || otpModelResponse.code() == 201)) {
                        Intent intent = new Intent(SignUpDetails.this, OTP_ValidationSingUp.class);
                        intent.putExtra(TWO_FACTOR_SESSION_ID, "");
                        intent.putExtra(OTP_CODE, "");
                        intent.putExtra(VONAGE_REQUEST_ID, otpModelResponse.body().getStatus().getVonageRequestId());
                        intent.putExtra(MOBILE_NUMBER, phoneNumber);
                        intent.putExtra(MOBILE_NUMBER_SHOW, binding.ccp.getFormattedFullNumber());
                        intent.putExtra(MOBILE_NUMBER_COUNTRY_CODE, binding.ccp.getSelectedCountryCode());
                        intent.putExtra(MOBILE_NUMBER_SHOW, binding.ccp.getFormattedFullNumber());
                        intent.putExtra(MOBILE_NUMBER_COUNTRY_CODE, selectedCountryCodeWithPlus);

                        intent.putExtra(MAIL_ID, binding.edtEmail.getText().toString());
                        intent.putExtra(PASSWORD, binding.edtPassword.getText().toString());
                        intent.putExtra(GENDER, gender);
                        intent.putExtra(FIRST_NAME, binding.edtFirstName.getText().toString());
                        intent.putExtra(SECOND_NAME, binding.edtLastName.getText().toString());
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

                }, throwable -> AppUtils.showMessage(this, throwable.getMessage())));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        compositeDisposable.clear();
    }
}
