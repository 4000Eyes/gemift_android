package com.gift.gemift.Ui.DashBoard.Fragments.Home;

import static com.gift.gemift.Utils.Constant.CIRCLE_ID;
import static com.gift.gemift.Utils.Constant.FRIEND_CIRCLE_NAME;
import static com.gift.gemift.Utils.Constant.SECRET_FIRST_NAME;
import static com.gift.gemift.Utils.Constant.SECRET_LAST_NAME;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;
import androidx.recyclerview.widget.GridLayoutManager;

import com.cazaea.sweetalert.SweetAlertDialog;
import com.gift.gemift.Model.FriendCircleListResponse;
import com.gift.gemift.Model.FriendDataModel;
import com.gift.gemift.Model.InsertCategory;
import com.gift.gemift.Model.InsertSubCategoryResponse;
import com.gift.gemift.Model.InterestList;
import com.gift.gemift.Model.List_SubCategory_Id;
import com.gift.gemift.Model.List_category_id;
import com.gift.gemift.Model.SecretFriendAgeResponse;
import com.gift.gemift.Model.Subcategory;
import com.gift.gemift.Model.SubcategoryResponse;
import com.gift.gemift.Model.SubcategoryResponseNew;
import com.gift.gemift.Network.ApiClient;
import com.gift.gemift.Network.ApiInterface;
import com.gift.gemift.R;
import com.gift.gemift.Storage.AppPreference.AppPreferences;
import com.gift.gemift.Ui.Adaptor.FriendCircleCategorySelectAdaptor;
import com.gift.gemift.Ui.Adaptor.FriendCircleSubCategoryAdaptor;
import com.gift.gemift.Ui.DashBoard.DashBoard;
import com.gift.gemift.Utils.AppUtils;
import com.gift.gemift.Utils.FilterHelper;
import com.gift.gemift.databinding.AddInterestBinding;
import com.google.android.material.chip.Chip;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;
import retrofit2.Response;

public class AddInterest extends AppCompatActivity implements FriendCircleCategorySelectAdaptor.onClickPerformed, FriendCircleSubCategoryAdaptor.onClickPerformed {
    private final CompositeDisposable compositeDisposable = new CompositeDisposable();
    private final HashMap<String, String> interestIdHashMap = new HashMap<>();
    private final HashMap<String, String> subInterestIdHashMap = new HashMap<>();
    private AddInterestBinding binding;
    private AppPreferences appPreferences;
    private boolean isInterestSubmitted = false;
    private String secretFriendAge, SecretFriendFirstName, SecretFriendLastName;
    String circle_id;
    String circle_Name;
    private boolean isList ;
    private List<InterestList> interestCategory  = new ArrayList<>();
    private List<Subcategory> interestSubCategory  = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = AddInterestBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.appbar.toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        final Drawable upArrow = ResourcesCompat.getDrawable(getResources(), R.drawable.ic_baseline_arrow, null);
        FilterHelper.setColorFilter(upArrow, getResources().getColor(R.color.white), FilterHelper.Mode.SRC_ATOP);
        getSupportActionBar().setHomeAsUpIndicator(upArrow);

        binding.appbar.txtTitle.setText("Add Interest");

        LoadUi();
    }

    @SuppressLint("SetTextI18n")
    private void LoadUi() {
        appPreferences = new AppPreferences(this);
        Intent dataIntent = getIntent();

        isList = dataIntent.getBooleanExtra("isList",false);

        if(isList){
            binding.llSpinner.setVisibility(View.GONE);
            binding.nestedScrollView.setVisibility(View.VISIBLE);
            circle_id = dataIntent.getStringExtra(CIRCLE_ID);
            circle_Name = dataIntent.getStringExtra(FRIEND_CIRCLE_NAME);
            SecretFriendFirstName = dataIntent.getStringExtra(SECRET_FIRST_NAME);
            SecretFriendLastName = dataIntent.getStringExtra(SECRET_LAST_NAME);
            binding.txtInterest.setText(circle_Name + " interested on?");
            getFriendCircleAge(circle_id);
        }else {
          getFriendList();
        }

    }

    private void getFriendList() {
        SweetAlertDialog pDialog = new SweetAlertDialog(this, SweetAlertDialog.PROGRESS_TYPE);

        pDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
        pDialog.setTitleText("Loading");
        pDialog.setCancelable(false);
        pDialog.show();
        ApiInterface apiInterface = ApiClient.getApiClient().create(ApiInterface.class);

        Observable<Response<FriendDataModel>> friendDetail = apiInterface.getFriendCircleList(appPreferences.getUserId(), 2);
        compositeDisposable.add(friendDetail
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map(result -> result)
                .subscribe(response -> {
                    pDialog.dismiss();

                    if (response.isSuccessful() && (response.code() == 200 || response.code() == 201)) {

                        appPreferences.setFriendCircleSize(response.body().getData().length);

                        List<String> circleName = new ArrayList<>();
                        for(FriendCircleListResponse friendCircleListResponse : response.body().getData()){
                            circleName.add(friendCircleListResponse.getFriend_circle_name());
                        }

                        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,  android.R.layout.simple_spinner_dropdown_item, circleName);
                        adapter.setDropDownViewResource( android.R.layout.simple_spinner_dropdown_item);
                        binding.spinner.setAdapter(adapter);

                        binding.spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                FriendCircleListResponse friendCircleListResponse= response.body().getData()[position];
                                circle_id = friendCircleListResponse.getFriend_circle_id();
                                circle_Name = friendCircleListResponse.getFriend_circle_name();
                                SecretFriendFirstName = friendCircleListResponse.getSecret_first_name();
                                SecretFriendLastName = friendCircleListResponse.getSecret_last_name();
                                binding.txtInterest.setText(circle_Name + " interested on?");
                                getFriendCircleAge(circle_id);

                                binding.nestedScrollView.setVisibility(View.VISIBLE);

                            }

                            @Override
                            public void onNothingSelected(AdapterView<?> parent) {

                            }
                        });


                    } else if (response.code() == 400) {
                        pDialog.dismiss();
                        try {
                            JSONObject jsonObject = new JSONObject(response.errorBody().string());
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


    private void getFriendCircleAge(String circle_id) {
        SweetAlertDialog pDialog = new SweetAlertDialog(this, SweetAlertDialog.PROGRESS_TYPE);
        pDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
        pDialog.setTitleText("Loading");
        pDialog.setCancelable(false);
        pDialog.show();
        ApiInterface apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        Observable<Response<SecretFriendAgeResponse>> friendCircleAge = apiInterface.getFriendCircleAge(1, circle_id);
        compositeDisposable.add(friendCircleAge
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map(result -> result)
                .subscribe(response -> {
                    pDialog.dismiss();
                    if (response.isSuccessful() && (response.code() == 200 || response.code() == 201)) {
                        if (response.body().getStatus()[0].getAge() != null) {
                            secretFriendAge = response.body().getStatus()[0].getAge();
                            getSubInterestList(circle_id);
                        } else {
                            Intent intent = new Intent(this, SecretFriendAgeUpdate.class);
                            intent.putExtra(CIRCLE_ID, circle_id);
                            intent.putExtra(SECRET_FIRST_NAME, response.body().getStatus()[0].getSecret_first_name());
                            intent.putExtra(SECRET_LAST_NAME, response.body().getStatus()[0].getSecret_last_name());
                            startActivity(intent);
                        }

                    } else if (response.code() == 400 || response.code() == 401) {
                        try {
                            JSONObject jsonObject = new JSONObject(response.errorBody().string());
                            String error = jsonObject.getString("Error");
                            Toast.makeText(AddInterest.this, error, Toast.LENGTH_LONG).show();
                        } catch (Exception e) {
                            Toast.makeText(AddInterest.this, e.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    } else {
                        try {
                            String error = response.errorBody().toString();
                            Toast.makeText(AddInterest.this, error, Toast.LENGTH_LONG).show();
                        } catch (Exception e) {
                            Toast.makeText(AddInterest.this, e.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    }

                }, throwable -> {
                    pDialog.dismiss();
                    AppUtils.showMessage(AddInterest.this, throwable.getMessage());
                }));
    }


    private void insertSubInterest(String circle_id, List_SubCategory_Id[] list_category_ids) {
        ApiInterface apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        InsertSubCategoryResponse insertCategory = new InsertSubCategoryResponse(list_category_ids, appPreferences.getUserId(), circle_id, 1);
        Observable<Response<ResponseBody>> addCategoryUser = apiInterface.addSubCategoryUser(insertCategory);
        compositeDisposable.add(addCategoryUser
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map(result -> result)
                .subscribe());
    }

    private void getSubInterestList(String circleId) {
        SweetAlertDialog pDialog = new SweetAlertDialog(this, SweetAlertDialog.PROGRESS_TYPE);
        pDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
        pDialog.setTitleText("Loading");
        pDialog.setCancelable(false);
        pDialog.show();
        ApiInterface apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        Observable<Response<SubcategoryResponseNew>> friendDetail = apiInterface.getNewSubCategoryInserted(10, "","","","","1","100");
        compositeDisposable.add(friendDetail
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map(result -> result)
                .subscribe(response -> {
                    pDialog.dismiss();
                    if (response.isSuccessful() && (response.code() == 200 || response.code() == 201)) {
                        if (response.body().getSubcategory().size() > 0) {
//                            addSubInterestChip(response.body().getSubcategory());
                            binding.gvCategories.setLayoutManager(new GridLayoutManager(this,2));
                            binding.gvCategories.setAdapter(new FriendCircleSubCategoryAdaptor(this,this, response.body().getSubcategory()));
                        } else {
                            finish();
                        }
                    } else if (response.code() == 400 || response.code() == 401) {
                        try {
                            JSONObject jsonObject = new JSONObject(response.errorBody().string());
                            String error = jsonObject.getString("Error");
                            Toast.makeText(AddInterest.this, error, Toast.LENGTH_LONG).show();
                        } catch (Exception e) {
                            Toast.makeText(AddInterest.this, e.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    } else {
                        try {
                            String error = response.errorBody().toString();
                            Toast.makeText(AddInterest.this, error, Toast.LENGTH_LONG).show();
                        } catch (Exception e) {
                            Toast.makeText(AddInterest.this, e.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    }

                }, throwable -> {
                    pDialog.dismiss();
                    AppUtils.showMessage(AddInterest.this, throwable.getMessage());
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
    public void onItemClick(boolean addStatus, InterestList categoryResponseItem) {
        if(addStatus){
            interestCategory.add(categoryResponseItem);
        }else {
            interestCategory.remove(categoryResponseItem);
        }

    }

    @Override
    public void onItemClick(boolean status, Subcategory categoryResponseItem) {
        ArrayList<List_SubCategory_Id> listSubCategoryIdArrayList = new ArrayList<>();
        String id = categoryResponseItem.getWeb_subcategory_id();
        List_SubCategory_Id list_subCategory_id = new List_SubCategory_Id(id, 1);
        listSubCategoryIdArrayList.add(list_subCategory_id);
        insertSubInterest(circle_id, listSubCategoryIdArrayList.toArray(new List_SubCategory_Id[0]));

    }
}
