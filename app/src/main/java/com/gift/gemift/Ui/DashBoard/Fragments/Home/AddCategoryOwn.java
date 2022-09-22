package com.gift.gemift.Ui.DashBoard.Fragments.Home;


import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.cazaea.sweetalert.SweetAlertDialog;
import com.gift.gemift.Model.GetNewCategoryResponseItem;
import com.gift.gemift.Model.InsertCategory;
import com.gift.gemift.Model.InsertCategoryPersonel;
import com.gift.gemift.Model.InterestList;
import com.gift.gemift.Model.List_category_id;
import com.gift.gemift.Network.ApiClient;
import com.gift.gemift.Network.ApiInterface;
import com.gift.gemift.Storage.AppPreference.AppPreferences;
import com.gift.gemift.Ui.Adaptor.CategorySelectAdaptor;
import com.gift.gemift.Ui.DashBoard.DashBoard;
import com.gift.gemift.Utils.AppUtils;
import com.gift.gemift.databinding.AddCategoryOwnBinding;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;
import retrofit2.Response;

public class AddCategoryOwn extends AppCompatActivity implements CategorySelectAdaptor.onClickPerformed {
    private final CompositeDisposable compositeDisposable = new CompositeDisposable();
    private AddCategoryOwnBinding binding;
    private AppPreferences appPreferences;
    List<GetNewCategoryResponseItem> category = new ArrayList<>();



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = AddCategoryOwnBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());



        LoadUi();

    }

    @SuppressLint("SetTextI18n")
    private void LoadUi() {
        appPreferences = new AppPreferences(this);

        getInterestList();


        binding.nextButton.setOnClickListener(view -> {
            if(category.size()==0){
                Toast.makeText(this, "Please select category", Toast.LENGTH_SHORT).show();
            }else {
                ArrayList<List_category_id> listCategoryIdArrayList = new ArrayList<>();

                for(GetNewCategoryResponseItem interestList:category){
                    List_category_id list_category_id = new List_category_id(interestList.getWebCategoryId(), 1);
                    listCategoryIdArrayList.add(list_category_id);
                }
                insertCategory(listCategoryIdArrayList.toArray(new List_category_id[0]));
            }
        });

    }


    @SuppressLint("SetTextI18n")
    private void getInterestList() {
        SweetAlertDialog pDialog = new SweetAlertDialog(this, SweetAlertDialog.PROGRESS_TYPE);
        pDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
        pDialog.setTitleText("Loading");
        pDialog.setCancelable(false);
        pDialog.show();
        ApiInterface apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        Observable<Response<List<GetNewCategoryResponseItem>>> friendDetail = apiInterface.getNewCategoryInserted(1);
        compositeDisposable.add(friendDetail
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map(result -> result)
                .subscribe(response -> {
                    pDialog.dismiss();


                    binding.rvCategory.setAdapter(new CategorySelectAdaptor(this,this,response.body()));



                }, throwable -> {
                    pDialog.dismiss();
                    AppUtils.showMessage(AddCategoryOwn.this, throwable.getMessage());
                }));
    }

    private void insertCategory( List_category_id[] list_category_ids) {
        ProgressDialog pDialog = new ProgressDialog(this);
        pDialog.setMessage("Please Wait...");
        pDialog.show();
        ApiInterface apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        InsertCategoryPersonel insertCategory = new InsertCategoryPersonel(list_category_ids, appPreferences.getUserId(), "add_category");
        Observable<Response<ResponseBody>> addCategoryUser = apiInterface.addCategoryUserPersonnel(insertCategory);
        compositeDisposable.add(addCategoryUser
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map(result -> result)
                .subscribe(response -> {
                    pDialog.dismiss();
                    if (response.isSuccessful() && (response.code() == 200 || response.code() == 201)) {
                        startActivity(new Intent(this,AddInterestOwn.class));
                        finish();

                    } else if (response.code() == 400 || response.code() == 401 || response.code() == 500) {
                        startActivity(new Intent(this, AddInterestOwn.class));
                        finish();

                        try {
                            assert response.errorBody() != null;
                            JSONObject jsonObject = new JSONObject(response.errorBody().string());
                            String error = jsonObject.getString("Error");
                            Toast.makeText(AddCategoryOwn.this, error, Toast.LENGTH_LONG).show();
                        } catch (Exception e) {
                            Toast.makeText(AddCategoryOwn.this, e.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    } else {
                        try {
                            assert response.errorBody() != null;
                            String error = response.errorBody().toString();
                            Toast.makeText(AddCategoryOwn.this, error, Toast.LENGTH_LONG).show();
                        } catch (Exception e) {
                            Toast.makeText(AddCategoryOwn.this, e.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    }

                }, throwable -> {
                    pDialog.dismiss();
                    AppUtils.showMessage(AddCategoryOwn.this, throwable.getMessage());

                }));
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




    @Override
    public void onItemClick(boolean add, GetNewCategoryResponseItem categoryResponseItem) {
        if(add){

            category.add(categoryResponseItem);
            appPreferences.setCategory(category);
        }else {
            category.remove(categoryResponseItem);
            appPreferences.setCategory(category);

        }
        if(category.size()==0){
            binding.nextButton.setVisibility(View.GONE);
        }else {
            binding.nextButton.setVisibility(View.VISIBLE);
        }
    }
}
