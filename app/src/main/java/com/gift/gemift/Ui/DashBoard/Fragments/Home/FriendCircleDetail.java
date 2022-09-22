package com.gift.gemift.Ui.DashBoard.Fragments.Home;

import static com.gift.gemift.Utils.Constant.AGE;
import static com.gift.gemift.Utils.Constant.CIRCLE_ID;
import static com.gift.gemift.Utils.Constant.FRIEND_CIRCLE_ID;
import static com.gift.gemift.Utils.Constant.FRIEND_CIRCLE_IMAGE;
import static com.gift.gemift.Utils.Constant.FRIEND_CIRCLE_NAME;
import static com.gift.gemift.Utils.Constant.FRIEND_CIRCLE_TITLE;
import static com.gift.gemift.Utils.Constant.SECRET_FIRST_NAME;
import static com.gift.gemift.Utils.Constant.SECRET_LAST_NAME;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.cazaea.sweetalert.SweetAlertDialog;
import com.gift.gemift.Model.FriendCircleAddInterestResponse;
import com.gift.gemift.Model.FriendCircleResponse;
import com.gift.gemift.Model.GetOccasionDetailResponse;
import com.gift.gemift.Model.GetOccasionResponse;
import com.gift.gemift.Model.ImageUploadResponse;
import com.gift.gemift.Model.SubcategoryResponseNew;
import com.gift.gemift.Model.UpdateImagePathResponse;
import com.gift.gemift.Network.ApiClient;
import com.gift.gemift.Network.ApiInterface;
import com.gift.gemift.R;
import com.gift.gemift.Storage.AppPreference.AppPreferences;
import com.gift.gemift.Storage.DB.Entity.FriendListEntity;
import com.gift.gemift.Ui.Adaptor.CategoryTabAdaptor;
import com.gift.gemift.Ui.Adaptor.ContributorListAdaptor;
import com.gift.gemift.Ui.Adaptor.OccasionListAdaptor;
import com.gift.gemift.Utils.AppUtils;
import com.gift.gemift.Utils.FilterHelper;
import com.gift.gemift.databinding.FriendCircleDetailViewBinding;
import com.github.dhaval2404.imagepicker.ImagePicker;

import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;
import retrofit2.Response;

public class FriendCircleDetail extends AppCompatActivity {

    private final CompositeDisposable compositeDisposable = new CompositeDisposable();
    private final ArrayList<FriendListEntity> contributorList = new ArrayList<>();
    private final HashMap<String, String> interestIdHashMap = new HashMap<>();
    private FriendCircleDetailViewBinding binding;
    private SweetAlertDialog pDialog;
    private ApiInterface apiInterface;
    private AppPreferences appPreferences;
    private String friendCircleId, friendCircleName, base64, friendAge, SecretFriendName, friendCircleImage, SecretFriendFirstName, SecretFriendLastName;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = FriendCircleDetailViewBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.appbar.toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        final Drawable upArrow = ResourcesCompat.getDrawable(getResources(), R.drawable.ic_baseline_arrow, null);
        FilterHelper.setColorFilter(upArrow, getResources().getColor(R.color.white), FilterHelper.Mode.SRC_ATOP);
        getSupportActionBar().setHomeAsUpIndicator(upArrow);

//
        LoadUi();


    }

    @SuppressLint("SetTextI18n")
    private void LoadUi() {
        Intent dataIntent = getIntent();
        apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        appPreferences = new AppPreferences(this);
        friendCircleId = dataIntent.getStringExtra(FRIEND_CIRCLE_ID);
        friendCircleName = dataIntent.getStringExtra(FRIEND_CIRCLE_NAME);
        friendAge = dataIntent.getStringExtra(AGE);

//        binding.txtCircleOccasionName.setText(dataIntent.getStringExtra(FRIEND_CIRCLE_NAME) + "'s Special Occasion");
        binding.txtCircleName.setText(dataIntent.getStringExtra(FRIEND_CIRCLE_NAME));
        pDialog = new SweetAlertDialog(this, SweetAlertDialog.PROGRESS_TYPE);
        binding.appbar.txtTitle.setText(friendCircleName);
        binding.txtInterest.setText("Interest");

        binding.imgAddContributor.setOnClickListener(view -> {
            Intent intent = new Intent(this, AddContributor.class);
            intent.putExtra(FRIEND_CIRCLE_ID, friendCircleId);
            startActivity(intent);
        });

        binding.imgAddInterest.setOnClickListener(v -> {
            Intent intent = new Intent(this, AddInterest.class);
            intent.putExtra("isList",true);
            intent.putExtra(CIRCLE_ID, friendCircleId);
            intent.putExtra(FRIEND_CIRCLE_NAME, friendCircleName);
            intent.putExtra(SECRET_FIRST_NAME, SecretFriendFirstName);
            intent.putExtra(SECRET_LAST_NAME, SecretFriendLastName);
            startActivity(intent);
        });

        binding.imgAddOccasion.setOnClickListener(v -> {
            Intent intent = new Intent(this, AddOccasionList.class);
            intent.putExtra("isList", true);
            intent.putExtra(CIRCLE_ID, friendCircleId);
            intent.putExtra(FRIEND_CIRCLE_TITLE, friendCircleName);
            intent.putExtra(FRIEND_CIRCLE_IMAGE, friendCircleImage);
            startActivity(intent);
        });

        binding.imgEdit.setOnClickListener(v -> {
            ImagePicker.Companion.with(this)
                    .crop()
                    .saveDir(getExternalFilesDir("images"))
                    .compress(512)
                    .galleryOnly()
                    .maxResultSize(1080, 1080)
                    .start();
        });


        binding.llViewMore.setOnClickListener(view -> {
            startActivity(new Intent(this,ViewFriedCircleInterests.class).putExtra("friendCircleID", friendCircleId));
        });
//        binding.btnImgUpload.setOnClickListener(v -> {
//            imageUpload();
//        });
    }

    @SuppressLint("SetTextI18n")
    private void getFriendCircleDetail(String friendId) {
        pDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
        pDialog.setTitleText("Loading");
        pDialog.setCancelable(false);
        pDialog.show();
        ApiInterface apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        Observable<Response<FriendCircleResponse>> friendDetail = apiInterface.getFriendCircleDetail(friendId, 1);
        compositeDisposable.add(friendDetail
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map(result -> result)
                .subscribe(response -> {
                    if (response.isSuccessful() && (response.code() == 200 || response.code() == 201)) {
                        contributorList.clear();
                        for (FriendListEntity listEntity : response.body().getFriend_circle_id()) {
                            if (listEntity.getRelationship().equals("SECRET_FRIEND")) {
                                binding.txtCircleCreatorName.setText(listEntity.getFirst_name() + " " + listEntity.getLast_name());
                                if (listEntity.getImage_url() != null) {
                                    RequestOptions myOptions = new RequestOptions()
                                            .centerCrop();
                                    Glide.with(this)
                                            .asBitmap()
                                            .apply(myOptions)
                                            .load(listEntity.getImage_url())
                                            .into(binding.friendCircleImg.profilePic);
                                }
                                SecretFriendName = listEntity.getSecret_first_name() + " " + listEntity.getSecret_last_name();
                                SecretFriendFirstName = listEntity.getSecret_first_name();
                                SecretFriendLastName = listEntity.getSecret_last_name();
                                friendCircleImage = listEntity.getImage_url();
                            } else if (listEntity.getRelationship().equals("CONTRIBUTOR")) {
                                contributorList.add(listEntity);
                            }
                        }
                        loadContributor();
                        getOccasionList();
                    } else if (response.code() == 400) {
                        getOccasionList();
                        try {
                            JSONObject jsonObject = new JSONObject(response.errorBody().string());
                            String error = jsonObject.getString("Error");
                            Toast.makeText(this, error, Toast.LENGTH_LONG).show();
                        } catch (Exception e) {
                            Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    } else {
                        getOccasionList();
                    }
                }, throwable -> {
                    pDialog.dismiss();
                    AppUtils.showMessage(this, throwable.getMessage());
                }));
    }

    private void getOccasionList() {
        Observable<Response<GetOccasionResponse>> friendDetail = apiInterface.getOccasionListByCircleId(1, friendCircleId, appPreferences.getUserId());
        compositeDisposable.add(friendDetail
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map(result -> result)
                .subscribe(response -> {
                    if (response.isSuccessful() && (response.code() == 200 || response.code() == 201)) {
                        if (response.body().getOccasions().length > 0) {


                            loadOccasionList(response.body().getOccasions());

                        }
                        getInterests();
                    } else if (response.code() == 400) {
                        getInterests();
                        try {
                            JSONObject jsonObject = new JSONObject(response.errorBody().string());
                            String error = jsonObject.getString("Error");
                            Toast.makeText(this, error, Toast.LENGTH_LONG).show();
                        } catch (Exception e) {
                            Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    } else {
                        getInterests();
                    }
                }, throwable -> {
                    pDialog.dismiss();
                    AppUtils.showMessage(this, throwable.getMessage());
                }));

    }



    @SuppressLint({"SetTextI18n", "NotifyDataSetChanged"})
    private void loadOccasionList(GetOccasionDetailResponse[] occasions) {
        binding.txtCircleOccasionName.setText("Upcoming Occasions");
        OccasionListAdaptor occasionListAdaptor = new OccasionListAdaptor(occasions, compositeDisposable, friendCircleId, this, friendAge, contributorList, SecretFriendName, interestIdHashMap);
        binding.recyleOccasionList.setAdapter(occasionListAdaptor);
        occasionListAdaptor.notifyDataSetChanged();
    }

    @SuppressLint({"SetTextI18n", "NotifyDataSetChanged"})
    private void loadContributor() {
        binding.txtContributorCount.setText("Friends in this circle (" + contributorList.size() + ")");
        ContributorListAdaptor contributorListAdaptor = new ContributorListAdaptor(contributorList, this);
        binding.recyleFriendLits.setAdapter(contributorListAdaptor);
        contributorListAdaptor.notifyDataSetChanged();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            File file = ImagePicker.Companion.getFile(data);
            Bitmap bitmap = BitmapFactory.decodeFile(file.getAbsolutePath());
            binding.friendCircleImg.profilePic.setImageBitmap(bitmap);
            base64 = AppUtils.encodeToBase64(bitmap, Bitmap.CompressFormat.JPEG, 60);
//            binding.btnImgUpload.setVisibility(View.VISIBLE);
            imageUpload();
        }

    }

    private void imageUpload() {
        pDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
        pDialog.setTitleText("Loading");
        pDialog.setCancelable(false);
        pDialog.show();
        ApiInterface apiInterface = ApiClient.getApiClientImageUpload().create(ApiInterface.class);

        ImageUploadResponse imageUploadResponse = new ImageUploadResponse(friendCircleId, "data:image/jpg;base64," + base64);
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
                    } else {
                        pDialog.dismiss();
                    }

                }, throwable -> {
                    AppUtils.showMessage(this, throwable.getMessage());
                    pDialog.dismiss();
                }));
    }

    private void updateImagePath(String imagePath) {
        UpdateImagePathResponse imageUploadResponse = new UpdateImagePathResponse(3, friendCircleId, imagePath, "friend_circle");
        Observable<Response<ResponseBody>> otpResponse = apiInterface.updateImagePath(imageUploadResponse);
        compositeDisposable.add(otpResponse
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map(result -> result)
                .subscribe(response -> {
                    pDialog.dismiss();
                    if (response.isSuccessful() && (response.code() == 200 || response.code() == 201)) {
                        AppUtils.showMessage(this, "Circle Image Updated Successfully");
//                        binding.btnImgUpload.setVisibility(View.GONE);
                    } else if (response.code() == 400) {
                        pDialog.dismiss();
                    } else {
                        pDialog.dismiss();
                    }

                }, throwable -> {
                    AppUtils.showMessage(this, throwable.getMessage());
                    pDialog.dismiss();
                }));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        compositeDisposable.clear();
    }

    @Override
    protected void onResume() {
        super.onResume();
        getFriendCircleDetail(friendCircleId);

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





    private void getInterests() {
        binding.rvSubCategory.setVisibility(View.GONE);

//        binding.progress.setProgress(0);

        ApiInterface apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        Observable<Response<FriendCircleAddInterestResponse>> friendDetail = apiInterface.getInterestInsertedFriendCircle(3,friendCircleId);
        compositeDisposable.add(friendDetail
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map(result -> result)
                .subscribe(response -> {
                    pDialog.dismiss();

                    if(response.body().getSubcategories().size()!=0){
                        binding.rvSubCategory.setVisibility(View.VISIBLE);
                        binding.llNoData.setVisibility(View.GONE);

                        if(response.body().getSubcategories().size()>3){
                            binding.llViewMore.setVisibility(View.VISIBLE);
                            binding.rvSubCategory.setAdapter(new CategoryTabAdaptor(this, response.body().getSubcategories(),3));

                        }else {
                            binding.llViewMore.setVisibility(View.GONE);
                            binding.rvSubCategory.setAdapter(new CategoryTabAdaptor(this, response.body().getSubcategories(),response.body().getSubcategories().size()));

                        }

                    }else {
                        binding.rvSubCategory.setVisibility(View.GONE);
                        binding.llViewMore.setVisibility(View.GONE);
                        binding.llNoData.setVisibility(View.VISIBLE);
                    }




                }, throwable -> {

                    pDialog.dismiss();
                    AppUtils.showMessage(this, throwable.getMessage());
                }));
    }


    class IgnoreCaseComparator implements Comparator<GetOccasionDetailResponse> {


        @Override
        public int compare(GetOccasionDetailResponse ob1, GetOccasionDetailResponse ob2) {

            int d1 =ob1.getDays_left();

            int d2 = ob2.getDays_left();

            return Integer.compare(d1, d2);
        }
    }
}
