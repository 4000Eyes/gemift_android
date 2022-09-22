package com.gift.gemift.Ui.DashBoard.Fragments.Product;

import static com.gift.gemift.Utils.Constant.FRIEND_CIRCLE_ID;
import static com.gift.gemift.Utils.Constant.OCCASION_DATE;
import static com.gift.gemift.Utils.Constant.OCCASION_ID;
import static com.gift.gemift.Utils.Constant.OCCASION_NAME;
import static com.gift.gemift.Utils.Constant.PROD_DESCRIPTION;
import static com.gift.gemift.Utils.Constant.PROD_ID;
import static com.gift.gemift.Utils.Constant.PROD_PRICE;
import static com.gift.gemift.Utils.Constant.PROD_TITLE;

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

import com.cazaea.sweetalert.SweetAlertDialog;
import com.gift.gemift.Model.InsertVoteProduct;
import com.gift.gemift.Network.ApiClient;
import com.gift.gemift.Network.ApiInterface;
import com.gift.gemift.R;
import com.gift.gemift.Storage.AppPreference.AppPreferences;
import com.gift.gemift.Ui.DashBoard.DashBoard;
import com.gift.gemift.Utils.AppUtils;
import com.gift.gemift.Utils.FilterHelper;
import com.gift.gemift.databinding.ProductDetailsBinding;

import org.json.JSONObject;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;
import retrofit2.Response;

public class ProductDetails extends AppCompatActivity {

    private final CompositeDisposable compositeDisposable = new CompositeDisposable();
    ProductDetailsBinding binding;
    String occasionId, occasionName, occasionDate, friendCircleId, prodPrice, prodTitle, prodDesc, prodId, year;
    ApiInterface apiInterface;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ProductDetailsBinding.inflate(getLayoutInflater());
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

        binding.appbar.txtTitle.setText("Product Info");
        Intent dataIntent = getIntent();
        apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        AppPreferences appPreferences = new AppPreferences(this);
        friendCircleId = dataIntent.getStringExtra(FRIEND_CIRCLE_ID);
        occasionId = dataIntent.getStringExtra(OCCASION_ID);
        occasionName = dataIntent.getStringExtra(OCCASION_NAME);
        occasionDate = dataIntent.getStringExtra(OCCASION_DATE);
        prodTitle = dataIntent.getStringExtra(PROD_TITLE);
        prodDesc = dataIntent.getStringExtra(PROD_DESCRIPTION);
        prodPrice = dataIntent.getStringExtra(PROD_PRICE);
        prodId = dataIntent.getStringExtra(PROD_ID);

        binding.prodTitle.setText(prodTitle);
        binding.prodPrice.setText("$ " + prodPrice);
        binding.prodDesc.setText(prodDesc);

        if (occasionDate != null) {

            String[] date = occasionDate.split("/");
            year = date[2];
        } else {
            year = "";
        }
        binding.btnProceed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                voteProduct();
            }
        });
    }

    private void voteProduct() {

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

    private void ConfirmationDialog() {
        new SweetAlertDialog(this, SweetAlertDialog.SUCCESS_TYPE)
                .setTitleText("Success")
                .setContentText("Voted Product successfully")
                .setConfirmText("Ok")
                .setConfirmClickListener(sweetAlertDialog -> startActivity(new Intent(this, DashBoard.class)))
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

    @Override
    protected void onDestroy() {
        super.onDestroy();
        compositeDisposable.clear();
    }
}
