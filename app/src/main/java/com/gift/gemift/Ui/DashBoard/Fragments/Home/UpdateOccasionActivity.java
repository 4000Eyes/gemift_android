package com.gift.gemift.Ui.DashBoard.Fragments.Home;

import static com.gift.gemift.Utils.Constant.CIRCLE_ID;
import static com.gift.gemift.Utils.Constant.FRIEND_CIRCLE_ID;
import static com.gift.gemift.Utils.Constant.FRIEND_CIRCLE_IMAGE;
import static com.gift.gemift.Utils.Constant.FRIEND_CIRCLE_TITLE;
import static com.gift.gemift.Utils.Constant.OCCASION_DATE;
import static com.gift.gemift.Utils.Constant.OCCASION_ID;
import static com.gift.gemift.Utils.Constant.OCCASION_NAME;
import static com.gift.gemift.Utils.Constant.SECRET_FIRST_NAME;

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

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.cazaea.sweetalert.SweetAlertDialog;
import com.gift.gemift.Model.UpdateOccasion;
import com.gift.gemift.Network.ApiClient;
import com.gift.gemift.Network.ApiInterface;
import com.gift.gemift.R;
import com.gift.gemift.Storage.AppPreference.AppPreferences;
import com.gift.gemift.Ui.DashBoard.DashBoard;
import com.gift.gemift.Utils.AppUtils;
import com.gift.gemift.Utils.FilterHelper;
import com.gift.gemift.databinding.OccationUpdateBinding;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.TimeZone;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;
import retrofit2.Response;

public class UpdateOccasionActivity extends AppCompatActivity {

    private final CompositeDisposable compositeDisposable = new CompositeDisposable();
    private final ArrayList<String> occasionList = new ArrayList<>();
    private final HashMap<String, String> occasionIdHashMap = new HashMap<>();
    private OccationUpdateBinding binding;
    private String occasion_selected = "", occasion_id, circle_id;
    private AppPreferences appPreferences;
    String friendCircleName;
    String friendCircleImage;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = OccationUpdateBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.appbar.toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        final Drawable upArrow = ResourcesCompat.getDrawable(getResources(), R.drawable.ic_baseline_arrow, null);
        FilterHelper.setColorFilter(upArrow, getResources().getColor(R.color.white), FilterHelper.Mode.SRC_ATOP);
        getSupportActionBar().setHomeAsUpIndicator(upArrow);

        binding.appbar.txtTitle.setText("Add Ocassion");

        LoadUi();
    }

    private void LoadUi() {
        Intent dataIntent = getIntent();
        appPreferences = new AppPreferences(this);


        String friendCircleId = dataIntent.getStringExtra(FRIEND_CIRCLE_ID);
        String secretFriendName = dataIntent.getStringExtra(SECRET_FIRST_NAME);
        String occasionId = dataIntent.getStringExtra(OCCASION_ID);
        String occasionName = dataIntent.getStringExtra(OCCASION_NAME);
        String occasionDate = dataIntent.getStringExtra(OCCASION_DATE);
        binding.txtRelationType.setText(friendCircleName);
        if (friendCircleImage != null) {
            RequestOptions myOptions = new RequestOptions().centerCrop();
            Glide.with(this)
                    .asBitmap()
                    .apply(myOptions)
                    .load(friendCircleImage)
                    .into(binding.imgFriends2);
        }


        binding.btnProceed.setOnClickListener(v -> {
            int month = binding.datePicker.getMonth() + 1;
            String dayOfMonth = "";
            String selectedMonth = "" + month;
            String selectedDay = "" + binding.datePicker.getDayOfMonth();
            if (month < 10) {
                selectedMonth = "0" + month;
            }
            if (binding.datePicker.getDayOfMonth() < 10) {
                selectedDay = "0" + selectedDay;
            }

            String dateSelected = selectedDay + "/" + selectedMonth + "/" + binding.datePicker.getYear();
            UpdateOccasion occasion = new UpdateOccasion();
            occasion.setOccasionId(occasionId);
            occasion.setOccasionDate(dateSelected);
            occasion.setUserId(appPreferences.getUserId());
            occasion.setFriendCircleId(friendCircleId);
            occasion.setRequestId(0);
            occasion.setValueTimezone("");
            occasion.setTimeZone(TimeZone.getDefault().getID());
            occasion.setApiCallTime(AppUtils.getCurrentDateCopy());
            InsertOccasion(occasion);
        });

    }






    private void InsertOccasion(UpdateOccasion occasionInsertResponse) {
        ProgressDialog pDialog = new ProgressDialog(this);
        pDialog.setMessage("Please Wait...");
        pDialog.show();
        ApiInterface apiInterface = ApiClient.getApiClient().create(ApiInterface.class);

        Observable<Response<ResponseBody>> friendDetail = apiInterface.updateOccasion(occasionInsertResponse);
        compositeDisposable.add(friendDetail
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map(result -> result)
                .subscribe(response -> {
                    pDialog.dismiss();
                    if (response.isSuccessful() && (response.code() == 200 || response.code() == 201)) {
//                        ConfirmationDialog();
                        AppUtils.showMessage(this, "Occasion updated successfully");
                        startActivity(new Intent(this, DashBoard.class));
                    } else if (response.code() == 400) {
                        try {
                            JSONObject jsonObject = new JSONObject(response.errorBody().string());
                            String error = jsonObject.getString("Error");
//                            errorDialog(error);
                            AppUtils.showMessage(this, error);
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

    private void ConfirmationDialog() {
//        new SweetAlertDialog(this, SweetAlertDialog.SUCCESS_TYPE)
//                .setTitleText("Success")
//                .setContentText("Occasion added successfully")
//                .setConfirmText("Ok")
//                .setConfirmClickListener(sweetAlertDialog -> startActivity(new Intent(this, DashBoard.class)))
//                .show();


    }

    private void errorDialog(String error) {
        new SweetAlertDialog(this, SweetAlertDialog.ERROR_TYPE)
                .setTitleText("Failure")
                .setContentText(error)
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
}
