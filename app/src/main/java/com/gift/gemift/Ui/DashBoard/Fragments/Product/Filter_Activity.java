package com.gift.gemift.Ui.DashBoard.Fragments.Product;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.cazaea.sweetalert.SweetAlertDialog;
import com.gift.gemift.Model.Filter;
import com.gift.gemift.Model.GetOccasionDetailResponse;
import com.gift.gemift.Model.GetOccasionResponse;
import com.gift.gemift.Model.ProductDatum;
import com.gift.gemift.Model.product.DataItem;
import com.gift.gemift.Model.product.ProductCategoryResponse;
import com.gift.gemift.Network.ApiClient;
import com.gift.gemift.Network.ApiInterface;
import com.gift.gemift.R;
import com.gift.gemift.Storage.AppPreference.AppPreferences;
import com.gift.gemift.Storage.AppPreference.Preferences;
import com.gift.gemift.Ui.Adaptor.FilterAdapter;
import com.gift.gemift.Ui.Adaptor.ProductListAdaptor;
import com.gift.gemift.Utils.AppUtils;
import com.gift.gemift.Utils.FilterHelper;
import com.gift.gemift.databinding.FilterActivityBinding;
import com.gift.gemift.databinding.OccasionproductsBinding;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.TimeZone;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;
import retrofit2.Response;

import static com.gift.gemift.Utils.Constant.FRIEND_CIRCLE_ID;
import static com.gift.gemift.Utils.Constant.OCCASION_ID;

public class Filter_Activity extends AppCompatActivity {

    FilterActivityBinding binding;
    private final CompositeDisposable compositeDisposable = new CompositeDisposable();
    private SweetAlertDialog pDialog;
    private ApiInterface apiInterface;
    String OccasionID;
    ArrayList<String> occasionList = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = FilterActivityBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.appbar.toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        final Drawable upArrow = ResourcesCompat.getDrawable(getResources(), R.drawable.ic_baseline_arrow, null);
        FilterHelper.setColorFilter(upArrow, getResources().getColor(R.color.white), FilterHelper.Mode.SRC_ATOP);
        getSupportActionBar().setHomeAsUpIndicator(upArrow);
        binding.appbar.txtTitle.setText("Filters");
        Intent dataIntent = getIntent();
        apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        OccasionID = dataIntent.getStringExtra(OCCASION_ID);
        getCategories();


    }

    private void initControls(List<String> categories) {
        binding.filterRV.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        binding.filterValuesRV.setLayoutManager(new LinearLayoutManager(this));

        List<String> colors = Arrays.asList(new String[]{"Birthday", "Anniversary", "Valentine's day"});
        if (!Preferences.filters.containsKey(Filter.INDEX_OCCASION)) {
            Preferences.filters.put(Filter.INDEX_OCCASION, new Filter("Occasions", colors, new ArrayList()) {
            });
        }
        List<String> sizes = Arrays.asList(new String[]{"Foodie", "Loves Electronics", "Spiritual"});
        if (!Preferences.filters.containsKey(Filter.INDEX_CATEGORY)) {
            Preferences.filters.put(Filter.INDEX_CATEGORY, new Filter("Category", categories, new ArrayList()));
        }
        List<String> prices = Arrays.asList(new String[]{"0-100", "101-200", "201-300"});
        if (!Preferences.filters.containsKey(Filter.INDEX_PRICE)) {
            Preferences.filters.put(Filter.INDEX_PRICE, new Filter("Price", prices, new ArrayList()));
        }

       FilterAdapter filterAdapter = new FilterAdapter(getApplicationContext(), Preferences.filters, binding.filterValuesRV);
        binding.filterRV.setAdapter(filterAdapter);


        binding.clearB.setOnClickListener(v -> {
            Preferences.filters.get(Filter.INDEX_OCCASION).setSelected(new ArrayList());
            Preferences.filters.get(Filter.INDEX_CATEGORY).setSelected(new ArrayList());
            Preferences.filters.get(Filter.INDEX_PRICE).setSelected(new ArrayList());
            Preferences.filters.clear();
            Intent objIntent = new Intent();
            setResult(RESULT_OK, objIntent);
            finish();
        });


        binding.applyB.setOnClickListener(v -> {
            Intent objIntent = new Intent();
            setResult(RESULT_OK, objIntent);
            finish();
        });
    }

    private void getCategories() {


        SweetAlertDialog pDialog = new SweetAlertDialog(Filter_Activity.this, SweetAlertDialog.PROGRESS_TYPE);
        pDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
        pDialog.setTitleText("Loading");
        pDialog.setCancelable(false);
        pDialog.show();
        AppPreferences appPreferences = new AppPreferences(this);
        Observable<Response<ProductCategoryResponse>> getCategories = apiInterface.getProductCategories("3", OccasionID,"M",TimeZone.getDefault().getID(),AppUtils.getCurrentDateCopy() );
        compositeDisposable.add(getCategories
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map(result -> result)
                .subscribe(response -> {
                    pDialog.dismiss();
                    if (response.isSuccessful() && (response.code() == 200 || response.code() == 201)) {
                        List<DataItem> dataItems = response.body().getData();

                        List<String> ca = new ArrayList<>();
                        for (DataItem dataItem : dataItems){
                            ca.add(dataItem.getId());
                        }
                        initControls(ca);

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
