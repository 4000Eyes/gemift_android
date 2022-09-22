package com.gift.gemift.Ui.DashBoard.Fragments.Home;


import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.gift.gemift.Model.InsertSubCategoryPersonnel;
import com.gift.gemift.Model.List_SubCategory_Id;
import com.gift.gemift.Model.Subcategory;
import com.gift.gemift.Model.SubcategoryResponseNew;
import com.gift.gemift.Network.ApiClient;
import com.gift.gemift.Network.ApiInterface;
import com.gift.gemift.R;
import com.gift.gemift.Storage.AppPreference.AppPreferences;
import com.gift.gemift.Ui.Adaptor.MyInterestAddAdapter;
import com.gift.gemift.Utils.AppUtils;
import com.gift.gemift.databinding.AddOwnInterestBinding;


import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;
import retrofit2.Response;

public class AddInterestOwn extends AppCompatActivity implements MyInterestAddAdapter.onClickPerformed {
    private final CompositeDisposable compositeDisposable = new CompositeDisposable();
    private AddOwnInterestBinding binding;
    private AppPreferences appPreferences;

    int pageCount =1;

    List<Subcategory> Subcategory = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = AddOwnInterestBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        LoadUi();
    }

    @SuppressLint("SetTextI18n")
    private void LoadUi() {
        appPreferences = new AppPreferences(this);
        Glide.with(this)
                .load(R.raw.gif_blink)
                .into(binding.progressImage);

        binding.rvSubCategory.setVisibility(View.VISIBLE);
        binding.rvSubCategory.setAdapter(new MyInterestAddAdapter(this,this, Subcategory));

        getSubInterestList();

        binding.actionDone.setOnClickListener(view -> finish());
        binding.actionHome.setOnClickListener(view -> finish());
    }






    private void getSubInterestList() {


        binding.progress.setVisibility(View.VISIBLE);

        ApiInterface apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        Observable<Response<SubcategoryResponseNew>> friendDetail = apiInterface.getNewSubCategoryInserted(10,appPreferences.getUserId(),"","","", String.valueOf(pageCount),"100");
        compositeDisposable.add(friendDetail
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map(result -> result)
                .subscribe(response -> {

                    binding.progress.setVisibility(View.GONE);
                    if(response.code() ==200){
                        if(response.body().getSubcategory().size()!=0){
                            pageCount++;
                            binding.llNoData.setVisibility(View.GONE);
                            Subcategory.addAll(response.body().getSubcategory());
                        }else {
                            binding.llNoData.setVisibility(View.VISIBLE);
                            binding.rvSubCategory.setVisibility(View.GONE);
                        }

                    }else if (response.code() == 400 || response.code() == 401) {
                        try {
                            JSONObject jsonObject = new JSONObject(response.errorBody().string());
                            String error = jsonObject.getString("Error");
                            Toast.makeText(AddInterestOwn.this, error, Toast.LENGTH_LONG).show();
                        } catch (Exception e) {
                            Toast.makeText(AddInterestOwn.this, e.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    } else {
                        try {
                            String error = response.errorBody().toString();
                            Toast.makeText(AddInterestOwn.this, error, Toast.LENGTH_LONG).show();
                        } catch (Exception e) {
                            Toast.makeText(AddInterestOwn.this, e.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    }


                }, throwable -> {

                    binding.progress.setVisibility(View.GONE);
                    binding.llNoData.setVisibility(View.VISIBLE);

                    AppUtils.showMessage(AddInterestOwn.this, throwable.getMessage());
                }));
    }




    private void insertSubInterest(List<List_SubCategory_Id> list_category_ids) {
        ApiInterface apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        InsertSubCategoryPersonnel insert = new InsertSubCategoryPersonnel(list_category_ids, appPreferences.getUserId() , "add_subcategory");
        Observable<Response<ResponseBody>> addCategoryUser = apiInterface.addSubCategoryUserPersonnel(insert);
        compositeDisposable.add(addCategoryUser
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map(result -> result)
                .subscribe());
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
    public void onItemClick( Subcategory categoryResponseItem) {

        ArrayList<List_SubCategory_Id> listSubCategoryIdArrayList = new ArrayList<>();

        String id = categoryResponseItem.getWeb_subcategory_id();
        List_SubCategory_Id list_subCategory_id = new List_SubCategory_Id(id, 1);
        listSubCategoryIdArrayList.add(list_subCategory_id);

        insertSubInterest(listSubCategoryIdArrayList);

    }
}
