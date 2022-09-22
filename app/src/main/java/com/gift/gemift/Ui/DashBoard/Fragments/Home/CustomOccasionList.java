package com.gift.gemift.Ui.DashBoard.Fragments.Home;

import static com.gift.gemift.Utils.Constant.CIRCLE_ID;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;

import com.cazaea.sweetalert.SweetAlertDialog;
import com.gift.gemift.Model.OccasionInsertResponse;
import com.gift.gemift.Network.ApiClient;
import com.gift.gemift.Network.ApiInterface;
import com.gift.gemift.R;
import com.gift.gemift.Storage.AppPreference.AppPreferences;
import com.gift.gemift.Ui.DashBoard.DashBoard;
import com.gift.gemift.Utils.AppUtils;
import com.gift.gemift.Utils.FilterHelper;
import com.gift.gemift.databinding.CustomOccasionListBinding;

import org.json.JSONObject;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;
import retrofit2.Response;

public class CustomOccasionList extends AppCompatActivity {

    private final String[] frequency_list = {"Every Year", "Every Month", "Every Week"};
    private final CompositeDisposable compositeDisposable = new CompositeDisposable();
    private CustomOccasionListBinding binding;
    private String frequency_selected, circle_id;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = CustomOccasionListBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.appbar.toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        final Drawable upArrow = ResourcesCompat.getDrawable(getResources(), R.drawable.ic_baseline_arrow, null);
        FilterHelper.setColorFilter(upArrow, getResources().getColor(R.color.white), FilterHelper.Mode.SRC_ATOP);
        getSupportActionBar().setHomeAsUpIndicator(upArrow);

        binding.appbar.txtTitle.setText("Add Custom Occasion");

        LoadUi();
    }

    private void LoadUi() {
        Intent dataIntent = getIntent();
        circle_id = dataIntent.getStringExtra(CIRCLE_ID);

        binding.txtStartDate.setOnClickListener(v -> {
            AppUtils.hideKeyboard(this);
            AppUtils.datePicker(this, binding.txtStartDate, binding.txtStartDate.getText().toString().trim());
        });


        binding.btnProceed.setOnClickListener(v -> InsertOccasion());

        ArrayAdapter<String> frequency_adaptor = new ArrayAdapter<>(CustomOccasionList.this, android.R.layout.simple_spinner_dropdown_item, frequency_list);
        binding.txtFrequency.setAdapter(frequency_adaptor);
        binding.txtFrequency.setOnItemClickListener((parent, view, position, id) -> {
            frequency_selected = parent.getItemAtPosition(position).toString();

        });
    }

    private void InsertOccasion() {
        SweetAlertDialog pDialog = new SweetAlertDialog(this, SweetAlertDialog.PROGRESS_TYPE);
        pDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
        pDialog.setTitleText("Loading");
        pDialog.setCancelable(false);
        pDialog.show();
        ApiInterface apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        AppPreferences appPreferences = new AppPreferences(this);
        OccasionInsertResponse occasionInsertResponse = new OccasionInsertResponse(appPreferences.getUserId(), "", binding.txtStartDate.getText().toString(), binding.edtOccasionName.getText().toString(), circle_id, 4, frequency_selected);

        Observable<Response<ResponseBody>> friendDetail = apiInterface.insertOccasion(occasionInsertResponse);
        compositeDisposable.add(friendDetail
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map(result -> result)
                .subscribe(response -> {
                    pDialog.dismiss();
                    if (response.isSuccessful() && (response.code() == 200 || response.code() == 201)) {
                        startActivity(new Intent(this, DashBoard.class));
                    } else if (response.code() == 400) {
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
