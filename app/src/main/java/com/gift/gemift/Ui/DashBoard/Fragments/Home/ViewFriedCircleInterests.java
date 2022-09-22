package com.gift.gemift.Ui.DashBoard.Fragments.Home;


import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.cazaea.sweetalert.SweetAlertDialog;
import com.gift.gemift.Model.FriendCircleAddInterestResponse;
import com.gift.gemift.Model.MyCategory;
import com.gift.gemift.Model.MySubCategory;
import com.gift.gemift.Model.Subcategory;
import com.gift.gemift.Model.SubcategoryResponse;
import com.gift.gemift.Model.SubcategoryResponseNew;
import com.gift.gemift.Model.getCategoryDetail;
import com.gift.gemift.Model.getCategoryResponse;
import com.gift.gemift.Network.ApiClient;
import com.gift.gemift.Network.ApiInterface;
import com.gift.gemift.R;
import com.gift.gemift.Storage.AppPreference.AppPreferences;
import com.gift.gemift.Ui.Adaptor.CategoryTabAdaptor;
import com.gift.gemift.Ui.Adaptor.MyCategoryAdaptor;
import com.gift.gemift.Ui.Adaptor.MySubCategoryAdaptor;
import com.gift.gemift.Ui.Adaptor.SubCategoryAdaptorFriendCircleDetails;
import com.gift.gemift.Utils.AppUtils;
import com.gift.gemift.databinding.MyIntrestBinding;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Response;

public class ViewFriedCircleInterests extends AppCompatActivity {
    private final CompositeDisposable compositeDisposable = new CompositeDisposable();
    private MyIntrestBinding binding;
    private AppPreferences appPreferences;
    String FriendCircleID = "";
    private ApiInterface apiInterface;


    private SweetAlertDialog pDialog;

    List<Subcategory> Subcategory = new ArrayList<>();


    String selectedCategory  ="";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = MyIntrestBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        FriendCircleID = getIntent().getStringExtra("friendCircleID");
        LoadUi();
    }

    @SuppressLint("SetTextI18n")
    private void LoadUi() {
        binding.txtTitle.setText("Circle Interests");
        apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        pDialog = new SweetAlertDialog(this, SweetAlertDialog.PROGRESS_TYPE);


        binding.actionBack.setOnClickListener(view -> finish());
        appPreferences = new AppPreferences(this);
        Glide.with(this)
                .load(R.raw.gif_blink)
                .into(binding.progressImage);



        binding.llAddMore.setOnClickListener(view -> startActivity(new Intent(this, AddInterestOwn.class)));

    }


    private void getSubInterestList() {
        binding.rvSubCategory.setVisibility(View.GONE);


        ApiInterface apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        Observable<Response<FriendCircleAddInterestResponse>> friendDetail = apiInterface.getNewInterestInserted(3,"",FriendCircleID,"","","1","100");
        compositeDisposable.add(friendDetail
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map(result -> result)
                .subscribe(response -> {

                    binding.progress.setVisibility(View.GONE);

                    if(response.body().getSubcategories().size()!=0){
                        binding.llNoData.setVisibility(View.GONE);
                        binding.rvSubCategory.setVisibility(View.VISIBLE);
                        binding.rvSubCategory.setAdapter(new CategoryTabAdaptor(this, response.body().getSubcategories(),response.body().getSubcategories().size()));

                    }else {
                        binding.llNoData.setVisibility(View.VISIBLE);
                        binding.rvSubCategory.setVisibility(View.GONE);
                    }




                }, throwable -> {
                    binding.progress.setVisibility(View.GONE);
                    binding.llNoData.setVisibility(View.VISIBLE);

                    AppUtils.showMessage(ViewFriedCircleInterests.this, throwable.getMessage());
                }));
    }




    @Override
    protected void onResume() {
        super.onResume();

        getSubInterestList();

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
