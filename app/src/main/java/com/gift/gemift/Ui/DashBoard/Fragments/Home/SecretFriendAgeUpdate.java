package com.gift.gemift.Ui.DashBoard.Fragments.Home;

import static com.gift.gemift.Utils.Constant.CIRCLE_ID;
import static com.gift.gemift.Utils.Constant.SECRET_FIRST_NAME;
import static com.gift.gemift.Utils.Constant.SECRET_LAST_NAME;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;

import com.gift.gemift.Model.UpdateFriendAgeResponse;
import com.gift.gemift.Network.ApiClient;
import com.gift.gemift.Network.ApiInterface;
import com.gift.gemift.R;
import com.gift.gemift.Storage.AppPreference.AppPreferences;
import com.gift.gemift.Ui.DashBoard.DashBoard;
import com.gift.gemift.Utils.AppUtils;
import com.gift.gemift.Utils.FilterHelper;
import com.gift.gemift.databinding.UpdateAgeBinding;

import org.json.JSONObject;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;
import retrofit2.Response;

public class SecretFriendAgeUpdate extends AppCompatActivity {

    private final CompositeDisposable compositeDisposable = new CompositeDisposable();
    private UpdateAgeBinding binding;
    private String gender = "";
    private String circle_id, firstName, lastName;
    private AppPreferences appPreferences;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = UpdateAgeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.appbar.toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        final Drawable upArrow = ResourcesCompat.getDrawable(getResources(), R.drawable.ic_baseline_arrow, null);
        FilterHelper.setColorFilter(upArrow, getResources().getColor(R.color.white), FilterHelper.Mode.SRC_ATOP);
        getSupportActionBar().setHomeAsUpIndicator(upArrow);

        binding.appbar.txtTitle.setText("Update Age");

        LoadUi();

    }

    private void LoadUi() {
        appPreferences = new AppPreferences(this);
        Intent dataIntent = getIntent();
        circle_id = dataIntent.getStringExtra(CIRCLE_ID);
        firstName = dataIntent.getStringExtra(SECRET_FIRST_NAME);
        lastName = dataIntent.getStringExtra(SECRET_LAST_NAME);

        binding.edtFirstName.setText(firstName);
        binding.edtFirstName.setEnabled(false);
        binding.edtLastName.setText(lastName);
        binding.edtLastName.setEnabled(false);

        binding.radioGenderGroup.setOnCheckedChangeListener((group, checkedId) -> {
            if (binding.radioMale.isChecked()) {
                gender = "M";
            } else {
                gender = "F";
            }
        });


        binding.btnProceed.setOnClickListener(v -> insertAge());

    }

    private void insertAge() {
        ProgressDialog pDialog = new ProgressDialog(this);
        pDialog.setMessage("Please Wait...");
        pDialog.show();
        ApiInterface apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        UpdateFriendAgeResponse updateFriendAgeResponse = new UpdateFriendAgeResponse(gender, Integer.parseInt(binding.edtAge.getText().toString()), circle_id, appPreferences.getUserId(), 1);
        Observable<Response<ResponseBody>> friendCircleAge = apiInterface.updateFriendAge(updateFriendAgeResponse);
        compositeDisposable.add(friendCircleAge
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
                            Toast.makeText(SecretFriendAgeUpdate.this, error, Toast.LENGTH_LONG).show();
                        } catch (Exception e) {
                            Toast.makeText(SecretFriendAgeUpdate.this, e.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    }

                }, throwable -> {
                    pDialog.dismiss();
                    AppUtils.showMessage(SecretFriendAgeUpdate.this, throwable.getMessage());
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
}
