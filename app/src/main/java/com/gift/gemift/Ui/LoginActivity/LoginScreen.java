package com.gift.gemift.Ui.LoginActivity;

import static com.gift.gemift.Utils.Constant.NO_INTERNET_CHECK;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.lifecycle.ViewModelProvider;

import com.cazaea.sweetalert.SweetAlertDialog;
import com.gift.gemift.Model.LoginResponse;
import com.gift.gemift.Network.ApiClient;
import com.gift.gemift.Network.ApiInterface;
import com.gift.gemift.Storage.DB.ViewModel.FriendListViewModel;
import com.gift.gemift.Ui.DashBoard.DashBoard;
import com.gift.gemift.Utils.AppUtils;
import com.gift.gemift.Utils.NetworkUtil;
import com.gift.gemift.databinding.LoginScreenBinding;
import com.google.gson.Gson;

import org.json.JSONObject;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Response;

public class LoginScreen extends AppCompatActivity {
    private final CompositeDisposable compositeDisposable = new CompositeDisposable();
    int PERMISSION_ALL = 1;
    private LoginScreenBinding binding;

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
        binding = LoginScreenBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        String[] PERMISSIONS = {android.Manifest.permission.READ_EXTERNAL_STORAGE, android.Manifest.permission.CAMERA,Manifest.permission.READ_CONTACTS, android.Manifest.permission.ACCESS_COARSE_LOCATION, android.Manifest.permission.ACCESS_FINE_LOCATION, android.Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.RECEIVE_SMS};

        if (!hasPermissions(this, PERMISSIONS)) {
            ActivityCompat.requestPermissions(this, PERMISSIONS, PERMISSION_ALL);

        }

//        binding.edtEmail.setText("maniraman505@gmail.com");
//        binding.edtPassword.setText("abc1234");

        binding.btnProceed.setOnClickListener(view -> {
            if (binding.edtEmail.getText().toString().isEmpty()) {
                AppUtils.showMessage(this, "Enter MailId");
                binding.edtEmail.requestFocus();
            } else if (binding.edtPassword.getText().toString().isEmpty()) {
                AppUtils.showMessage(this, "Enter Password");
                binding.edtPassword.requestFocus();
            } else {
                if (!NetworkUtil.getConnectivityStatusString(this).equals(NO_INTERNET_CHECK)) {
                    getLoginDetails(binding.edtEmail.getText().toString().trim(), binding.edtPassword.getText().toString().trim());
                } else {
                    AppUtils.showMessage(this, "No internet connection");
                }
            }

        });

        binding.btnSignup.setOnClickListener(view -> startActivity(new Intent(this, SignUpDetails.class)));

        binding.txtForgotPassword.setOnClickListener(view -> startActivity(new Intent(this, ForgotPassword.class)));
    }

    private void getLoginDetails(String mailId, String password) {
        SweetAlertDialog pDialog = new SweetAlertDialog(this, SweetAlertDialog.PROGRESS_TYPE);
        pDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
        pDialog.setTitleText("Loading");
        pDialog.setCancelable(false);
        pDialog.show();
        ApiInterface apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        LoginResponse loginResponse = new LoginResponse(mailId, password);
        Observable<Response<LoginResponse>> loginCredantials = apiInterface.getLoginDetails(loginResponse);
        compositeDisposable.add(loginCredantials
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map(result -> result)
                .subscribe(loginResponseResponse -> {
                    pDialog.dismiss();
                    if (loginResponseResponse.isSuccessful() && (loginResponseResponse.code() == 200 || loginResponseResponse.code() == 201)) {
                        Gson gson = new Gson();
                        FriendListViewModel friendListViewModel = new ViewModelProvider(this).get(FriendListViewModel.class);
////                        FriendListEntity[] friendListEntity = gson.fromJson(data, FriendListEntity[].class);
//                        friendListViewModel.insertFriendListDetails(friendListEntity);
//                        AppPreferences appPreferences = new AppPreferences(this);
//                        appPreferences.setTokenId(loginResponseResponse.body().getToken());
//                        appPreferences.setUserId(friendListEntity[0].getUser_id());
                        startActivity(new Intent(this, DashBoard.class));
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

    @Override
    protected void onDestroy() {
        super.onDestroy();
        compositeDisposable.clear();
    }
}
