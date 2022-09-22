package com.gift.gemift.Ui.DashBoard.Fragments.Home;

import static com.gift.gemift.Utils.Constant.CONTRIBUTOR_LIST;
import static com.gift.gemift.Utils.Constant.FRIEND_CIRCLE_ID;
import static com.gift.gemift.Utils.Constant.OCCASION_DATE;
import static com.gift.gemift.Utils.Constant.OCCASION_ID;
import static com.gift.gemift.Utils.Constant.OCCASION_NAME;
import static com.gift.gemift.Utils.Constant.SECRET_FIRST_NAME;

import android.annotation.SuppressLint;
import android.content.Intent;
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
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.cazaea.sweetalert.SweetAlertDialog;
import com.gift.gemift.Model.InitialFundRequestModel;
import com.gift.gemift.Model.InitiateFundCreator;
import com.gift.gemift.Model.InitiateFundFriends;
import com.gift.gemift.Model.InitiateFundModel;
import com.gift.gemift.Model.InsertVoteProduct;
import com.gift.gemift.Model.ProductVote;
import com.gift.gemift.Model.VotedProductResponse;
import com.gift.gemift.Model.VotedRoot;
import com.gift.gemift.Model.Wallet.TransactionDetailResponse;
import com.gift.gemift.Model.Wallet.TransactionItem;
import com.gift.gemift.Network.ApiClient;
import com.gift.gemift.Network.ApiInterface;
import com.gift.gemift.R;
import com.gift.gemift.Storage.AppPreference.AppPreferences;
import com.gift.gemift.Storage.DB.Entity.FriendListEntity;
import com.gift.gemift.Storage.DB.Entity.UserDetailEntity;
import com.gift.gemift.Storage.DB.ViewModel.UserDetailViewModel;
import com.gift.gemift.Ui.Adaptor.ContributorListAdaptor;
import com.gift.gemift.Ui.Adaptor.SelectedProductAdapter;
import com.gift.gemift.Ui.DashBoard.DashBoard;
import com.gift.gemift.Ui.DashBoard.Fragments.Wallet.TransactionConfirm;
import com.gift.gemift.Ui.Dialogs.InitiateTeamConfirmDialog;
import com.gift.gemift.Utils.AppUtils;
import com.gift.gemift.Utils.FilterHelper;
import com.gift.gemift.databinding.OccasionDetailsBinding;

import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.TimeZone;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;
import retrofit2.Response;

public class OccasionDetails extends AppCompatActivity {
    OccasionDetailsBinding binding;
    ApiInterface apiInterface;
    String year, occasionId, occasionDate, friendCircleId,occasionName;
    CompositeDisposable compositeDisposable = new CompositeDisposable();
    private AppPreferences appPreferences;
    private ArrayList<FriendListEntity> contributorList = new ArrayList<>();
    private UserDetailViewModel userDetailViewModel;
    private UserDetailEntity[] userDetailDetail;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = OccasionDetailsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.appbar.toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        final Drawable upArrow = ResourcesCompat.getDrawable(getResources(), R.drawable.ic_baseline_arrow, null);
        FilterHelper.setColorFilter(upArrow, getResources().getColor(R.color.white), FilterHelper.Mode.SRC_ATOP);
        getSupportActionBar().setHomeAsUpIndicator(upArrow);

        LoadUI();

    }

    @SuppressLint("SetTextI18n")
    private void LoadUI() {
        binding.appbar.imgEdit.setVisibility(View.VISIBLE);
        Intent dataIntent = getIntent();
        apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        appPreferences = new AppPreferences(this);
        friendCircleId = dataIntent.getStringExtra(FRIEND_CIRCLE_ID);
        String secretfriendname = dataIntent.getStringExtra(SECRET_FIRST_NAME);
        occasionId = dataIntent.getStringExtra(OCCASION_ID);
        occasionName = dataIntent.getStringExtra(OCCASION_NAME);
        occasionDate = dataIntent.getStringExtra(OCCASION_DATE);
        binding.appbar.txtTitle.setText(secretfriendname + "'s " + occasionName);
        contributorList = (ArrayList<FriendListEntity>) getIntent().getSerializableExtra(CONTRIBUTOR_LIST);
        binding.tvFriendName.setText(secretfriendname);
        binding.tvOccasionname.setText(occasionName);
        binding.tvDate.setText(occasionDate);
        if (occasionDate != null) {
            String[] date = occasionDate.split("/");
            year = date[2];
        } else {
            year = "";
        }
//        getAllSelectedProducts("95b38dd9-bdcf-40d6-8a69-4ed50cce4e86","birthday","2021");
        userDetailViewModel = new ViewModelProvider(this).get(UserDetailViewModel.class);
        getAllSelectedProducts(friendCircleId, occasionName, year);


        if(contributorList.size()==0){
            binding.cardOccasionContributor.setVisibility(View.GONE);
        }else {
            binding.cardOccasionContributor.setVisibility(View.VISIBLE);

            loadContributor();
        }

        compositeDisposable.add(userDetailViewModel.
                getUserDetail()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(userDetailEntities -> {
                    if (userDetailEntities != null) {
                        if (userDetailEntities.length > 0) {
                            userDetailDetail = userDetailEntities;
                        }
                    }
                }));


        binding.appbar.imgEdit.setOnClickListener(view -> {


            Intent i = new Intent(this, UpdateOccasionActivity.class);
            i.putExtra(FRIEND_CIRCLE_ID,friendCircleId);
            i.putExtra(SECRET_FIRST_NAME,secretfriendname);
            i.putExtra(OCCASION_ID, occasionId);
            i.putExtra(OCCASION_NAME, occasionName);
            i.putExtra(CONTRIBUTOR_LIST, contributorList);
            i.putExtra(OCCASION_DATE, occasionDate);
            startActivity(i);

        });

    }

    @SuppressLint({"SetTextI18n", "NotifyDataSetChanged"})
    private void loadContributor() {
        binding.txtContributorCount.setText("Friends in this circle (" + contributorList.size() + ")");
        ContributorListAdaptor contributorListAdaptor = new ContributorListAdaptor(contributorList, this);
        binding.recyleFriendLits.setAdapter(contributorListAdaptor);
        contributorListAdaptor.notifyDataSetChanged();
    }

    private void getAllSelectedProducts(String friendCircleId, String occasionName, String OccYear) {
        SweetAlertDialog pDialog = new SweetAlertDialog(OccasionDetails.this, SweetAlertDialog.PROGRESS_TYPE);
        pDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
        pDialog.setTitleText("Loading");
        pDialog.setCancelable(false);
        pDialog.show();
        ApiInterface apiInterface = ApiClient.getApiClient().create(ApiInterface.class);

        Observable<Response<VotedRoot>> products = apiInterface.getVotedProducts(5, friendCircleId, occasionName, OccYear);
        compositeDisposable.add(products
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map(result -> result)
                .subscribe(response -> {
                    pDialog.dismiss();
                    if (response.isSuccessful() && (response.code() == 200 || response.code() == 201)) {

                        ArrayList<VotedProductResponse> datum = response.body().getData();
                        if (datum.size() > 0) {
                            LoadRvProducts(datum);
                        } else {
                            Toast.makeText(this, "No products selected for this occasion", Toast.LENGTH_LONG).show();
                        }

                    } else if (response.code() == 400) {
                        try {
                            JSONObject jsonObject = new JSONObject(response.errorBody().string());
                            String error = jsonObject.getString("Error");
                            Toast.makeText(this, error, Toast.LENGTH_SHORT).show();
                        } catch (Exception e) {
                            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(this, "something went wrong", Toast.LENGTH_LONG).show();
                    }
                }, throwable -> {
                    pDialog.dismiss();
                    AppUtils.showMessage(this, throwable.getMessage());
                }));
    }

    @SuppressLint("SetTextI18n")
    private void LoadRvProducts(ArrayList<VotedProductResponse> datum) {
        binding.txtSelectedproduct.setText("Product selected (" + datum.size() + ")");
        binding.rvProductlist.setLayoutManager(new LinearLayoutManager(OccasionDetails.this, LinearLayoutManager.VERTICAL, false));
        SelectedProductAdapter productListAdaptor = new SelectedProductAdapter(datum, OccasionDetails.this, (position, votedProductResponse) -> {
            shareFund(votedProductResponse);
        });
        binding.rvProductlist.setAdapter(productListAdaptor);
        productListAdaptor.notifyDataSetChanged();

    }

    private void shareFund(VotedProductResponse votedProductResponse) {
        SweetAlertDialog pDialog = new SweetAlertDialog(OccasionDetails.this, SweetAlertDialog.PROGRESS_TYPE);
        pDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
        pDialog.setTitleText("Loading");
        pDialog.setCancelable(false);
        pDialog.show();
        ApiInterface apiInterface = ApiClient.getApiClientShareFund().create(ApiInterface.class);
        ArrayList<InitiateFundFriends> initiateFundFriendsArrayList = new ArrayList<>();

        InitialFundRequestModel initiateFundModel = new InitialFundRequestModel();
//        InitiateFundModel initiateFundModel = new InitiateFundModel();
        initiateFundModel.setRequestType("initiate_team_buy");
        initiateFundModel.setNotes("");
        initiateFundModel.setMiscCost(10.2);
        initiateFundModel.setFirstName(userDetailDetail[0].getFirst_name());
        initiateFundModel.setLastName(userDetailDetail[0].getLast_name());
        initiateFundModel.setOccasionName(occasionName);
        initiateFundModel.setProductPrice(votedProductResponse.getPrice());
        initiateFundModel.setExpirationDate(AppUtils.addDayWithNoOfDays(AppUtils.getCurrentDateCopy(), 5));
        initiateFundModel.setInitiated_date(AppUtils.getCurrentDateCopy());
        initiateFundModel.setTimeZone(TimeZone.getDefault().getID());
        initiateFundModel.setOccasionId(occasionId);
        initiateFundModel.setOccasionDate(occasionDate);
        initiateFundModel.setProductId(votedProductResponse.getProduct_id());
        initiateFundModel.setEmailAddress(userDetailDetail[0].getEmail_address());
        initiateFundModel.setUserId(userDetailDetail[0].getLogged_user_id());
        initiateFundModel.setPhoneNumber(userDetailDetail[0].getPhone_number());
        initiateFundModel.setCountry_code(appPreferences.getCountryCode());
        initiateFundModel.setFriendCircleId(friendCircleId);



        /*InitiateFundCreator initiateFundCreator = new InitiateFundCreator(" ", 10.2, userDetailDetail[0].getLast_name(), votedProductResponse.getPrice(), AppUtils.addDayWithNoOfDays(AppUtils.getCurrentDate(), 5), TimeZone.getDefault().getID(), occasionId, userDetailDetail[0].getEmail_address(), userDetailDetail[0].getLogged_user_id(), occasionDate, votedProductResponse.getProduct_id(), userDetailDetail[0].getPhone_number(), friendCircleId, userDetailDetail[0].getFirst_name());
        for (FriendListEntity contributor : contributorList) {
            if (!contributor.getUser_id().equals(appPreferences.getUserId())) {
                InitiateFundFriends initiateFundFriends = new InitiateFundFriends("y", contributor.getEmail_address(), contributor.getUser_id(), AppUtils.getCurrentDate(), contributor.getLast_name(), contributor.getPhone_number(), contributor.getFirst_name());
                initiateFundFriendsArrayList.add(initiateFundFriends);
            }
        }

        initiateFundModel.setCreator(initiateFundCreator);
        initiateFundModel.setFriends(initiateFundFriendsArrayList.toArray(new InitiateFundFriends[0]));*/
        Observable<Response<TransactionDetailResponse>> products = apiInterface.gmm_initiate_team_buy(initiateFundModel);
        compositeDisposable.add(products
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map(result -> result)
                .subscribe(response -> {
                    pDialog.dismiss();
                    if (response.isSuccessful() && (response.code() == 200 || response.code() == 201)) {
                        ResultResponse(response);
                    } else if (response.code() == 400) {
                        try {
                            JSONObject jsonObject = new JSONObject(response.errorBody().string());
                            String error = jsonObject.getString("Error");
                            Toast.makeText(this, error, Toast.LENGTH_SHORT).show();
                        } catch (Exception e) {
                            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(this, "something went wrong", Toast.LENGTH_LONG).show();
                    }
                }, throwable -> {
                    pDialog.dismiss();
                    AppUtils.showMessage(this, throwable.getMessage());
                }));

    }

    private void ResultResponse(Response<TransactionDetailResponse> response) {

        ArrayList<TransactionItem> transactionItems = (ArrayList<TransactionItem>) response.body().getTransaction();
        startActivity(new Intent(this, TransactionConfirm.class).putExtra("data",transactionItems));


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

    public  void  actionLike(VotedProductResponse votedProductResponse){
//        voteProduct(votedProductResponse);
        productLike(votedProductResponse.getProduct_id());

    }

    public void actionDisLike(VotedProductResponse votedProductResponse) {

    }

    public void productLike(String productId) {
        String year;
        if (occasionDate != null) {

            String[] date = occasionDate.split("/");
            year = date[2];
        } else {
            year = "";
        }
        SweetAlertDialog pDialog = new SweetAlertDialog(this, SweetAlertDialog.PROGRESS_TYPE);
        pDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
        pDialog.setTitleText("Loading");
        pDialog.setCancelable(false);
        pDialog.show();
        ApiInterface apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        AppPreferences appPreferences = new AppPreferences(this);

        ProductVote productVote = new ProductVote();
        productVote.setProductId(Integer.parseInt(productId));
        productVote.setOccasionId(occasionId);
        productVote.setVote(1);
        productVote.setOccasionName(occasionName);
        productVote.setOccasionYear(Integer.parseInt(year));
        productVote.setTimeZone(TimeZone.getDefault().getID());
        productVote.setApiCallTime(AppUtils.getCurrentDateCopy());
        productVote.setRequestId(9);
        productVote.setComment("He will love this pr");
        productVote.setFriendCircleId(friendCircleId);
        productVote.setUserId(appPreferences.getUserId());

        Observable<Response<ResponseBody>> friendDetail = apiInterface.productVote(productVote);
        compositeDisposable.add(friendDetail
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map(result -> result)
                .subscribe(response -> {
                    pDialog.dismiss();
                    if (response.isSuccessful() && (response.code() == 200 || response.code() == 201)) {

//                        ConfirmationDialog();
                        Toast.makeText(this, "Voted Product successfully", Toast.LENGTH_SHORT).show();
                    } else if (response.code() == 400) {
                        try {
                            JSONObject jsonObject = new JSONObject(response.errorBody().string());
                            String error = jsonObject.getString("Error");
                            Toast.makeText(this, error, Toast.LENGTH_SHORT).show();
                        } catch (Exception e) {
                            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        try {
                            JSONObject jsonObject = new JSONObject(response.errorBody().string());
                            String error = jsonObject.getString("Error");
                            Toast.makeText(this, error, Toast.LENGTH_SHORT).show();
                        } catch (Exception e) {
                            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }

                }, throwable -> {
                    pDialog.dismiss();
                    AppUtils.showMessage(this, throwable.getMessage());
                }));
    }
    private void voteProduct(VotedProductResponse votedProductResponse) {

        SweetAlertDialog pDialog = new SweetAlertDialog(this, SweetAlertDialog.PROGRESS_TYPE);
        pDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
        pDialog.setTitleText("Loading");
        pDialog.setCancelable(false);
        pDialog.show();
        ApiInterface apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        AppPreferences appPreferences = new AppPreferences(this);
        InsertVoteProduct insertVoteProduct = new InsertVoteProduct(8, votedProductResponse.product_id, votedProductResponse.product_title, ""+votedProductResponse.getPrice(), friendCircleId, appPreferences.getUserId(), 1, "He will love this product", occasionName, year);

        Observable<Response<ResponseBody>> friendDetail = apiInterface.insertProductVote(insertVoteProduct);
        compositeDisposable.add(friendDetail
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map(result -> result)
                .subscribe(response -> {
                    pDialog.dismiss();
                    if (response.isSuccessful() && (response.code() == 200 || response.code() == 201)) {
                        Toast.makeText(this, "You liked this product", Toast.LENGTH_SHORT).show();
                        getAllSelectedProducts(friendCircleId, occasionName, year);

                    } else if (response.code() == 400) {
                        try {
                            JSONObject jsonObject = new JSONObject(response.errorBody().string());
                            String error = jsonObject.getString("Error");
                            Toast.makeText(this, error, Toast.LENGTH_SHORT).show();
                        } catch (Exception e) {
                            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        try {
                            JSONObject jsonObject = new JSONObject(response.errorBody().string());
                            String error = jsonObject.getString("Error");
                            Toast.makeText(this, error, Toast.LENGTH_SHORT).show();
                        } catch (Exception e) {
                            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }

                }, throwable -> {
                    pDialog.dismiss();
                    AppUtils.showMessage(this, throwable.getMessage());
                }));

    }


}
