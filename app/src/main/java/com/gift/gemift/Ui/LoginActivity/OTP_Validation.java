package com.gift.gemift.Ui.LoginActivity;

import static com.gift.gemift.Utils.Constant.MOBILE_NUMBER;
import static com.gift.gemift.Utils.Constant.MOBILE_NUMBER_COUNTRY_CODE;
import static com.gift.gemift.Utils.Constant.MOBILE_NUMBER_SHOW;
import static com.gift.gemift.Utils.Constant.OTP_CODE;
import static com.gift.gemift.Utils.Constant.TWO_FACTOR_SESSION_ID;
import static com.gift.gemift.Utils.Constant.VONAGE_REQUEST_ID;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
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
import com.gift.gemift.Model.Root;
import com.gift.gemift.Model.UnapprovedOccasion;
import com.gift.gemift.Model.VonageOTPModel;
import com.gift.gemift.Network.ApiClient;
import com.gift.gemift.Network.ApiInterface;
import com.gift.gemift.R;
import com.gift.gemift.Storage.AppPreference.AppPreferences;
import com.gift.gemift.Storage.DB.ViewModel.UserDetailViewModel;
import com.gift.gemift.Ui.DashBoard.DashBoard;
import com.gift.gemift.Ui.DashBoard.Fragments.Friends.Invitation_Occasion;
import com.gift.gemift.Ui.DashBoard.StatsScreen;
import com.gift.gemift.Utils.AppUtils;
import com.gift.gemift.Utils.FilterHelper;
import com.gift.gemift.databinding.OtpScreenBinding;
import com.google.gson.Gson;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;
import retrofit2.Response;

public class OTP_Validation extends AppCompatActivity {

    private final CompositeDisposable compositeDisposable = new CompositeDisposable();
    ArrayList<UnapprovedOccasion> occasionArrayList = new ArrayList<>();
    private OtpScreenBinding binding;
    private String MobileNumber, twoFactorSessionId, mobileNumberShow, twoFactorOtp, countryCode;
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
        userDetailViewModel = new ViewModelProvider(this).get(UserDetailViewModel.class);
        mobileNumberShow = dataIntent.getStringExtra(MOBILE_NUMBER_SHOW);
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
        ProgressDialog pDialog = new ProgressDialog(OTP_Validation.this);
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
                        getLoginDetailsByNumber(MobileNumber);
                    } else if (otpModelResponse.code() == 400) {
                        OtpWriteMsg("OTP Mismatch");
                        AppUtils.showMessage(OTP_Validation.this, "OTP Mismatch");
                    }

                }, throwable -> AppUtils.showMessage(this, throwable.getMessage())));
    }

    private void validateOTP(String requestId) {
        SweetAlertDialog pDialog = new SweetAlertDialog(this, SweetAlertDialog.PROGRESS_TYPE);
        pDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
        pDialog.setTitleText("Loading");
        pDialog.setCancelable(false);
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
                        getLoginDetailsByNumber(MobileNumber);
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

    private void getLoginDetailsByNumber(String phoneNumber) {
        SweetAlertDialog pDialog = new SweetAlertDialog(this, SweetAlertDialog.PROGRESS_TYPE);
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
                    pDialog.dismiss();
                    if (loginResponseResponse.isSuccessful() && (loginResponseResponse.code() == 200 || loginResponseResponse.code() == 201)) {
                        AppPreferences appPreferences = new AppPreferences(this);
                        userDetailViewModel.insertFriendListDetails(loginResponseResponse.body().getData());
                        appPreferences.setUserId(loginResponseResponse.body().getData()[0].getLogged_user_id());
                        appPreferences.setTokenId(loginResponseResponse.body().getToken());
                        if (loginResponseResponse.body().getData()[0].getImage_url() != null)
                            appPreferences.setProfileImage(loginResponseResponse.body().getData()[0].getImage_url());
                        appPreferences.setPhoneNumber(MobileNumber);
                        appPreferences.setPhoneNumberCountryCode(countryCode);
                        startActivity(new Intent(this, DashBoard.class));

//                        getInvites(loginResponseResponse.body().getData()[0].getLogged_user_id(), loginResponseResponse.body().getData()[0].getPhone_number());
                    } else if (loginResponseResponse.code() == 401) {
                        Intent intent = new Intent(OTP_Validation.this, StatsScreen.class);
                        intent.putExtra(MOBILE_NUMBER, MobileNumber);
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

//    private void getInvites(String logged_user_id, String phone_number) {
//        SweetAlertDialog pDialog = new SweetAlertDialog(this, SweetAlertDialog.PROGRESS_TYPE);
//        pDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
//        pDialog.setTitleText("Loading");
//        pDialog.setCancelable(false);
//        pDialog.show();
//        ApiInterface apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
//        Observable<Response<ArrayList<Root>>> notify = apiInterface.getInvites(1, logged_user_id, phone_number);
//        compositeDisposable.add(notify
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .map(result -> result)
//                .subscribe(response -> {
//                    pDialog.dismiss();
//                    if (response.isSuccessful() && (response.code() == 200 || response.code() == 201)) {
//                        if (response.body() != null) {
//                            ArrayList<Root> roots = response.body();
//
//                            for (Root datum : roots) {
////                                if (datum.getContributor_invites() != null) {
////                                    if (datum.getContributor_invites().size() > 0) {
////                                        String circlename = datum.getContributor_invites().get(0).getFriend_circle_name();
////                                        String circle_id = datum.getContributor_invites().get(0).getFriend_circle_id();
////                                        Intent i = new Intent(this, InvitationGroup.class);
////                                        i.putExtra(FRIEND_CIRCLE_ID, circle_id);
////                                        i.putExtra(FRIEND_CIRCLE_NAME, circlename);
////                                        i.putExtra(MOBILE_NUMBER, phone_number);
////                                        startActivity(i);
////
////                                }
////                                    else {
////                                    startActivity(new Intent(this, DashBoard.class));
////                                }
//
//
//                                if (datum.getUnapproved_occasions() != null) {
//                                    if (datum.getUnapproved_occasions().size() > 0) {
//                                        occasionArrayList.addAll(datum.getUnapproved_occasions());
//
//                                        Intent i = new Intent(this, Invitation_Occasion.class);
//                                        i.putExtra("List", new Gson().toJson(occasionArrayList));
//                                        startActivity(i);
//
//
//                                    } else {
//                                        startActivity(new Intent(this, DashBoard.class));
//                                    }
//                                }
////                                else {
////                                    startActivity(new Intent(this, DashBoard.class));
////                                }
//                            }
//                        }
//                    } else if (response.code() == 400) {
//                        try {
//                            JSONObject jsonObject = new JSONObject(response.errorBody().string());
//                            String error = jsonObject.getString("Error");
//                            Toast.makeText(OTP_Validation.this, error, Toast.LENGTH_LONG).show();
//                        } catch (Exception e) {
//                            Toast.makeText(OTP_Validation.this, e.getMessage(), Toast.LENGTH_LONG).show();
//                            startActivity(new Intent(this, DashBoard.class));
//
//                        }
//                    }
//
//                }, throwable -> {
//                    pDialog.dismiss();
//                    AppUtils.showMessage(OTP_Validation.this, throwable.getMessage());
//                }));
//    }


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
