package com.gift.gemift.Ui.DashBoard.Fragments.Product;

import static com.gift.gemift.Utils.Constant.FRIEND_CIRCLE_ID;
import static com.gift.gemift.Utils.Constant.OCCASION_DATE;
import static com.gift.gemift.Utils.Constant.OCCASION_ID;
import static com.gift.gemift.Utils.Constant.OCCASION_NAME;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.cazaea.sweetalert.SweetAlertDialog;
import com.gift.gemift.Model.Filter;
import com.gift.gemift.Model.GetOccasionDetailResponse;
import com.gift.gemift.Model.GetOccasionResponse;
import com.gift.gemift.Model.InsertVoteProduct;
import com.gift.gemift.Model.ProductDatum;
import com.gift.gemift.Model.ProductRoot;
import com.gift.gemift.Model.ProductVote;
import com.gift.gemift.Network.ApiClient;
import com.gift.gemift.Network.ApiInterface;
import com.gift.gemift.R;
import com.gift.gemift.Storage.AppPreference.AppPreferences;
import com.gift.gemift.Storage.AppPreference.Preferences;
import com.gift.gemift.Ui.Adaptor.ProductListAdaptor;
import com.gift.gemift.Utils.AppUtils;
import com.gift.gemift.Utils.FilterHelper;
import com.gift.gemift.databinding.FilterLayoutBinding;
import com.gift.gemift.databinding.OccasionproductsBinding;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.TimeZone;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;
import retrofit2.Response;


public class OccasionProducts extends AppCompatActivity {

    private final CompositeDisposable compositeDisposable = new CompositeDisposable();
    OccasionproductsBinding binding;
    FilterLayoutBinding filterLayoutBinding;
    ArrayList<String> occasionNames = new ArrayList<>();
    ArrayList<String> interestList = new ArrayList<>();
    ActivityResultLauncher<Intent> activityResultLauncher;
    private SweetAlertDialog pDialog;
    private ApiInterface apiInterface;
    private String friendCircleId, occasionId, occasionDate, occasionName;
    private HashMap<String, String> interestIdHashMap;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = OccasionproductsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.appbar.toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        final Drawable upArrow = ResourcesCompat.getDrawable(getResources(), R.drawable.ic_baseline_arrow, null);
        FilterHelper.setColorFilter(upArrow, getResources().getColor(R.color.white), FilterHelper.Mode.SRC_ATOP);
        getSupportActionBar().setHomeAsUpIndicator(upArrow);

        LoadUI();

    }

    private void LoadUI() {
        binding.appbar.txtTitle.setText("Products");
        Intent dataIntent = getIntent();
        apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        friendCircleId = dataIntent.getStringExtra(FRIEND_CIRCLE_ID);
        occasionId = dataIntent.getStringExtra(OCCASION_ID);
        occasionName = dataIntent.getStringExtra(OCCASION_NAME);
        occasionDate = dataIntent.getStringExtra(OCCASION_DATE);
        Type type = new TypeToken<HashMap<String, String>>() {
        }.getType();
        interestIdHashMap = new Gson().fromJson(getIntent().getStringExtra("InterestList"), type);

//        Iterator myVeryOwnIterator = interestIdHashMap.keySet().iterator();
//        while(myVeryOwnIterator.hasNext()) {
//            String key=(String)myVeryOwnIterator.next();
//            interestList.add(key);
//            String value=(String)interestIdHashMap.get(key);
//            Toast.makeText(this, "Key: "+key+" Value: "+value, Toast.LENGTH_SHORT).show();
//        }
        if (!Preferences.filters.isEmpty()) {
            Preferences.filters.get(Filter.INDEX_OCCASION).setSelected(new ArrayList());
            Preferences.filters.get(Filter.INDEX_CATEGORY).setSelected(new ArrayList());
            Preferences.filters.get(Filter.INDEX_PRICE).setSelected(new ArrayList());
        }
        getAllProducts(friendCircleId, occasionId, "ASC");
//        getOccasionDetails();
        binding.filterB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(OccasionProducts.this, Filter_Activity.class);
                intent.putExtra(OCCASION_ID, occasionId);
                activityResultLauncher.launch(intent);

            }
        });

        binding.appbar.imgFilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        activityResultLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
            if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                Bundle bundle = result.getData().getExtras();
                getAllProducts(friendCircleId, occasionId, "ASC");

            } else if (result.getResultCode() == 101 && result.getData() != null) {
                Bundle bundle1 = result.getData().getExtras();

            }
        });


    }

    @SuppressLint("SetTextI18n")
    private void getAllProducts(String friendId, String occasionId, String sortorder) {
        SweetAlertDialog pDialog = new SweetAlertDialog(OccasionProducts.this, SweetAlertDialog.PROGRESS_TYPE);
        pDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
        pDialog.setTitleText("Loading");
        pDialog.setCancelable(false);
        pDialog.show();
        ApiInterface apiInterface = ApiClient.getApiClient().create(ApiInterface.class);

        Observable<Response<ProductRoot>> products = apiInterface.getProducts(1, sortorder, occasionId, friendId,"1","100");
        compositeDisposable.add(products
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map(result -> result)
                .subscribe(response -> {
                    if (response.isSuccessful() && (response.code() == 200 || response.code() == 201)) {
                        ArrayList<ProductDatum> datum = response.body().getData();
                        for (int i = 0; i < datum.size(); i++) {
                         /*mr   String tagNumber = datum.get(i).getOccasion_name();
                            occasionNames.add(tagNumber);*/
                            occasionNames.add(occasionName);
                        }
                        if (datum.size() > 0) {
                            LoadRvProducts(datum, pDialog);
                        } else {
                            pDialog.dismiss();
                            AlertNoProducts();
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

    @SuppressLint({"NotifyDataSetChanged", "SetTextI18n"})
    private void LoadRvProducts(ArrayList<ProductDatum> productDatumArrayList, SweetAlertDialog pDialog) {
        binding.tvHotdeals.setText("Hot deals(" + productDatumArrayList.size() + ")");
        if (!Preferences.filters.isEmpty()) {
            ArrayList<ProductDatum> filteredItems = new ArrayList<ProductDatum>();
            List<String> occasions = Preferences.filters.get(Filter.INDEX_OCCASION).getSelected();
            List<String> category = Preferences.filters.get(Filter.INDEX_CATEGORY).getSelected();
            List<String> prices = Preferences.filters.get(Filter.INDEX_PRICE).getSelected();
            for (ProductDatum item : productDatumArrayList) {
//mr                boolean occasionmatched = occasions.size() <= 0 || occasions.contains(item.getOccasion_name());
                //                boolean sizeMatched = true;
//                if (category.size() > 0 && !category.contains(item.getCategory().toString())) {
//                    sizeMatched = false;
//                }
                boolean priceMatched = prices.size() <= 0 || priceContains(prices, item.getPrice());
                //                if (occasionmatched && sizeMatched && priceMatched) {
//                    filteredItems.add(item);
//                }
               /*mr if (occasionmatched && priceMatched) {
                    filteredItems.add(item);
                }*/
            }
            productDatumArrayList = filteredItems;
        }

        binding.tvHotdeals.setText("Hot deals (" + productDatumArrayList.size() + ")");
        if (occasionId == null) {
            getOccasionDetails(productDatumArrayList);
        } else {
//            binding.rvProductlist.setLayoutManager(new LinearLayoutManager(OccasionProducts.this, LinearLayoutManager.VERTICAL, false));
            ProductListAdaptor productListAdaptor = new ProductListAdaptor(productDatumArrayList, OccasionProducts.this, friendCircleId, occasionId, occasionName, occasionDate);
            binding.rvProductlist.setAdapter(productListAdaptor);
            binding.rvProductlist.setNestedScrollingEnabled(false);
            binding.rvProductlist.setHasFixedSize(true);
            productListAdaptor.notifyDataSetChanged();
        }

        pDialog.dismiss();
    }

    private void getOccasionDetails(ArrayList<ProductDatum> productDatumArrayList) {
        SweetAlertDialog pDialog = new SweetAlertDialog(OccasionProducts.this, SweetAlertDialog.PROGRESS_TYPE);
        pDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
        pDialog.setTitleText("Loading");
        pDialog.setCancelable(false);
        pDialog.show();
        AppPreferences appPreferences = new AppPreferences(this);
        Observable<Response<GetOccasionResponse>> friendDetail = apiInterface.getOccasionListByCircleId(1, friendCircleId, appPreferences.getUserId());
        compositeDisposable.add(friendDetail
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map(result -> result)
                .subscribe(response -> {
                    pDialog.dismiss();
                    if (response.isSuccessful() && (response.code() == 200 || response.code() == 201)) {
                        if (response.body().getOccasions().length > 0) {
                            GetOccasionDetailResponse[] occasionDetailResponses = response.body().getOccasions();
//                            for (GetOccasionDetailResponse datum : occasionDetailResponses) {
//                                datum.getOccasion_id();
//                                datum.getOccasion_date();
//                                datum.getOccasion_name();
//
//                            }
                            binding.rvProductlist.setLayoutManager(new LinearLayoutManager(OccasionProducts.this, LinearLayoutManager.VERTICAL, false));
                            ProductListAdaptor productListAdaptor = new ProductListAdaptor(productDatumArrayList, OccasionProducts.this, friendCircleId, occasionDetailResponses[0].getOccasion_id(), occasionDetailResponses[0].getOccasion_name(), occasionDetailResponses[0].getOccasion_date());
                            binding.rvProductlist.setAdapter(productListAdaptor);
                            binding.rvProductlist.setNestedScrollingEnabled(false);
                            binding.rvProductlist.setHasFixedSize(true);
                            productListAdaptor.notifyDataSetChanged();
                        } else {
                            NoOccasionsDialog();
                        }
                    } else if (response.code() == 400) {

                        try {
                            JSONObject jsonObject = new JSONObject(response.errorBody().string());
                            String error = jsonObject.getString("Error");
                            Toast.makeText(this, error, Toast.LENGTH_LONG).show();
                        } catch (Exception e) {
                            Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    } else {

                    }
                }, throwable -> {
                    pDialog.dismiss();
                    AppUtils.showMessage(this, throwable.getMessage());
                }));

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        compositeDisposable.clear();
    }

    private void AlertNoProducts() {
        new SweetAlertDialog(this, SweetAlertDialog.ERROR_TYPE)
                .setTitleText("Sorry!!")
                .setContentText("No Products available for " + occasionName)
                .setConfirmText("Ok")
                .setConfirmClickListener(sweetAlertDialog -> finish())
                .show();
    }

    private boolean priceContains(List<String> prices, Double price) {
        boolean flag = false;
        for (String p : prices) {
            String[] tmpPrices = p.split("-");
            if (price >= Double.valueOf(tmpPrices[0]) && price <= Double.valueOf(tmpPrices[1])) {
                flag = true;
                break;
            }
        }
        return flag;
    }

    private void NoOccasionsDialog() {
        new SweetAlertDialog(this, SweetAlertDialog.WARNING_TYPE)
                .setTitleText("Sorry!!!")
                .setContentText("No occasions available to select gift")
                .setConfirmText("Ok")
                .setConfirmClickListener(sweetAlertDialog -> finish())
                .show();

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




    public void voteProduct(String prodId, String prodTitle, String prodPrice,String friendCircleId) {

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
        InsertVoteProduct insertVoteProduct = new InsertVoteProduct(8, prodId, prodTitle, prodPrice, friendCircleId, appPreferences.getUserId(), 1, "He will love this product", occasionName, year);

        insertVoteProduct.setOccasionId(occasionId);
        Observable<Response<ResponseBody>> friendDetail = apiInterface.insertProductVote(insertVoteProduct);
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

}
