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

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.res.ResourcesCompat;
import androidx.lifecycle.ViewModelProvider;

import com.cazaea.sweetalert.SweetAlertDialog;
import com.gift.gemift.Model.LoginResponse;
import com.gift.gemift.Model.OTPSuccessResponse;
import com.gift.gemift.Model.SignUpResponse;
import com.gift.gemift.Model.VonageOTPModel;
import com.gift.gemift.Network.ApiClient;
import com.gift.gemift.Network.ApiInterface;
import com.gift.gemift.R;
import com.gift.gemift.Storage.AppPreference.AppPreferences;
import com.gift.gemift.Storage.DB.ViewModel.UserDetailViewModel;
import com.gift.gemift.Utils.AppUtils;
import com.gift.gemift.Utils.FilterHelper;
import com.gift.gemift.databinding.OtpScreenBinding;

import org.json.JSONObject;

import java.util.TimeZone;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;
import retrofit2.Response;

public class OTP_ValidationSingUp extends AppCompatActivity {

    private final CompositeDisposable compositeDisposable = new CompositeDisposable();
    private OtpScreenBinding binding;
    private String MobileNumber, twoFactorSessionId, twoFactorOtp, countryCode, MailId, PassWord, Gender, FirstName, SecondName, MobileNumberShow;
    private UserDetailViewModel userDetailViewModel;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = OtpScreenBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        final Drawable upArrow = ResourcesCompat.getDrawable(getResources(), R.drawable.ic_baseline_arrow, null);
        FilterHelper.setColorFilter(upArrow, getResources().getColor(R.color.text_color), FilterHelper.Mode.SRC_ATOP);
        getSupportActionBar().setHomeAsUpIndicator(upArrow);

        LoadUi();

    }

    private void LoadUi() {
        Intent dataIntent = getIntent();
        MobileNumber = dataIntent.getStringExtra(MOBILE_NUMBER);
        twoFactorSessionId = dataIntent.getStringExtra(TWO_FACTOR_SESSION_ID);
        twoFactorOtp = dataIntent.getStringExtra(OTP_CODE);
        countryCode = dataIntent.getStringExtra(MOBILE_NUMBER_COUNTRY_CODE);

        MailId = dataIntent.getStringExtra(MAIL_ID);
        PassWord = dataIntent.getStringExtra(PASSWORD);
        Gender = dataIntent.getStringExtra(GENDER);
        FirstName = dataIntent.getStringExtra(FIRST_NAME);
        SecondName = dataIntent.getStringExtra(SECOND_NAME);
        MobileNumberShow = dataIntent.getStringExtra(MOBILE_NUMBER_SHOW);


        userDetailViewModel = new ViewModelProvider(this).get(UserDetailViewModel.class);
        binding.txtMobileNo.setText(dataIntent.getStringExtra(MOBILE_NUMBER_SHOW));
        LoadTimer();

        binding.otpView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (charSequence.length() <= 3) {
                    binding.txtOtpResult.setVisibility(View.GONE);
                } else if (charSequence.length() == 4) {
                    if (countryCode.equals("91")) {
                        validateIndianOTP(binding.otpView.getText().toString(), twoFactorSessionId);
                    } else {
                        validateOTP(dataIntent.getStringExtra(VONAGE_REQUEST_ID));
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        binding.btnProceed.setOnClickListener(v -> {
            if (binding.otpView.getText().length() >= 3) {
                if (countryCode.equals("91")) {
                    validateIndianOTP(binding.otpView.getText().toString(), twoFactorSessionId);
                } else {
                    validateOTP(dataIntent.getStringExtra(VONAGE_REQUEST_ID));
                }

            }

        });
    }

    private void validateIndianOTP(String otp, String twoFactorSessionId) {
        ProgressDialog pDialog = new ProgressDialog(OTP_ValidationSingUp.this);
        pDialog.setMessage("Please Wait...");
        pDialog.show();
        ApiInterface apiInterface = ApiClient.getApiClientOTP().create(ApiInterface.class);
        Observable<Response<OTPSuccessResponse>> otpResponse = apiInterface.VerifyIndianOTP(twoFactorSessionId, otp);
        compositeDisposable.add(otpResponse
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map(result -> result)
                .subscribe(otpModelResponse -> {
                    pDialog.dismiss();
                    if (otpModelResponse.isSuccessful() && (otpModelResponse.code() == 200 || otpModelResponse.code() == 201)) {
                        insertSingUpDetails();
                    } else if (otpModelResponse.code() == 400) {
                        OtpWriteMsg("OTp Mismatch");
                        AppUtils.showMessage(OTP_ValidationSingUp.this, "OTP Mismatch");
                    }

                }, throwable -> AppUtils.showMessage(this, throwable.getMessage())));
    }

    private void validateOTP(String requestId) {
        ProgressDialog pDialog = new ProgressDialog(OTP_ValidationSingUp.this);
        pDialog.setMessage("Please Wait...");
        pDialog.show();
        ApiInterface apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        VonageOTPModel vonageOTPModel = new VonageOTPModel();
        vonageOTPModel.setVonage_request_id(requestId);
        vonageOTPModel.setVonage_response_code(Integer.parseInt(binding.otpView.getText().toString()));
        vonageOTPModel.setRequest_id(2);
        Observable<Response<ResponseBody>> otpResponse = apiInterface.verifyOTP(vonageOTPModel);
        compositeDisposable.add(otpResponse
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map(result -> result)
                .subscribe(otpModelResponse -> {
                    pDialog.dismiss();
                    if (otpModelResponse.isSuccessful() && (otpModelResponse.code() == 200 || otpModelResponse.code() == 201)) {
                        insertSingUpDetails();
                    } else if (otpModelResponse.code() == 404) {
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

    private void OtpWriteMsg(String errorMsg) {
        new SweetAlertDialog(this, SweetAlertDialog.ERROR_TYPE)
                .setTitleText("OTP Mismatch")
                .setContentText(errorMsg)
                .setConfirmText("OK")
                .setConfirmClickListener(Dialog::dismiss)
                .show();
    }

    private void insertSingUpDetails() {
        ProgressDialog pDialog = new ProgressDialog(OTP_ValidationSingUp.this);
        pDialog.setMessage("Please Wait...");
        pDialog.show();
        ApiInterface apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        SignUpResponse signUpResponse = new SignUpResponse(MailId, 0, PassWord, MobileNumber, Gender, FirstName, SecondName, "", "", "","image",countryCode);
         signUpResponse.setTimeZone(TimeZone.getDefault().getID());
         signUpResponse.setApiCallTime(AppUtils.getCurrentDateCopy());
        Observable<Response<ResponseBody>> loginCredantials = apiInterface.insertSingUpDetails(signUpResponse);
        compositeDisposable.add(loginCredantials
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map(result -> result)
                .subscribe(loginResponseResponse -> {
                    pDialog.dismiss();
                    if (loginResponseResponse.isSuccessful() && (loginResponseResponse.code() == 200 || loginResponseResponse.code() == 201)) {
                        getLoginDetailsByNumber(MobileNumber,countryCode);
                    } else {
                        getLoginDetailsByNumber(MobileNumber,countryCode);

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

    private void getLoginDetailsByNumber(String mobileNumber, String countryCode) {
        ProgressDialog pDialog = new ProgressDialog(OTP_ValidationSingUp.this);
        pDialog.setMessage("Please Wait...");
        pDialog.show();
        ApiInterface apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        LoginResponse loginResponse = new LoginResponse();
        loginResponse.setPhone_number(mobileNumber);
        loginResponse.setCountry_code(countryCode);
        Observable<Response<LoginResponse>> loginCredantials = apiInterface.getLoginDetailsByNumber(loginResponse);
        compositeDisposable.add(loginCredantials
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map(result -> result)
                .subscribe(loginResponseResponse -> {
                    pDialog.dismiss();
                    if (loginResponseResponse.isSuccessful() && (loginResponseResponse.code() == 200 || loginResponseResponse.code() == 201)) {
                        AppPreferences appPreferences = new AppPreferences(this);
                        userDetailViewModel.insertFriendListDetails(loginResponseResponse.body().getData());
                        appPreferences.setUserId(loginResponseResponse.body().getData()[0].getLogged_user_id());
                        appPreferences.setTokenId(loginResponseResponse.body().getToken());
                        appPreferences.setPhoneNumber(MobileNumber);
                        appPreferences.setPhoneNumberCountryCode(countryCode);
                        startActivity(new Intent(this, NewUserWelcome.class));
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

    private void LoadTimer() {
        new CountDownTimer(120000, 1000) { // adjust the milli seconds here
            @SuppressLint({"DefaultLocale", "SetTextI18n"})
            public void onTick(long millisUntilFinished) {
                binding.txtOtpTimer.setText("" + String.format("%d min: %d sec",
                        TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished),
                        TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished) -
                                TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished))));
            }

            public void onFinish() {
                binding.txtResendOTP.setVisibility(View.VISIBLE);
                binding.txtOtpTimer.setVisibility(View.GONE);
                binding.txtOtpTimer.setText("00:00");
            }
        }.start();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        if (menuItem.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return (super.onOptionsItemSelected(menuItem));
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            finish();
        }
        return true;
    }

}