package com.gift.gemift.Ui.DashBoard.Fragments.Profile;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.MenuItem;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;

import com.gift.gemift.Model.ImageUploadResponse;
import com.gift.gemift.Model.UpdateImagePathResponse;
import com.gift.gemift.Network.ApiClient;
import com.gift.gemift.Network.ApiInterface;
import com.gift.gemift.R;
import com.gift.gemift.Storage.AppPreference.AppPreferences;
import com.gift.gemift.Ui.DashBoard.DashBoard;
import com.gift.gemift.Utils.AppUtils;
import com.gift.gemift.Utils.FilterHelper;
import com.gift.gemift.databinding.UpdateProfileBinding;
import com.github.dhaval2404.imagepicker.ImagePicker;

import java.io.File;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;
import retrofit2.Response;

public class UpdateProfile extends AppCompatActivity {

    private final CompositeDisposable compositeDisposable = new CompositeDisposable();
    private String base64;
    private UpdateProfileBinding binding;
    private AppPreferences appPreferences;
    private ProgressDialog pDialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = UpdateProfileBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        setSupportActionBar(binding.appbar.toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        final Drawable upArrow = ResourcesCompat.getDrawable(getResources(), R.drawable.ic_baseline_arrow, null);
        FilterHelper.setColorFilter(upArrow, getResources().getColor(R.color.white), FilterHelper.Mode.SRC_ATOP);
        getSupportActionBar().setHomeAsUpIndicator(upArrow);
        appPreferences = new AppPreferences(this);
        binding.appbar.txtTitle.setText("Update Profile");


        binding.imgProfileImage.setOnClickListener(v -> {
            ImagePicker.Companion.with(this)
                    .crop()
                    .saveDir(getExternalFilesDir("images"))
                    .compress(512)
                    .maxResultSize(1080, 1080)
                    .start();
        });

        binding.btnProceed.setOnClickListener(v -> {
            imageUpload();
        });
    }

    private void imageUpload() {
        pDialog = new ProgressDialog(UpdateProfile.this);
        pDialog.setMessage("Please Wait...");
        pDialog.show();
        ApiInterface apiInterface = ApiClient.getApiClientImageUpload().create(ApiInterface.class);
        ImageUploadResponse imageUploadResponse = new ImageUploadResponse(appPreferences.getUserId(), "data:image/jpg;base64," + base64);
        Observable<Response<ImageUploadResponse>> otpResponse = apiInterface.uploadImage(imageUploadResponse);
        compositeDisposable.add(otpResponse
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map(result -> result)
                .subscribe(response -> {

                    if (response.isSuccessful() && (response.code() == 200 || response.code() == 201)) {
                        updateImagePath(response.body().getImagePath());
                    } else if (response.code() == 400) {
                        pDialog.dismiss();

                    }

                }, throwable -> {
                    AppUtils.showMessage(this, throwable.getMessage());
                    pDialog.dismiss();
                }));
    }

    private void updateImagePath(String imagePath) {
        ApiInterface apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        UpdateImagePathResponse imageUploadResponse = new UpdateImagePathResponse(3, appPreferences.getUserId(), imagePath, "user");
        Observable<Response<ResponseBody>> otpResponse = apiInterface.updateImagePath(imageUploadResponse);
        compositeDisposable.add(otpResponse
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map(result -> result)
                .subscribe(response -> {
                    pDialog.dismiss();
                    if (response.isSuccessful() && (response.code() == 200 || response.code() == 201)) {
                        AppUtils.showMessage(this, "Success");
                        startActivity(new Intent(this, DashBoard.class));
                    } else if (response.code() == 400) {


                    }

                }, throwable -> {
                    AppUtils.showMessage(this, throwable.getMessage());
                    pDialog.dismiss();
                }));
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            File file = ImagePicker.Companion.getFile(data);
            Bitmap bitmap = BitmapFactory.decodeFile(file.getAbsolutePath());
            binding.imgProfileImage.setImageBitmap(bitmap);
            base64 = AppUtils.encodeToBase64(bitmap, Bitmap.CompressFormat.JPEG, 60);
        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        compositeDisposable.clear();
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
